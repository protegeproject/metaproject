package edu.stanford.protege.metaproject;

/**
 * Representation of a user, consisting of a unique identifier used to log in, a display name, and an email address
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface User {

    /**
     * Get user's identifier that is used for logging in
     *
     * @return User identifier
     */
    UserId getId();

    /**
     * Get user's name as used for displaying purposes
     *
     * @return User name
     */
    UserName getDisplayName();

    /**
     * Get user's email address
     *
     * @return Email address instance
     */
    EmailAddress getEmailAddress();

}
