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
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class UserImpl implements User, Serializable {
    private static final long serialVersionUID = -514737206401105074L;
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

    @Override
    public UserId getId() {
        return id;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
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
        if (!(o instanceof UserImpl)) {
            return false;
        }
        UserImpl that = (UserImpl) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(emailAddress, that.emailAddress);
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
