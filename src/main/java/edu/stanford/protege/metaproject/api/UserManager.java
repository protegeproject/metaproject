package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.AccessControlObjectNotFoundException;
import edu.stanford.protege.metaproject.api.exception.UserAddressAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserAlreadyRegisteredException;
import edu.stanford.protege.metaproject.api.exception.UserNotFoundException;

import java.util.Set;

/**
 * A manager for users and user details (except password, which is handled by {@link AuthenticationManager}).
 * The user manager follows the singleton pattern; only one instance of this manager may exist.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface UserManager extends Manager {

    /**
     * Add user(s)
     *
     * @param user  One or more users
     * @throws UserAddressAlreadyInUseException Email address already in use by another user
     * @throws UserAlreadyRegisteredException   Identifier of given user is already in use
     */
    void add(User... user) throws UserAddressAlreadyInUseException, UserAlreadyRegisteredException;

    /**
     * Remove the given user(s)
     *
     * @param user One or more users
     * @throws UserNotFoundException  User does not exist
     */
    void remove(User... user) throws AccessControlObjectNotFoundException;

    /**
     * Get all users
     *
     * @return Set of all users
     */
    Set<User> getUsers();

    /**
     * Get the user(s) registered with the specified name
     *
     * @param userName  User name instance
     * @return Set of users with given name
     */
    Set<User> getUsers(Name userName);

    /**
     * Get the user(s) registered with the specified email address
     *
     * @param emailAddress  Email address
     * @return Set of users
     */
    Set<User> getUsers(Address emailAddress);

    /**
     * Get a guest user instance
     *
     * @return Guest user
     */
    User getGuestUser();

    /**
     * A convenience method to fetch a user or die trying (with an exception)
     *
     * @param userId    User identifier
     * @return User instance
     * @throws UserNotFoundException    User not found
     */
    User getUser(UserId userId) throws UserNotFoundException;

    /**
     * Change the display name of the given user
     *
     * @param userId    User identifier
     * @param userName  New name
     * @throws UserNotFoundException  User does not exist
     * @throws UserAddressAlreadyInUseException Email address already in use by another user
     * @throws UserAlreadyRegisteredException   Identifier of given user is already in use
     */
    void changeUserName(UserId userId, Name userName) throws UserNotFoundException, UserAddressAlreadyInUseException, UserAlreadyRegisteredException;

    /**
     * Change the email address of a user
     *
     * @param userId  User identifier
     * @param emailAddress New email address
     * @throws UserNotFoundException  User does not exist
     * @throws UserAddressAlreadyInUseException Email address already in use by another user
     * @throws UserAlreadyRegisteredException   Identifier of given user is already in use
     */
    void changeEmailAddress(UserId userId, Address emailAddress) throws UserNotFoundException, UserAddressAlreadyInUseException, UserAlreadyRegisteredException;

}
