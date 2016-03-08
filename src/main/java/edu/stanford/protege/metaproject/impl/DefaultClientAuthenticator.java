package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class DefaultClientAuthenticator implements ClientAuthenticator {

    public DefaultClientAuthenticator() { }

    @Override
    public AuthToken hasValidCredentials(UserId userId, PlainPassword password, UserAuthenticator userAuthenticator) throws UserNotRegisteredException {
        return userAuthenticator.hasValidCredentials(userId, password);
    }
    
}
