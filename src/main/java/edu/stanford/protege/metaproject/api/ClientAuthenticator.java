package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;

/**
 * A client-side authenticator that relies on a plain password and user identifier.
 * The given details are validated against the specified user authenticator, which
 * may be the default user authenticator or another such as LDAP.
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
     * @return Authorisation token that represents the state of user credential verification
     */
    AuthToken hasValidCredentials(UserId userId, PlainPassword password, UserAuthenticator userAuthenticator) throws UserNotRegisteredException;

}
