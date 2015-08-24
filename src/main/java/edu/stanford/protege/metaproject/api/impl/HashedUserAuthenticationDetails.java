package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.*;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class HashedUserAuthenticationDetails implements UserAuthenticationDetails, Serializable, Comparable<UserAuthenticationDetails> {
    private static final long serialVersionUID = -461646307722268866L;
    private final UserId userId;
    private final SaltedPassword password;
    private final Salt salt;

    /**
     * Constructor
     *
     * @param userId    User identifier
     * @param password  Salted (hashed) password
     * @param salt  Salt (possibly null)
     */
    public HashedUserAuthenticationDetails(UserId userId, SaltedPassword password, Optional<Salt> salt) {
        this.userId = checkNotNull(userId);
        this.password = checkNotNull(password);
        if(salt.isPresent()) {
            this.salt = checkNotNull(salt.get());
        }
        else {
            this.salt = null;
        }
    }

    @Override
    public SaltedPassword getPassword() {
        return password;
    }

    @Override
    public UserId getUserId() {
        return userId;
    }

    @Override
    public Optional<Salt> getSalt() {
        return Optional.of(salt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashedUserAuthenticationDetails that = (HashedUserAuthenticationDetails) o;
        return Objects.equal(userId, that.userId) &&
                Objects.equal(password, that.password) &&
                Objects.equal(salt, that.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, password, salt);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("password", password)
                .add("salt", salt)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull UserAuthenticationDetails that) {
        return ComparisonChain.start()
                .compare(this.userId.get(), that.getUserId().get())
                .result();
    }
}
