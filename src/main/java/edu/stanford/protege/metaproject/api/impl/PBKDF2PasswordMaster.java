package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A password hash generator based on PBKDF2 (Password-Based Key Derivation Function 2)
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PBKDF2PasswordMaster implements PasswordMaster {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    private final SaltGenerator saltGenerator;
    private final int hashByteSize, nrPBKDF2Iterations;

    /**
     * Package-private constructor; use builder
     *
     * @param saltGenerator Salt generator
     * @param hashByteSize  Hash byte size
     * @param nrPBKDF2Iterations    Number of iterations
     */
    PBKDF2PasswordMaster(SaltGenerator saltGenerator, int hashByteSize, int nrPBKDF2Iterations) {
        this.saltGenerator = checkNotNull(saltGenerator);
        this.hashByteSize = checkNotNull(hashByteSize);
        this.nrPBKDF2Iterations = checkNotNull(nrPBKDF2Iterations);
    }

    /**
     * Create a salted PBKDF2 hash of the password
     *
     * @param password  Plain password to hash
     * @return Salted PBKDF2 hash of the password
     */
    @Override
    public SaltedPassword createHash(PlainPassword password) {
        Salt salt = saltGenerator.generate();
        return createHash(password, salt);
    }

    /**
     * Create a salted PBKDF2 hash of the password with the given salt
     *
     * @param password  Plain password
     * @param salt  Salt
     * @return Salted PBKDF2 hash of the password
     */
    @Override
    public SaltedPassword createHash(PlainPassword password, Salt salt) {
        return createHash(password.getPassword().toCharArray(), salt);
    }

    /**
     * Create a salted PBKDF2 hash of the password
     *
     * @param password  Character array password to hash
     * @param salt  Salt
     * @return Salted PBKDF2 hash of the password
     */
    private SaltedPassword createHash(char[] password, Salt salt) {
        byte[] saltBytes = salt.getBytes();
        byte[] hash = new byte[0];
        try {
            hash = pbkdf2(password, saltBytes, nrPBKDF2Iterations, hashByteSize);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return new SaltedPasswordImpl(Hex.encodeHexString(hash), salt);
    }

    /**
     * Validates a password using a hash
     *
     * @param password  The password to check
     * @param correctHash   The hash of the valid password
     * @return true if the password is correct, false otherwise
     */
    @Override
    public boolean validatePassword(PlainPassword password, SaltedPassword correctHash) {
        byte[] salt = correctHash.getSalt().getBytes();
        byte[] hash = fromHex(correctHash.getPassword());
        byte[] testHash = new byte[0];
        try {
            testHash = pbkdf2(password.getPassword().toCharArray(), salt, nrPBKDF2Iterations, hash.length);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return equals(hash, testHash);
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method is used so that password hashes
     * cannot be extracted from an on-line system using a timing attack and then attacked off-line
     *
     * @param a First byte array
     * @param b Second byte array
     * @return true if both byte arrays are the same, false otherwise
     */
    private boolean equals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
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
        byte[] binary = new byte[0];
        try {
            binary = Hex.decodeHex(hex.toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return binary;
    }

    /**
     * Get the salt generator
     *
     * @return Salt generator
     */
    @Override
    public SaltGenerator getSaltGenerator() {
        return saltGenerator;
    }

    /**
     * Get the byte size set for hashes
     *
     * @return Hash byte size
     */
    @Override
    public int getHashByteSize() {
        return hashByteSize;
    }

    /**
     * Get the number of PBKDF2 iterations set to be performed
     *
     * @return Number of PBKDF2 iterations
     */
    @Override
    public Optional<Integer> getNumberOfIterations() {
        return Optional.of(nrPBKDF2Iterations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PBKDF2PasswordMaster that = (PBKDF2PasswordMaster) o;
        return Objects.equal(hashByteSize, that.hashByteSize) &&
                Objects.equal(nrPBKDF2Iterations, that.nrPBKDF2Iterations) &&
                Objects.equal(saltGenerator, that.saltGenerator);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(saltGenerator, hashByteSize, nrPBKDF2Iterations);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("saltGenerator", saltGenerator)
                .add("hashByteSize", hashByteSize)
                .add("nrPBKDF2Iterations", nrPBKDF2Iterations)
                .toString();
    }

    /**
     * @author Rafael Gonçalves <br>
     * Stanford Center for Biomedical Informatics Research
     */
    public static class Builder {
        private int hashByteSize = 24;
        private int nrPBKDF2Iterations = 1000;
        private SaltGenerator saltGenerator = new SaltGeneratorImpl();

        public Builder setSaltGenerator(SaltGenerator saltGenerator) {
            this.saltGenerator = saltGenerator;
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
            return new PBKDF2PasswordMaster(saltGenerator, hashByteSize, nrPBKDF2Iterations);
        }
    }
}
