package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Salt;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.io.Serializable;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class SaltImpl implements Salt, Serializable {
    private static final long serialVersionUID = -4946418544284738194L;
    @Nonnull private final String salt;

    /**
     * Constructor
     *
     * @param salt Salt string
     */
    public SaltImpl(@Nonnull String salt) {
        this.salt = checkNotNull(salt);
    }

    @Override
    @Nonnull
    public byte[] getBytes() {
        byte[] saltBytes = salt.getBytes();
        return Arrays.copyOf(saltBytes, saltBytes.length);
    }

    @Override
    @Nonnull
    public String getString() {
        return salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Salt)) {
            return false;
        }
        Salt that = (Salt) o;
        return Objects.equal(salt, that.getString());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(salt);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(salt)
                .toString();
    }
}
