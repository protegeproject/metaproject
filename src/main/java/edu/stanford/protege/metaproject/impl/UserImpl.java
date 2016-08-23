package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.EmailAddress;
import edu.stanford.protege.metaproject.api.Name;
import edu.stanford.protege.metaproject.api.User;
import edu.stanford.protege.metaproject.api.UserId;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class UserImpl implements User, Serializable {
    private static final long serialVersionUID = -514737206401105074L;
    @Nonnull private final UserId id;
    @Nonnull private final Name name;
    @Nonnull private final EmailAddress emailAddress;

    /**
     * Constructor
     *
     * @param id    User identifier
     * @param name  User display name
     * @param emailAddress  Email address
     */
    public UserImpl(@Nonnull UserId id, @Nonnull Name name, @Nonnull EmailAddress emailAddress) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.emailAddress = checkNotNull(emailAddress);
    }

    @Override
    @Nonnull
    public UserId getId() {
        return id;
    }

    @Override
    @Nonnull
    public Name getName() {
        return name;
    }

    @Override
    @Nonnull
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    @Override
    public boolean isUser() {
        return true;
    }

    @Override
    public boolean isProject() {
        return false;
    }

    @Override
    public boolean isRole() {
        return false;
    }

    @Override
    public boolean isOperation() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User that = (User) o;
        return Objects.equal(id, that.getId()) &&
                Objects.equal(name, that.getName()) &&
                Objects.equal(emailAddress, that.getEmailAddress());
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
                .compare(this.name.get(), that.getName().get())
                .result();
    }
}
