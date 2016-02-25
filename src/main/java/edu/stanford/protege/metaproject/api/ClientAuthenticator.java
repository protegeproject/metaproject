package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;

/**
 * A client-side authenticator that relies on a plain password and user identifier.
 * The given details are validated against the specified user authenticator.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ClientAuthenticator {

    /**
     * Verify whether the given username and password are valid against the given authenticator
     *
     * @param userId    User identifier
     * @param password  Plain password
     * @param userAuthenticator Credential authenticator
     * @throws UserNotRegisteredException  User is not registered
     * @return true if user credentials are valid, false otherwise
     */
    boolean hasValidCredentials(UserId userId, PlainPassword password, UserAuthenticator userAuthenticator) throws UserNotRegisteredException;

}
