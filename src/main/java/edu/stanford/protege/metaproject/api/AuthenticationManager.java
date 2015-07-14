package edu.stanford.protege.metaproject.api;

/**
 * A manager for everything authentication-related
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface AuthenticationManager {

    /**
     * Register a user in the user registry
     *
     * @param user  User instance
     * @param password  Password
     */
    void addUser(User user, String password);

    /**
     * Remove user from user registry
     *
     * @param user  User instance
     */
    void removeUser(User user);

    /**
     * Change password of a specified user to the given password
     *
     * @param user  User instance
     * @param password  New password
     */
    void changePassword(User user, String password);

    /**
     * Send a username reminder to specified email
     *
     * @param email Email address instance
     */
    void remindUserId(EmailAddress email);

    /**
     * Set the authentication type
     *
     * @param authenticator    Authenticator
     */
    void setAuthenticator(Authenticator authenticator);

    /**
     * Get the authenticator being used
     *
     * @return Authenticator instance
     */
    Authenticator getAuthenticator();

}
