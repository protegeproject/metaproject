package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.AuthenticationDetails;
import edu.stanford.protege.metaproject.api.SaltedPasswordDigest;
import edu.stanford.protege.metaproject.api.UserId;

import javax.annotation.Nonnull;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class AuthenticationDetailsImpl implements AuthenticationDetails, Serializable, Comparable<AuthenticationDetails> {
    private static final long serialVersionUID = -1678322962202041927L;
    private final UserId userId;
    private final SaltedPasswordDigest password;

    /**
     * Constructor
     *
     * @param userId    User identifier
     * @param password  Salted (hashed) password
     */
    public AuthenticationDetailsImpl(UserId userId, SaltedPasswordDigest password) {
        this.userId = checkNotNull(userId);
        this.password = checkNotNull(password);
    }

    @Override
    public SaltedPasswordDigest getPassword() {
        return password;
    }

    @Override
    public UserId getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationDetailsImpl that = (AuthenticationDetailsImpl) o;
        return Objects.equal(userId, that.userId) &&
                Objects.equal(password, that.password);
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
