package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Salt;
import edu.stanford.protege.metaproject.api.SaltedPassword;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class SaltedPasswordImpl implements SaltedPassword, Serializable {
    private static final long serialVersionUID = 1956370831077820579L;
    private final String password;
    private final Salt salt;

    /**
     * Constructor
     *
     * @param password    Password string
     * @param salt  Salt
     */
    public SaltedPasswordImpl(String password, Salt salt) {
        this.password = checkNotNull(password);
        this.salt = checkNotNull(salt);
    }

    /**
     * Get the password hash
     *
     * @return Password hash
     */
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
        SaltedPasswordImpl that = (SaltedPasswordImpl) o;
        return Objects.equal(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(password);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("password", password)
                .toString();
    }
}
