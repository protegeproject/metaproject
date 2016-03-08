package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.AuthToken;
import edu.stanford.protege.metaproject.api.UserId;

import javax.annotation.Nonnull;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UnauthorizedUserToken implements AuthToken, Serializable {
    private static final long serialVersionUID = -3753360956708178259L;
    private final UserId userId;

    /**
     * Constructor
     *
     * @param userId    User identifier
     */
    public UnauthorizedUserToken(UserId userId) {
        this.userId = checkNotNull(userId);
    }

    @Override
    public UserId getUserId() {
        return userId;
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
        return Objects.equal(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull AuthToken that) {
        return ComparisonChain.start()
                .compare(this.userId.get(), that.getUserId().get())
                .result();
    }
}
