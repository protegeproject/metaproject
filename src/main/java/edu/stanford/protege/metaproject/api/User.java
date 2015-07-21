package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Representation of a user, consisting of a unique identifier used to log in, a display name, and an email address
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class User implements Serializable, HasDetails, HasEmailAddress {
    private static final long serialVersionUID = 426611184351097983L;
    private final UserId userId;
    private final UserName userName;
    private final EmailAddress emailAddress;

    /**
     * Constructor
     *
     * @param userId    User identifier
     * @param userName  User display name
     * @param emailAddress  Email address
     */
    public User(UserId userId, UserName userName, EmailAddress emailAddress) {
        this.userId = checkNotNull(userId);
        this.userName = checkNotNull(userName);
        this.emailAddress = checkNotNull(emailAddress);
    }

    /**
     * Get user's identifier that is used for logging in
     *
     * @return User identifier
     */
    public UserId getId() {
        return userId;
    }

    /**
     * Get user's name as used for displaying purposes
     *
     * @return User name
     */
    public UserName getName() {
        return userName;
    }

    /**
     * Get user's email address
     *
     * @return Email address instance
     */
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(userId, user.userId) &&
                Objects.equal(userName, user.userName) &&
                Objects.equal(emailAddress, user.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, userName, emailAddress);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("userName", userName)
                .add("emailAddress", emailAddress)
                .toString();
    }
}
