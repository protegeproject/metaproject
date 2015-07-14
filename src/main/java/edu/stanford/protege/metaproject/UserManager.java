package edu.stanford.protege.metaproject;

import java.util.Set;

/**
 * A manager for users and user details (except password, which is handled by {@link AuthenticationManager})
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface UserManager {

    /**
     * Add a user
     *
     * @param user  User instance
     */
    void addUser(User user);

    /**
     * Remove a user
     *
     * @param user User instance
     */
    void removeUser(User user);

    /**
     * Get all users
     *
     * @return Set of all users
     */
    Set<User> getUsers();

    /**
     * Get the user with the specified identifier
     *
     * @param userId  User identifier
     * @return User instance
     */
    User getUser(UserId userId);

    /**
     * Get the user(s) registered with the specified name
     *
     * @param userName  User name
     * @return Set of users
     */
    Set<User> getUsers(UserName userName);

    /**
     * Get the user(s) registered with the specified email address
     *
     * @param emailAddress  Email address
     * @return Set of users
     */
    Set<User> getUsers(EmailAddress emailAddress);

    /**
     * Change the unique identifier of a given user
     *
     * @param user  User instance
     * @param userId  New user identifier
     */
    void changeUserId(User user, UserId userId);

    /**
     * Change the display name of the given user
     *
     * @param user    User instance
     * @param userName  New name
     */
    void changeUserName(User user, UserName userName);

    /**
     * Change the email address of a user
     *
     * @param user  User instance
     * @param emailAddress New email address
     */
    void changeEmailAddress(User user, EmailAddress emailAddress);

}
