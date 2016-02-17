package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Salt;

import java.io.Serializable;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of salt data
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class SaltImpl implements Salt, Serializable {
    private static final long serialVersionUID = -4946418544284738194L;
    private final String salt;

    /**
     * Constructor
     *
     * @param salt Salt string
     */
    public SaltImpl(String salt) {
        this.salt = checkNotNull(salt);
    }

    @Override
    public byte[] getBytes() {
        byte[] saltBytes = salt.getBytes();
        return Arrays.copyOf(saltBytes, saltBytes.length);
    }

    @Override
    public String getString() {
        return salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaltImpl salt1 = (SaltImpl) o;
        return Objects.equal(salt, salt1.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(salt);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("salt", salt)
                .toString();
    }
}
