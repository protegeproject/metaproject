package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.PlainPassword;

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
public final class PlainPasswordImpl implements PlainPassword, Serializable {
    private static final long serialVersionUID = 3575663606583395411L;
    @Nonnull private final String plainPassword;

    /**
     * Constructor
     *
     * @param plainPassword    Password string
     */
    public PlainPasswordImpl(@Nonnull String plainPassword) {
        this.plainPassword = checkNotNull(plainPassword);
    }
    
    @Override
    @Nonnull
    public String getPassword() {
        return plainPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlainPassword)) {
            return false;
        }
        PlainPassword that = (PlainPassword) o;
        return Objects.equal(plainPassword, that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(plainPassword);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(plainPassword)
                .toString();
    }
}
