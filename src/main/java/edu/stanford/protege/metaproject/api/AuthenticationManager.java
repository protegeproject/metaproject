package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UserIdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;

import java.util.Set;

/**
 * Manager for user authentication; handles the (modification of the) registration of users in the server, and the verification
 * of credentials for accessing the server. The AuthenticationManager maintains a collection of user authentication details, which
 * map a user identifier to a (salted) password. Credential verification is done against the given password validator.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface AuthenticationManager {

    /**
     * Register a user in the user registry
     *
     * @param userId  User identifier
     * @param password  Password
     * @throws UserIdAlreadyInUseException   User is already registered
     */
    void add(UserId userId, SaltedPasswordDigest password) throws UserIdAlreadyInUseException;

    /**
     * Remove user from the authentication registry (the user will not be able to login)
     *
     * @param userId  User identifier
     * @throws UserNotRegisteredException   User is not registered
     */
    void remove(UserId userId) throws UserNotRegisteredException;

    /**
     * Get all entries of user authentication details
     *
     * @return Set of user authentication details
     */
    Set<AuthenticationDetails> getAuthenticationDetails();

    /**
     * Get the authentication details for a user with the given identifier
     *
     * @param userId    User identifier
     * @return Authentication details
     * @throws UserNotRegisteredException   User is not registered
     */
    AuthenticationDetails getAuthenticationDetails(UserId userId) throws UserNotRegisteredException;

    /**
     * Get the cryptographic salt used for hashing the given user's password
     *
     * @param userId    User identifier
     * @return Salt
     * @throws UserNotRegisteredException   User is not registered
     */
    Salt getSalt(UserId userId) throws UserNotRegisteredException;

    /**
     * Verify whether the given user-password pair is a valid (registered) one
     *
     * @param userId    User identifier
     * @param password  Password
     * @return true if user and password are valid w.r.t. the authentication details, false otherwise
     * @throws UserNotRegisteredException   User is not registered
     */
    boolean hasValidCredentials(UserId userId, SaltedPasswordDigest password) throws UserNotRegisteredException;

    /**
     * Change password of a specified user to the given password
     *
     * @param userId  User identifier
     * @param password  New password
     * @throws UserNotRegisteredException   User is not registered
     */
    void changePassword(UserId userId, SaltedPasswordDigest password) throws UserNotRegisteredException;

    /**
     * Verify whether the user with the given identifier exists in the registry
     *
     * @param userId    User identifier
     * @return true if the identifier corresponds to an existing, registered user, false otherwise
     */
    boolean contains(UserId userId);

}
