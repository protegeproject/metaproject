package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of salt data
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class Salt implements Serializable {
    private static final long serialVersionUID = 8965939935983488938L;
    private final byte[] bytes;

    /**
     * Constructor
     * @param bytes Byte array
     */
    public Salt(byte[] bytes) {
        this.bytes = checkNotNull(bytes);
    }

    /**
     * Get salt data
     *
     * @return Byte array
     */
    public byte[] getBytes() {
        return Arrays.copyOf(bytes, bytes.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salt salt = (Salt) o;
        return Objects.equal(bytes, salt.bytes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bytes);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bytes", bytes)
                .toString();
    }
}
