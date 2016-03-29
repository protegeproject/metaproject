package edu.stanford.protege.metaproject.api;

/**
 * A user authenticator that verifies whether the given username and password are valid.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface UserAuthenticator {

    /**
     * Verify whether the given user and password are valid. The idea is that this takes a user identifier
     * and a plain password, (presumably) encrypts the password, and ships the user identifier and salted password to
     * some authentication handler that should return an authorization token.
     *
     * @param userId    User identifier
     * @param password  Password
     * @return Authorisation token that represents the state of user credential verification
     */
    AuthToken hasValidCredentials(UserId userId, PlainPassword password);

}
