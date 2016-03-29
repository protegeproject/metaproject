package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UnknownUserIdException;
import edu.stanford.protege.metaproject.api.exception.UserIdAlreadyInUseException;

import java.util.Set;

/**
 * A manager for users and user details (except password, which is handled by {@link AuthenticationRegistry}).
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface UserRegistry {

    /**
     * Add user(s)
     *
     * @param user  One or more users
     * @throws UserIdAlreadyInUseException   Identifier of given user is already in use
     */
    void add(User... user) throws UserIdAlreadyInUseException;

    /**
     * Remove the given user(s)
     *
     * @param user One or more users
     */
    void remove(User... user);

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
     * @throws UnknownUserIdException    User identifier is not recognized
     */
    User getUser(UserId userId) throws UnknownUserIdException;

    /**
     * Change the display name of the given user
     *
     * @param userId    User identifier
     * @param userName  New name
     * @throws UnknownUserIdException  User identifier is not recognized
     */
    void setName(UserId userId, Name userName) throws UnknownUserIdException;

    /**
     * Change the email address of a user
     *
     * @param userId  User identifier
     * @param emailAddress New email address
     * @throws UnknownUserIdException  User identifier is not recognized
     */
    void setEmailAddress(UserId userId, EmailAddress emailAddress) throws UnknownUserIdException;

    /**
     * Verify whether the email address of the given user is already being used by another user
     *
     * @param address   User address
     * @return true if email address is used by some other user, false otherwise
     */
    boolean isEmailAddressInUse(EmailAddress address);

    /**
     * Check whether the user registry contains a user with the given identifier
     *
     * @param userId    User identifier
     * @return true if there is a user with the specified identifier, false otherwise
     */
    boolean contains(UserId userId);

}
