package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * A representation of a user, consisting of a unique identifier used to log in, a display name, and an email address
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface User extends PolicyObject<UserId>, Comparable<User> {

    /**
     * Get user's email address
     *
     * @return Email address
     */
    @Nonnull
    EmailAddress getEmailAddress();

}
