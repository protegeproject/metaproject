package edu.stanford.protege.metaproject.api;

/**
 * A representation of a salted password, consisting of the (string) hashed password,
 * and the salt used to hash the password
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface SaltedPassword extends Password {

    /**
     * Get the salt used in for hashing this password
     *
     * @return Salt used in hashing function
     */
    Salt getSalt();

}
