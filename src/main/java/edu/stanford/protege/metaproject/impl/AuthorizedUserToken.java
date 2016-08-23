package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.AuthToken;
import edu.stanford.protege.metaproject.api.User;

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
public final class AuthorizedUserToken implements AuthToken, Serializable {
    private static final long serialVersionUID = -7528862656544274939L;
    @Nonnull private final User user;

    /**
     * Constructor
     *
     * @param user    User identifier
     */
    public AuthorizedUserToken(@Nonnull User user) {
        this.user = checkNotNull(user);
    }

    @Override
    @Nonnull
    public User getUser() {
        return user;
    }

    @Override
    public boolean isAuthorized() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthToken)) {
            return false;
        }
        AuthToken that = (AuthToken) o;
        return Objects.equal(user, that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(user);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("user", user)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull AuthToken that) {
        return ComparisonChain.start()
                .compare(this.user.getId().get(), that.getUser().getId().get())
                .result();
    }
}
