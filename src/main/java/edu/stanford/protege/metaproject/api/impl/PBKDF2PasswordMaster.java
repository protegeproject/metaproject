package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A password hash generator based on PBKDF2 (Password-Based Key Derivation Function 2)
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PBKDF2PasswordMaster implements PasswordMaster {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int SALT_INDEX = 0, PBKDF2_INDEX = 1;

    private final SaltGenerator saltGenerator;
    private final int saltByteSize, hashByteSize, nrPBKDF2Iterations;

    /**
     * Package-private constructor; use builder
     *
     * @param saltGenerator Salt generator
     * @param saltByteSize  Salt byte size
     * @param hashByteSize  Hash byte size
     * @param nrPBKDF2Iterations    Number of iterations
     */
    PBKDF2PasswordMaster(SaltGenerator saltGenerator, int saltByteSize, int hashByteSize, int nrPBKDF2Iterations) {
        this.saltGenerator = checkNotNull(saltGenerator);
        this.saltByteSize = checkNotNull(saltByteSize);
        this.hashByteSize = checkNotNull(hashByteSize);
        this.nrPBKDF2Iterations = checkNotNull(nrPBKDF2Iterations);
    }

    /**
     * Create a salted PBKDF2 hash of the password
     *
     * @param password  Plain password to hash
     * @return Salted PBKDF2 hash of the password
     */
    public SaltedPassword createHash(PlainPassword password) {
        return createHash(password.getPassword());
    }

    /**
     * Create a salted PBKDF2 hash of the password
     *
     * @param password  String password to hash
     * @return Salted PBKDF2 hash of the password
     */
    public SaltedPassword createHash(String password) {
        return createHash(password.toCharArray());
    }

    /**
     * Create a salted PBKDF2 hash of the password
     *
     * @param password  Character array password to hash
     * @return Salted PBKDF2 hash of the password
     */
    private SaltedPassword createHash(char[] password) {
        Salt salt = saltGenerator.generate();
        byte[] saltBytes = salt.getBytes();
        byte[] hash = new byte[0];
        try {
            hash = pbkdf2(password, saltBytes, nrPBKDF2Iterations, hashByteSize);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return new SaltedPasswordImpl(toHex(saltBytes) + ":" + toHex(hash), salt);
    }

    /**
     * Validates a password against the expected hash
     *
     * @param password  The password to check
     * @param correctHash   The hash of the valid password
     * @return true if the password is correct, false otherwise
     */
    public boolean validatePassword(String password, String correctHash) {
        return validatePassword(password.toCharArray(), correctHash);
    }

    /**
     * Validates a password using a hash
     *
     * @param password  Plain password instance to check
     * @param correctHash   The correct salted hashed password instance
     * @return true if the password is correct, false otherwise
     */
    public boolean validatePassword(PlainPassword password, SaltedPassword correctHash) {
        return validatePassword(password.getPassword().toCharArray(), correctHash.getPassword());
    }

    /**
     * Validates a password using a hash
     *
     * @param password  The password to check
     * @param correctHash   The hash of the valid password
     * @return true if the password is correct, false otherwise
     */
    private boolean validatePassword(char[] password, String correctHash) {
        String[] params = correctHash.split(":");
        byte[] salt = fromHex(params[SALT_INDEX]);
        byte[] hash = fromHex(params[PBKDF2_INDEX]);
        byte[] testHash = new byte[0];
        try {
            testHash = pbkdf2(password, salt, nrPBKDF2Iterations, hash.length);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return equals(hash, testHash);
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using a timing attack and then attacked off-line.
     *
     * @param a First byte array
     * @param b Second byte array
     * @return true if both byte arrays are the same, false otherwise
     */
    private boolean equals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    /**
     *  Computes the PBKDF2 hash of a password
     *
     * @param password  Password to hash
     * @param salt  Salt
     * @param iterations    Iteration count (slowness factor)
     * @param bytes Length of the hash to compute in bytes
     * @return The PBDKF2 hash of the password
     * @throws NoSuchAlgorithmException Cryptographic algorithm not available in this environment
     * @throws InvalidKeySpecException  Invalid key specification
     */
    private byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    /**
     * Converts a string of hexadecimal characters into a byte array
     *
     * @param hex   Hex string
     * @return Byte array of decoded hex string
     */
    private byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }

    /**
     * Converts a byte array into a hexadecimal string
     *
     * @param array Byte array to convert
     * @return A length*2 character string encoding the byte array
     */
    private String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }
        else {
            return hex;
        }
    }

    /**
     * Get the salt generator
     *
     * @return Salt generator
     */
    public SaltGenerator getSaltGenerator() {
        return saltGenerator;
    }

    /**
     * Get the byte size set for salts
     *
     * @return Salt byte size
     */
    public int getSaltByteSize() {
        return saltByteSize;
    }

    /**
     * Get the byte size set for hashes
     *
     * @return Hash byte size
     */
    public int getHashByteSize() {
        return hashByteSize;
    }

    /**
     * Get the number of PBKDF2 iterations set to be performed
     *
     * @return Number of PBKDF2 iterations
     */
    public int getNrPBKDF2Iterations() {
        return nrPBKDF2Iterations;
    }


    /**
     * @author Rafael Gonçalves <br>
     * Stanford Center for Biomedical Informatics Research
     */
    public static class Builder {
        private int saltByteSize = 24;
        private int hashByteSize = 24;
        private int nrPBKDF2Iterations = 1000;
        private SaltGenerator saltGenerator = SaltGeneratorImpl.getInstance(saltByteSize);

        public Builder setSaltGenerator(SaltGenerator saltGenerator) {
            this.saltGenerator = saltGenerator;
            return this;
        }

        public Builder setSaltByteSize(int saltByteSize) {
            this.saltByteSize = saltByteSize;
            return this;
        }

        public Builder setHashByteSize(int hashByteSize) {
            this.hashByteSize = hashByteSize;
            return this;
        }

        public Builder setNrPBKDF2Iterations(int nrPBKDF2Iterations) {
            this.nrPBKDF2Iterations = nrPBKDF2Iterations;
            return this;
        }

        public PBKDF2PasswordMaster createPasswordMaster() {
            return new PBKDF2PasswordMaster(saltGenerator, saltByteSize, hashByteSize, nrPBKDF2Iterations);
        }
    }
}
