package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.UserAddressAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserAlreadyRegisteredException;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A manager for everything authentication-related
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AuthenticationManager implements Manager, Serializable {
    private static final long serialVersionUID = -4147595425630794400L;
    private Set<UserAuthenticationDetails> userAuthenticationDetails = new HashSet<>();
    private PasswordMaster passwordMaster = new PBKDF2PasswordMaster.Builder().createPasswordMaster();

    /**
     * Constructor
     *
     * @param userAuthenticationDetails   Set of user authentication details
     */
    public AuthenticationManager(Set<UserAuthenticationDetails> userAuthenticationDetails) {
        this.userAuthenticationDetails = checkNotNull(userAuthenticationDetails);
    }

    /**
     * No-arguments constructor for a new, empty policy
     */
    public AuthenticationManager() { }

    /**
     * Verify whether the given user-password pair is a valid (registered) one
     *
     * @param userId    User identifier
     * @param password  Password
     * @return true if user and password are valid w.r.t. the authentication details, false otherwise
     * @throws UserNotRegisteredException   User is not registered
     */
    public boolean hasValidCredentials(UserId userId, PlainPassword password) throws UserNotRegisteredException {
        UserAuthenticationDetails userDetails = getUserDetails(userId);
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
    public void registerUser(UserId userId, PlainPassword password) throws UserAlreadyRegisteredException, UserAddressAlreadyInUseException {
        if(isRegistered(userId)) {
            throw new UserAlreadyRegisteredException("The specified user is already registered with the authentication protocol. Recover or change the password.");
        }
        userAuthenticationDetails.add(getHashedUserAuthenticationDetails(userId, password));
    }

    /**
     * Remove user from the authentication registry (the user will not be able to login)
     *
     * @param userId  User identifier
     * @throws UserNotRegisteredException   User is not registered
     */
    public void removeUser(UserId userId) throws UserNotRegisteredException {
        UserAuthenticationDetails toDelete = getUserDetails(userId);
        userAuthenticationDetails.remove(toDelete);
    }

    /**
     * Change password of a specified user to the given password
     *
     * @param userId  User identifier
     * @param password  New password
     * @throws UserNotRegisteredException   User is not registered
     */
    public void changePassword(UserId userId, PlainPassword password) throws UserNotRegisteredException {
        UserAuthenticationDetails userDetails = getUserDetails(userId);
        changePassword(userDetails, password);
    }

    public void changePassword(UserAuthenticationDetails userDetails, PlainPassword password) {
        userAuthenticationDetails.remove(userDetails);
        UserAuthenticationDetails newUserDetails = getHashedUserAuthenticationDetails(userDetails.getUserId(), password);
        userAuthenticationDetails.add(newUserDetails);
    }

    /**
     * Get the authentication details of the user with the given user identifier
     *
     * @param userId    User identifier
     * @return Authentication details of user
     * @throws UserNotRegisteredException   User is not registered
     */
    private UserAuthenticationDetails getUserDetails(UserId userId) throws UserNotRegisteredException {
        UserAuthenticationDetails details = null;
        for(UserAuthenticationDetails userDetails : userAuthenticationDetails) {
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
    public boolean isRegistered(UserId userId) {
        for(UserAuthenticationDetails userDetails : userAuthenticationDetails) {
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
    private UserAuthenticationDetails getHashedUserAuthenticationDetails(UserId userId, Password password) {
        SaltedPassword passwordHash = passwordMaster.createHash(password.getPassword());
        return new HashedUserAuthenticationDetails(userId, passwordHash, Optional.of(passwordHash.getSalt()));
    }

    /**
     * Get all entries of user authentication details
     *
     * @return Set of user authentication details
     */
    public Set<UserAuthenticationDetails> getUserAuthenticationDetails() {
        return userAuthenticationDetails;
    }

    @Override
    public boolean exists(Id userId) {
        for(UserAuthenticationDetails userDetails : userAuthenticationDetails) {
            if(userDetails.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }
}
