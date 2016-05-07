package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.IdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AuthenticationRegistryImpl implements AuthenticationRegistry, Serializable {
    private static final long serialVersionUID = 4434402723477226069L;
    private Set<AuthenticationDetails> authenticationDetails = new HashSet<>();

    /**
     * Constructor
     *
     * @param authenticationDetails   Set of user authentication details
     */
    public AuthenticationRegistryImpl(Set<AuthenticationDetails> authenticationDetails) {
        this.authenticationDetails = checkNotNull(authenticationDetails);
    }

    @Override
    public boolean hasValidCredentials(UserId userId, SaltedPasswordDigest password) throws UserNotRegisteredException {
        SaltedPasswordDigest correctHash = getAuthenticationDetails(userId).getPassword();
        return slowEquals(password.getPassword().getBytes(), correctHash.getPassword().getBytes());
    }

    @Override
    public void add(UserId userId, SaltedPasswordDigest password) throws IdAlreadyInUseException {
        if(contains(userId)) {
            throw new IdAlreadyInUseException("The specified user is already registered with the authentication manager. Recover or change the password.");
        }
        authenticationDetails.add(createAuthenticationDetails(userId, password));
    }

    @Override
    public void remove(UserId userId) throws UserNotRegisteredException {
        AuthenticationDetails toDelete = getAuthenticationDetails(userId);
        authenticationDetails.remove(toDelete);
    }

    @Override
    public void changePassword(UserId userId, SaltedPasswordDigest password) throws UserNotRegisteredException {
        authenticationDetails.remove(getAuthenticationDetails(userId));
        AuthenticationDetails newUserDetails = createAuthenticationDetails(userId, password);
        authenticationDetails.add(newUserDetails);
    }

    @Override
    public AuthenticationDetails getAuthenticationDetails(UserId userId) throws UserNotRegisteredException {
        AuthenticationDetails details = null;
        for(AuthenticationDetails userDetails : authenticationDetails) {
            if (userDetails.getUserId().equals(userId)) {
                details = userDetails;
                break;
            }
        }
        if(details == null) {
            throw new UserNotRegisteredException("The specified user identifier does not correspond to a user registered" +
                    " with the authentication manager.");
        }
        return details;
    }

    @Override
    public Salt getSalt(UserId userId) throws UserNotRegisteredException {
        return getAuthenticationDetails(userId).getPassword().getSalt();
    }

    /**
     * Create an instance of Authentication details
     */
    private AuthenticationDetails createAuthenticationDetails(UserId userId, SaltedPasswordDigest password) {
        return Manager.getFactory().getAuthenticationDetails(userId, password);
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method is used so that password hashes
     * cannot be extracted from an on-line system using a timing attack and then attacked off-line
     *
     * @param a First byte array
     * @param b Second byte array
     * @return true if both byte arrays are the same, false otherwise
     */
    private boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    @Override
    public Set<AuthenticationDetails> getAuthenticationDetails() {
        return authenticationDetails;
    }

    @Override
    public boolean contains(UserId userId) {
        for(AuthenticationDetails userDetails : authenticationDetails) {
            if(userDetails.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthenticationRegistryImpl)) {
            return false;
        }
        AuthenticationRegistryImpl that = (AuthenticationRegistryImpl) o;
        return Objects.equal(authenticationDetails, that.authenticationDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(authenticationDetails);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("authenticationDetails", authenticationDetails)
                .toString();
    }
}
