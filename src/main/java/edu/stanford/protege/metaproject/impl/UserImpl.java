package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.EmailAddress;
import edu.stanford.protege.metaproject.api.Name;
import edu.stanford.protege.metaproject.api.User;
import edu.stanford.protege.metaproject.api.UserId;

import javax.annotation.Nonnull;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Representation of a user, consisting of a unique identifier used to log in, a display name, and an email address
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class UserImpl implements User, Serializable, Comparable<User> {
    private static final long serialVersionUID = 8008446184946348594L;
    private final UserId id;
    private final Name name;
    private final EmailAddress emailAddress;

    /**
     * Constructor
     *
     * @param id    User identifier
     * @param name  User display name
     * @param emailAddress  Email address
     */
    public UserImpl(UserId id, Name name, EmailAddress emailAddress) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.emailAddress = checkNotNull(emailAddress);
    }

    /**
     * Get user's identifier that is used for logging in
     *
     * @return User identifier
     */
    @Override
    public UserId getId() {
        return id;
    }

    /**
     * Get user's name as used for displaying purposes
     *
     * @return User name
     */
    @Override
    public Name getName() {
        return name;
    }

    /**
     * Get user's email address
     *
     * @return Email address instance
     */
    @Override
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserImpl user = (UserImpl) o;
        return Objects.equal(id, user.id) &&
                Objects.equal(name, user.name) &&
                Objects.equal(emailAddress, user.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, emailAddress);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("emailAddress", emailAddress)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull User that) {
        return ComparisonChain.start()
                .compare(this.id.get(), that.getId().get())
                .result();
    }
}
