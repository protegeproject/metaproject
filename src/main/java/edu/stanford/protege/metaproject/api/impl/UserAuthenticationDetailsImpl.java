package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Id;
import edu.stanford.protege.metaproject.api.Password;
import edu.stanford.protege.metaproject.api.Salt;
import edu.stanford.protege.metaproject.api.UserAuthenticationDetails;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class UserAuthenticationDetailsImpl implements UserAuthenticationDetails, Serializable {
    private static final long serialVersionUID = -7037105865507446796L;
    private final Id userId;
    private final Password password;
    private final Salt salt;

    /**
     * Constructor
     *
     * @param userId    User identifier
     * @param password  Password
     * @param salt  Salt
     */
    public UserAuthenticationDetailsImpl(Id userId, Password password, Salt salt) {
        this.userId = checkNotNull(userId);
        this.password = checkNotNull(password);
        this.salt = checkNotNull(salt);
    }

    @Override
    public Password getPassword() {
        return password;
    }

    @Override
    public Salt getSalt() {
        return salt;
    }

    @Override
    public Id getId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthenticationDetailsImpl that = (UserAuthenticationDetailsImpl) o;
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
}
