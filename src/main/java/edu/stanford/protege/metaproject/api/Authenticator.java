package edu.stanford.protege.metaproject.api;

/**
 * A representation of an authenticator
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Authenticator {

    /**
     * Check whether the given username and password combination is a valid one
     *
     * @param userId  User unique identifier
     * @param password  User password
     * @return true if user-password combination is valid, false otherwise
     */
    boolean hasValidCredentials(UserId userId, String password);

}
