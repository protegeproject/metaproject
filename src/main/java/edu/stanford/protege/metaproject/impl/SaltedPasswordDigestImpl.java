package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Salt;
import edu.stanford.protege.metaproject.api.SaltedPasswordDigest;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class SaltedPasswordDigestImpl implements SaltedPasswordDigest, Serializable {
    private static final long serialVersionUID = 2576657046695803549L;
    private final String password;
    private final Salt salt;

    /**
     * Constructor for salted passwords
     *
     * @param password Password
     * @param salt     Salt
     */
    public SaltedPasswordDigestImpl(String password, Salt salt) {
        this.password = checkNotNull(password);
        this.salt = checkNotNull(salt);
    }

    /**
     * Get the password hash
     *
     * @return Password hash
     */
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Salt getSalt() {
        return salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaltedPasswordDigestImpl that = (SaltedPasswordDigestImpl) o;
        return Objects.equal(password, that.password) &&
                Objects.equal(salt, that.salt);
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
