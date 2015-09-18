package edu.stanford.protege.metaproject.api;

/**
 * A representation of a salt-based password hashing function
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface PasswordHasher {

    /**
     * Create a salted hash of the password
     *
     * @param password  Plain password to hash
     * @return Salted hash of the password
     */
    SaltedPasswordDigest createHash(PlainPassword password);

    /**
     * Create a salted hash of the password with the given salt
     *
     * @param password  Plain password to hash
     * @param salt  Salt
     * @return Salted hash of the password
     */
    SaltedPasswordDigest createHash(PlainPassword password, Salt salt);

    /**
     * Hash the given password according to the specified parameters
     *
     * @param password  Password to hash
     * @param salt  Salt
     * @param iterations    Number of iterations
     * @param bytes Hash byte size
     * @return Hash byte array
     */
    byte[] hash(String password, byte[] salt, int iterations, int bytes);

    /**
     * Get the byte size set for hashes
     *
     * @return Hash byte size
     */
    int getHashByteSize();

    /**
     * Get the number of iterations set to be performed by the password stretching function
     *
     * @return Number of password-stretching iterations
     */
    int getNumberOfIterations();

}
