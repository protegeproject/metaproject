package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Salt;
import edu.stanford.protege.metaproject.api.SaltGenerator;
import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A generator of cryptographic salt
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class SaltGeneratorImpl implements SaltGenerator {
    private static final int DEFAULT_BYTE_LENGTH = 24;
    private final int nrBytes;

    /**
     * No-arguments constructor that uses default byte length of 24
     */
    public SaltGeneratorImpl() {
        this(DEFAULT_BYTE_LENGTH);
    }

    /**
     * Constructor
     *
     * @param nrBytes   Salt byte length
     */
    public SaltGeneratorImpl(int nrBytes) {
        this.nrBytes = checkNotNull(nrBytes);
    }

    /**
     * Generate a salt
     *
     * @return Salt
     */
    @Override
    public Salt generate() {
        byte[] bytes = new byte[nrBytes];
        new SecureRandom().nextBytes(bytes);
        return new SaltImpl(Hex.encodeHexString(bytes));
    }

    /**
     * Get the byte length used for generating a salt
     *
     * @return Number of bytes
     */
    @Override
    public int getByteLength() {
        return nrBytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaltGeneratorImpl that = (SaltGeneratorImpl) o;
        return Objects.equal(nrBytes, that.nrBytes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nrBytes);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("nrBytes", nrBytes)
                .toString();
    }
}
