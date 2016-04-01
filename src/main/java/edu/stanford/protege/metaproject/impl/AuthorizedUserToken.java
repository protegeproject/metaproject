package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.AuthToken;
import edu.stanford.protege.metaproject.api.User;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class AuthorizedUserToken implements AuthToken, Serializable {
    private static final long serialVersionUID = -7742639350166955863L;
    private final User user;
    private final String secret;

    /**
     * Constructor
     *
     * @param user    User identifier
     */
    public AuthorizedUserToken(User user) {
        this.user = checkNotNull(user);
        this.secret = UUID.randomUUID().toString();
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public boolean isAuthorized() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizedUserToken that = (AuthorizedUserToken) o;
        return Objects.equal(user, that.user) &&
                Objects.equal(secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(user, secret);
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
