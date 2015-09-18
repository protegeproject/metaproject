package edu.stanford.protege.metaproject.api;

/**
 * A representation of user authentication details, consisting of the unique user identifier,
 * the salted password, and the salt used in the password hashing function
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface AuthenticationDetails extends Comparable<AuthenticationDetails> {

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
    SaltedPasswordDigest getPassword();

}
