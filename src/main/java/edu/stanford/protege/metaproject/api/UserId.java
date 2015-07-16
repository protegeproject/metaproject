package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of a unique user identifier that is used for logging in to the server
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class UserId extends Identifier implements Serializable {
    private static final long serialVersionUID = 2356034050705038117L;
    private final String userId;

    /**
     * Constructor
     *
     * @param userId    User identifier
     */
    public UserId(String userId) {
        this.userId = checkNotNull(userId);
    }

    /**
     * Get the user identifier
     *
     * @return User identifier
     */
    public String getId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId1 = (UserId) o;
        return Objects.equal(userId, userId1.userId);
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
}
