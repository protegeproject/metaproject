package edu.stanford.protege.metaproject.user;

/**
 * Representation of a user
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface User {

    /**
     * Get user's username
     *
     * @return Username instance
     */
    UserId getId();

    /**
     * Get user's email address
     *
     * @return Email address instance
     */
    EmailAddress getEmailAddress();

}
