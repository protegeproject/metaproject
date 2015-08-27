package edu.stanford.protege.metaproject.api;

import java.util.Optional;

/**
 * A representation of user authentication details, consisting of the unique user identifier,
 * the salted password, and the salt used in the password hashing function
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface UserAuthenticationDetails extends Comparable<UserAuthenticationDetails> {

    /**
     * Get the user identifier
     *
     * @return User identifier
     */
    UserId getUserId();

    /**
     * Get the salted password instance
     *
     * @return Salted password
     */
    SaltedPassword getPassword();

    /**
     * Get the salt used for password hashing
     *
     * @return Salt
     */
    Optional<Salt> getSalt();

}
