package edu.stanford.protege.metaproject.api;

import java.util.Optional;

/**
 * A representation of a password hash generator
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface PasswordHashGenerator {

    /**
     * Create a salted hash of the password
     *
     * @param password  Plain password to hash
     * @return Salted hash of the password
     */
    SaltedPassword createHash(String password);

    /**
     * Create a salted hash of the password
     *
     * @param password  String password to hash
     * @return Salted hash of the password
     */
    SaltedPassword createHash(PlainPassword password);

    /**
     * Get the byte size set for salts
     *
     * @return Salt byte size
     */
    int getSaltByteSize();

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
