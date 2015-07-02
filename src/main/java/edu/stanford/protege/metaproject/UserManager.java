package edu.stanford.protege.metaproject;

import java.util.Set;

/**
 * A manager for users and user details
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface UserManager {

    /**
     * Get all users
     *
     * @return Set of all users
     */
    Set<User> getUsers();

    /**
     * Get the user with the specified username
     *
     * @param userId  Username instance
     * @return User instance
     */
    User getUser(UserId userId);

    /**
     * Set the username of a given user
     *
     * @param user  User instance
     * @param userId  Username instance
     */
    void setUsername(User user, UserId userId);

    /**
     * Get the email of a user
     *
     * @param user  User instance
     * @return Email address
     */
    EmailAddress getEmailAddress(User user);

    /**
     * Set the email address of a user
     *
     * @param user  User instance
     * @param email Email address instance
     */
    void setEmailAddress(User user, EmailAddress email);

}
