package edu.stanford.protege.metaproject.api;

import java.util.Optional;

/**
 * A representation of a password "master" that handles password hashing as well as password validation.
 * It checks whether the given password is valid with respect to the specified (correct) password, and
 * generates password hashes
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface PasswordHandler {

    /**
     * Get the salt generator used by the password handler
     *
     * @return Salt generator
     */
    SaltGenerator getSaltGenerator();

    /**
     * Validates a password using a hash
     *
     * @param password  Plain password instance to check
     * @param correctHash   The correct salted hashed password instance
     * @return true if the password is correct, false otherwise
     */
    boolean validatePassword(PlainPassword password, SaltedPassword correctHash);

    /**
     * Create a salted hash of the password
     *
     * @param password  Plain password to hash
     * @return Salted hash of the password
     */
    SaltedPassword createHash(PlainPassword password);

    /**
     * Create a salted hash of the password with the given salt
     *
     * @param password  Plain password to hash
     * @param salt  Salt
     * @return Salted hash of the password
     */
    SaltedPassword createHash(PlainPassword password, Salt salt);

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
    Optional<Integer> getNumberOfIterations();

}
