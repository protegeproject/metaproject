package edu.stanford.protege.metaproject.api;

/**
 * A representation of a user, consisting of a unique identifier used to log in, a display name, and an email address
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface User extends PolicyObject<UserId>, Comparable<User> {

    /**
     * Get user's email address
     *
     * @return Email address
     */
    EmailAddress getEmailAddress();

}
