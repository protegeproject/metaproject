package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
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
    private static final long serialVersionUID = -8434267075481461361L;
    private final byte[] bytes;

    /**
     * Constructor
     * @param bytes Byte array
     */
    public SaltImpl(byte[] bytes) {
        this.bytes = checkNotNull(bytes);
    }

    /**
     * Get salt data
     *
     * @return Byte array
     */
    @Override
    public byte[] getBytes() {
        return Arrays.copyOf(bytes, bytes.length);
    }

    @Override
    public String getString() {
        return new String(bytes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaltImpl salt = (SaltImpl) o;
        return Arrays.equals(bytes, salt.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bytes", bytes)
                .toString();
    }
}
