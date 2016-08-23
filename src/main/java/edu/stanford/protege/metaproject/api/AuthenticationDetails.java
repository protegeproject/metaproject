package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * A representation of user authentication details, consisting of the unique user identifier,
 * the salted password, and the salt used in the password hashing function
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface AuthenticationDetails extends Comparable<AuthenticationDetails> {

    /**
     * Get the user identifier
     *
     * @return User identifier
     */
    @Nonnull
    UserId getUserId();

    /**
     * Get the salted password instance
     *
     * @return Salted password
     */
    @Nonnull
    SaltedPasswordDigest getPassword();

}
