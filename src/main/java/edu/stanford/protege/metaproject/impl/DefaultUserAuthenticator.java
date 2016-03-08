package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.ServerConfigurationNotLoadedException;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class DefaultUserAuthenticator implements UserAuthenticator {
    private final Logger logger = LoggerFactory.getLogger(DefaultUserAuthenticator.class.getName());

    /**
     * No-args constructor
     */
    public DefaultUserAuthenticator() { }

    @Override
    public AuthToken hasValidCredentials(UserId userId, PlainPassword password) throws UserNotRegisteredException {
        AuthenticationManager authenticationManager;
        try {
            authenticationManager = Manager.getServerConfiguration().getAuthenticationManager();
            Salt salt = authenticationManager.getSalt(userId); // get salt used to hash user's password

            final PasswordHasher passwordHasher = Manager.getFactory().createPasswordHasher();
            // hash given password (with same salt as upon registration) before checking with the authentication manager
            SaltedPasswordDigest saltedPasswordDigest = passwordHasher.hash(password, salt);
            if(authenticationManager.hasValidCredentials(userId, saltedPasswordDigest)) {
                return new AuthorizedUserToken(userId);
            }
        } catch (ServerConfigurationNotLoadedException e) {
            logger.error("Unable to authenticate user against the server authentication manager " +
                    "because the server configuration has not been loaded.");
        }
        return new UnauthorizedUserToken(userId);
    }
}
