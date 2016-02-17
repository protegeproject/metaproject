package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.PlainPassword;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class PlainPasswordImpl implements PlainPassword, Serializable {
    private static final long serialVersionUID = 3575663606583395411L;
    private final String plainPassword;

    /**
     * Constructor
     *
     * @param plainPassword    Password string
     */
    public PlainPasswordImpl(String plainPassword) {
        this.plainPassword = checkNotNull(plainPassword);
    }

    /**
     * Get the salted password bytes
     *
     * @return Salted password bytes array
     */
    @Override
    public String getPassword() {
        return plainPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlainPasswordImpl that = (PlainPasswordImpl) o;
        return Objects.equal(plainPassword, that.plainPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(plainPassword);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("plainPassword", "******")
                .toString();
    }
}
