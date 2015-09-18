package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.EmailAddressAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserIdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;

import java.io.Serializable;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AuthenticationManagerImpl implements AuthenticationManager, Serializable {
    private static final long serialVersionUID = 6714308634662458905L;
    private Set<AuthenticationDetails> authenticationDetails;

    /**
     * Constructor
     *
     * @param authenticationDetails   Set of user authentication details
     */
    public AuthenticationManagerImpl(Set<AuthenticationDetails> authenticationDetails) {
        this.authenticationDetails = checkNotNull(authenticationDetails);
    }

    @Override
    public boolean hasValidCredentials(UserId userId, SaltedPasswordDigest password) throws UserNotRegisteredException {
        SaltedPasswordDigest correctHash = getAuthenticationDetails(userId).getPassword();
        return slowEquals(password.getPassword().getBytes(), correctHash.getPassword().getBytes());
    }

    @Override
    public void add(UserId userId, SaltedPasswordDigest password) throws UserIdAlreadyInUseException, EmailAddressAlreadyInUseException {
        if(contains(userId)) {
            throw new UserIdAlreadyInUseException("The specified user is already registered with the authentication manager. Recover or change the password.");
        }
        authenticationDetails.add(getAuthenticationDetails(userId, password));
    }

    @Override
    public void remove(UserId userId) throws UserNotRegisteredException {
        AuthenticationDetails toDelete = getAuthenticationDetails(userId);
        authenticationDetails.remove(toDelete);
    }

    @Override
    public void changePassword(UserId userId, SaltedPasswordDigest password) throws UserNotRegisteredException {
        authenticationDetails.remove(getAuthenticationDetails(userId));
        AuthenticationDetails newUserDetails = getAuthenticationDetails(userId, password);
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

    /**
     * Get an instance of UserAuthenticationDetails using the specified user identifier and password
     *
     * @param userId    User identifier
     * @param password  Password
     * @return Instance of UserAuthenticationDetails
     */
    private AuthenticationDetails getAuthenticationDetails(UserId userId, SaltedPasswordDigest password) {
        return new AuthenticationDetailsImpl(userId, password);
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
    public boolean contains(AccessControlObjectId userId) {
        for(AuthenticationDetails userDetails : authenticationDetails) {
            if(userDetails.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationManagerImpl that = (AuthenticationManagerImpl) o;
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
