package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.AuthenticationDetails;
import edu.stanford.protege.metaproject.api.SaltedPasswordDigest;
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
public final class AuthenticationDetailsImpl implements AuthenticationDetails, Serializable {
    private static final long serialVersionUID = 7662794049332794523L;
    @Nonnull private final UserId userId;
    @Nonnull private final SaltedPasswordDigest password;

    /**
     * Constructor
     *
     * @param userId    User identifier
     * @param password  Salted (hashed) password
     */
    public AuthenticationDetailsImpl(@Nonnull UserId userId, @Nonnull SaltedPasswordDigest password) {
        this.userId = checkNotNull(userId);
        this.password = checkNotNull(password);
    }

    @Override
    @Nonnull
    public SaltedPasswordDigest getPassword() {
        return password;
    }

    @Override
    @Nonnull
    public UserId getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(!(o instanceof AuthenticationDetails)) {
            return false;
        }
        AuthenticationDetails that = (AuthenticationDetails) o;
        return Objects.equal(userId, that.getUserId()) &&
                Objects.equal(password, that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, password);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("password", password)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull AuthenticationDetails that) {
        return ComparisonChain.start()
                .compare(this.userId.get(), that.getUserId().get())
                .result();
    }
}
