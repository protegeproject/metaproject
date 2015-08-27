package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Salt;
import edu.stanford.protege.metaproject.api.SaltGenerator;

import java.security.SecureRandom;
import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A generator of cryptographic salt
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class SaltGeneratorImpl implements SaltGenerator {
    private static SaltGeneratorImpl instance = null;
    private static final int DEFAULT_BYTE_LENGTH = 24;
    private final Random random = new SecureRandom();
    private final int nrBytes;

    /**
     * Constructor
     *
     * @param nrBytes   Salt byte length
     */
    private SaltGeneratorImpl(int nrBytes) {
        this.nrBytes = checkNotNull(nrBytes);
    }

    /**
     * Get the singleton instance of this salt generator that produces salts with the given byte length
     *
     * @param nrBytes   Number of bytes for the salt
     * @return Instance of the salt generator
     */
    public static SaltGeneratorImpl getInstance(int nrBytes) {
        if(instance == null) {
            instance = new SaltGeneratorImpl(nrBytes);
        }
        return instance;
    }

    /**
     * Get the singleton instance of this salt generator that produces salts with the default byte length (24 bytes)
     *
     * @return Instance of the salt generator
     */
    public static SaltGeneratorImpl getInstance() {
        if(instance == null) {
            instance = new SaltGeneratorImpl(DEFAULT_BYTE_LENGTH);
        }
        return instance;
    }

    /**
     * Generate a salt
     *
     * @return Salt
     */
    @Override
    public Salt generate() {
        byte[] bytes = new byte[nrBytes];
        random.nextBytes(bytes);
        return new SaltImpl(bytes);
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
        return Objects.equal(nrBytes, that.nrBytes) &&
                Objects.equal(random, that.random);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(random, nrBytes);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("random", random)
                .add("nrBytes", nrBytes)
                .toString();
    }
}
