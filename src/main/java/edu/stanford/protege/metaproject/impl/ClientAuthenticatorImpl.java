package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.ClientAuthenticator;
import edu.stanford.protege.metaproject.api.PlainPassword;
import edu.stanford.protege.metaproject.api.UserAuthenticator;
import edu.stanford.protege.metaproject.api.UserId;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ClientAuthenticatorImpl implements ClientAuthenticator {

    public ClientAuthenticatorImpl() { }

    @Override
    public boolean hasValidCredentials(UserId userId, PlainPassword password, UserAuthenticator userAuthenticator) throws UserNotRegisteredException {
        return userAuthenticator.hasValidCredentials(userId, password);
    }
    
}
