package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.UserAddressAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserAlreadyRegisteredException;
import edu.stanford.protege.metaproject.api.exception.UserNotFoundException;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A manager for everything authentication-related
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AuthenticationManager implements Manager {
    private static AuthenticationManager instance = null;
    private static Set<UserAuthenticationDetails> userAuthenticationDetails;
    private UserManager userManager = UserManager.getInstance();

    /**
     * Private constructor
     *
     * @param userAuthenticationDetails   Set of user authentication details
     */
    private AuthenticationManager(Set<UserAuthenticationDetails> userAuthenticationDetails) {
        this.userAuthenticationDetails = checkNotNull(userAuthenticationDetails);
    }

    /**
     * Get the singleton instance of the authentication manager with the supplied set of user
     * authentication details
     *
     * @param userAuthDetails   Set of user authentication details
     * @return Authentication manager
     */
    public static AuthenticationManager getInstance(Set<UserAuthenticationDetails> userAuthDetails) {
        if(instance == null || !userAuthenticationDetails.equals(userAuthDetails)) {
            instance = new AuthenticationManager(userAuthDetails);
        }
        return instance;
    }

    /**
     * Get the singleton instance of authentication manager
     *
     * @return Authentication manager
     */
    public static AuthenticationManager getInstance() {
        if(instance == null) {
            instance = new AuthenticationManager(new HashSet<>());
        }
        return instance;
    }

    /**
     * Verify whether the given user-password pair is a valid (registered) one
     *
     * @param userId    User identifier
     * @param password  Password
     * @return true if user and password are valid w.r.t. the authentication details, false otherwise
     * @throws UserNotRegisteredException   User is not registered
     */
    public boolean hasValidCredentials(Id userId, Password password) throws UserNotRegisteredException {
        UserAuthenticationDetails userDetails = getUserDetails(userId);
        if(userDetails.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    /**
     * Register a user in the user registry
     *
     * @param user  User
     * @param password  Password
     * @param salt  Salt
     * @throws UserAlreadyRegisteredException   User is already registered
     * @throws UserAddressAlreadyInUseException User email address is already in use by another user
     */
    public void registerUser(Id user, Password password, Salt salt) throws UserAlreadyRegisteredException, UserAddressAlreadyInUseException {
        if(isRegistered(user)) {
            throw new UserAlreadyRegisteredException("The specified user is already registered with the authentication protocol. Recover or change the password.");
        }
        if(isAddressUsed(user)) {
            throw new UserAddressAlreadyInUseException("The specified user address is already used for authentication purposes by another user.");
        }
        userAuthenticationDetails.add(getUserAuthenticationDetails(user, password, salt));
    }

    /**
     * Remove user from the authentication registry (the user will not be able to login)
     *
     * @param userId  User identifier
     * @throws UserNotRegisteredException   User is not registered
     */
    public void removeUser(Id userId) throws UserNotRegisteredException {
        if(!isRegistered(userId)) {
            throw new UserNotRegisteredException("The specified user has not been registered.");
        }
        UserAuthenticationDetails toDelete = null;
        for(UserAuthenticationDetails userDetails : userAuthenticationDetails) {
            if(userDetails.getId().equals(userId)) {
                toDelete = userDetails;
            }
        }
        userAuthenticationDetails.remove(toDelete);
    }

    /**
     * Change password of a specified user to the given password
     *
     * @param userId  User identifier
     * @param password  New password
     * @throws UserNotRegisteredException   User is not registered
     */
    public void changePassword(Id userId, Password password) throws UserNotRegisteredException {
        if(!isRegistered(userId)) {
            throw new UserNotRegisteredException("The specified user has not been registered.");
        }
        UserAuthenticationDetails toDelete = null;
        for(UserAuthenticationDetails userDetails : userAuthenticationDetails) {
            if(userDetails.getId().equals(userId)) {
                toDelete = userDetails;
            }
        }
        userAuthenticationDetails.remove(toDelete);
        userAuthenticationDetails.add(getUserAuthenticationDetails(userId, password, toDelete.getSalt()));
    }

    /**
     * Get the authentication details of the user with the given user identifier
     *
     * @param userId    User identifier
     * @return Authentication details of user
     * @throws UserNotRegisteredException   User is not registered
     */
    private UserAuthenticationDetails getUserDetails(Id userId) throws UserNotRegisteredException {
        UserAuthenticationDetails details = null;
        for(UserAuthenticationDetails userDetails : userAuthenticationDetails) {
            if (userDetails.getId().equals(userId)) {
                details = userDetails;
                break;
            }
        }
        if(details == null) {
            throw new UserNotRegisteredException("The specified user identifier does not correspond" +
                    "to a register user.");
        }
        return details;
    }

    /**
     * Verify whether a given user is already registered
     *
     * @param userId  User identifier
     * @return true if user is registered, false otherwise
     */
    public boolean isRegistered(Id userId) {
        for(UserAuthenticationDetails userDetails : userAuthenticationDetails) {
            if(userDetails.getId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verify whether the email address of the given user is already being used by another user
     *
     * @param id   User identifier
     * @return true if email address is used by some other user, false otherwise
     */
    public boolean isAddressUsed(Id id) {
        User user = null;
        try {
            user = userManager.getUser(id);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        Address email = user.getAddress();
        for(User u : userManager.getUsers()) {
            if(u.getAddress().equals(email)) {
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
     * @param salt  Salt
     * @return Instance of UserAuthenticationDetails
     */
    private UserAuthenticationDetails getUserAuthenticationDetails(Id userId, Password password, Salt salt) {
        return new UserAuthenticationDetailsImpl(userId, password, salt);
    }

    @Override
    public boolean exists(Id userId) {
        for(UserAuthenticationDetails userDetails : userAuthenticationDetails) {
            if(userDetails.getId().equals(userId)) {
                return true;
            }
        }
        return false;
    }
}
