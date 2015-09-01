package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.UserAddressAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserAlreadyRegisteredException;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Manager for user authentication; handles the (modification of the) registration of users in the server, and the verification
 * of credentials for accessing the server. The AuthenticationManager maintains a collection of user authentication details
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AuthenticationManagerImpl implements AuthenticationManager, Serializable {
    private static final long serialVersionUID = 3744980989244926797L;
    private final PasswordMaster passwordMaster = new PBKDF2PasswordMaster.Builder().createPasswordMaster();
    private Set<AuthenticationDetails> authenticationDetails = new HashSet<>();

    /**
     * Constructor
     *
     * @param authenticationDetails   Set of user authentication details
     */
    public AuthenticationManagerImpl(Set<AuthenticationDetails> authenticationDetails) {
        this.authenticationDetails = checkNotNull(authenticationDetails);
    }

    /**
     * No-arguments constructor for a new, empty policy
     */
    public AuthenticationManagerImpl() { }

    /**
     * Verify whether the given user-password pair is a valid (registered) one
     *
     * @param userId    User identifier
     * @param password  Password
     * @return true if user and password are valid w.r.t. the authentication details, false otherwise
     * @throws UserNotRegisteredException   User is not registered
     */
    @Override
    public boolean hasValidCredentials(UserId userId, PlainPassword password) throws UserNotRegisteredException {
        AuthenticationDetails userDetails = getAuthenticationDetails(userId);
        return passwordMaster.validatePassword(password, userDetails.getPassword());
    }

    /**
     * Register a user in the user registry
     *
     * @param userId  User identifier
     * @param password  Plain text password
     * @throws UserAlreadyRegisteredException   User is already registered
     * @throws UserAddressAlreadyInUseException Email address is already in use by another user
     */
    @Override
    public void registerUser(UserId userId, PlainPassword password) throws UserAlreadyRegisteredException, UserAddressAlreadyInUseException {
        if(isRegistered(userId)) {
            throw new UserAlreadyRegisteredException("The specified user is already registered with the authentication protocol. Recover or change the password.");
        }
        authenticationDetails.add(getHashedAuthenticationDetails(userId, password));
    }

    /**
     * Remove user from the authentication registry (the user will not be able to login)
     *
     * @param userId  User identifier
     * @throws UserNotRegisteredException   User is not registered
     */
    @Override
    public void removeUser(UserId userId) throws UserNotRegisteredException {
        AuthenticationDetails toDelete = getAuthenticationDetails(userId);
        authenticationDetails.remove(toDelete);
    }

    /**
     * Change password of a specified user to the given password
     *
     * @param userId  User identifier
     * @param password  New password
     * @throws UserNotRegisteredException   User is not registered
     */
    @Override
    public void changePassword(UserId userId, PlainPassword password) throws UserNotRegisteredException {
        AuthenticationDetails userDetails = getAuthenticationDetails(userId);
        changePassword(userDetails, password);
    }

    /**
     * Change password of the user to the given password, based on the specified authentication details
     *
     * @param userDetails   User authentication details
     * @param password  New password
     */
    @Override
    public void changePassword(AuthenticationDetails userDetails, PlainPassword password) {
        authenticationDetails.remove(userDetails);
        AuthenticationDetails newUserDetails = getHashedAuthenticationDetails(userDetails.getUserId(), password);
        authenticationDetails.add(newUserDetails);
    }

    /**
     * Get the authentication details of the user with the given user identifier
     *
     * @param userId    User identifier
     * @return Authentication details of user
     * @throws UserNotRegisteredException   User is not registered
     */
    public AuthenticationDetails getAuthenticationDetails(UserId userId) throws UserNotRegisteredException {
        AuthenticationDetails details = null;
        for(AuthenticationDetails userDetails : authenticationDetails) {
            if (userDetails.getUserId().equals(userId)) {
                details = userDetails;
                break;
            }
        }
        if(details == null) {
            throw new UserNotRegisteredException("The specified user identifier does not correspond to a registered user.");
        }
        return details;
    }

    /**
     * Check whether a given user is already registered
     *
     * @param userId  User identifier
     * @return true if user is registered, false otherwise
     */
    @Override
    public boolean isRegistered(UserId userId) {
        for(AuthenticationDetails userDetails : authenticationDetails) {
            if(userDetails.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get an instance of UserAuthenticationDetails using the specified user identifier, password and salt
     *
     * @param userId    User identifier
     * @param password  Password
     * @return Instance of UserAuthenticationDetails
     */
    private AuthenticationDetails getHashedAuthenticationDetails(UserId userId, Password password) {
        SaltedPassword passwordHash = passwordMaster.createHash(password.getPassword());
        return new AuthenticationDetailsImpl(userId, passwordHash);
    }

    /**
     * Get all entries of user authentication details
     *
     * @return Set of user authentication details
     */
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
                .add("userAuthenticationDetails", authenticationDetails)
                .toString();
    }
}
