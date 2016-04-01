package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.AuthToken;
import edu.stanford.protege.metaproject.api.User;

import javax.annotation.Nonnull;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class UnauthorizedUserToken implements AuthToken, Serializable {
    private static final long serialVersionUID = -2517575146165315707L;
    private final User user;

    /**
     * Constructor
     *
     * @param user    User
     */
    public UnauthorizedUserToken(User user) {
        this.user = checkNotNull(user);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public boolean isAuthorized() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnauthorizedUserToken that = (UnauthorizedUserToken) o;
        return Objects.equal(user, that.user);
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
