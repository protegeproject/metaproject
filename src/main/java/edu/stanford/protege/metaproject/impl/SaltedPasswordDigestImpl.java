package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Salt;
import edu.stanford.protege.metaproject.api.SaltedPasswordDigest;

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
public final class SaltedPasswordDigestImpl implements SaltedPasswordDigest, Serializable {
    private static final long serialVersionUID = 2576657046695803549L;
    @Nonnull private final String password;
    @Nonnull private final Salt salt;

    /**
     * Constructor for salted passwords
     *
     * @param password Password
     * @param salt     Salt
     */
    public SaltedPasswordDigestImpl(@Nonnull String password, @Nonnull Salt salt) {
        this.password = checkNotNull(password);
        this.salt = checkNotNull(salt);
    }

    @Override
    @Nonnull
    public String getPassword() {
        return password;
    }

    @Override
    @Nonnull
    public Salt getSalt() {
        return salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaltedPasswordDigest)) {
            return false;
        }
        SaltedPasswordDigest that = (SaltedPasswordDigest) o;
        return Objects.equal(password, that.getPassword()) &&
                Objects.equal(salt, that.getSalt());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(password, salt);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("password", password)
                .add("salt", salt)
                .toString();
    }
}
