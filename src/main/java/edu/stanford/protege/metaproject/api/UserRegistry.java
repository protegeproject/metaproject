package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;

import java.util.Set;

/**
 * A manager for users and user details (except password, which is handled by {@link AuthenticationRegistry}).
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface UserRegistry extends Registry<User> {

    /**
     * Get the user(s) registered with the specified name
     *
     * @param userName  User name instance
     * @return Set of users with given name
     */
    Set<User> getEntries(Name userName);

    /**
     * Get the user(s) registered with the specified email address
     *
     * @param emailAddress  Email address
     * @return Set of users
     */
    Set<User> getEntries(EmailAddress emailAddress);

    /**
     * Get a guest user instance
     *
     * @return Guest user
     */
    User getGuestUser();

    /**
     * Change the display name of the given user
     *
     * @param userId    User identifier
     * @param userName  New name
     * @throws UnknownMetaprojectObjectIdException  User identifier is not recognized
     */
    void setName(UserId userId, Name userName) throws UnknownMetaprojectObjectIdException;

    /**
     * Change the email address of a user
     *
     * @param userId  User identifier
     * @param emailAddress New email address
     * @throws UnknownMetaprojectObjectIdException  User identifier is not recognized
     */
    void setEmailAddress(UserId userId, EmailAddress emailAddress) throws UnknownMetaprojectObjectIdException;

    /**
     * Verify whether the email address of the given user is already being used by another user
     *
     * @param address   User address
     * @return true if email address is used by some other user, false otherwise
     */
    boolean isEmailAddressInUse(EmailAddress address);

}
