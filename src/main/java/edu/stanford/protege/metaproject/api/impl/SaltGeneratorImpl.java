package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Salt;
import edu.stanford.protege.metaproject.api.SaltGenerator;

import java.security.SecureRandom;
import java.util.Random;

/**
 * A generator of cryptographic salt
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class SaltGeneratorImpl implements SaltGenerator {
    private static SaltGeneratorImpl instance = null;
    private final Random random = new SecureRandom();
    private final int nrBytes = 16;

    /**
     * Constructor
     */
    private SaltGeneratorImpl() { }

    /**
     * Get the singleton instance of the salt generator
     *
     * @return Salt generator instance
     */
    public static SaltGeneratorImpl getInstance() {
        if(instance == null) {
            instance = new SaltGeneratorImpl();
        }
        return instance;
    }

    /**
     * Generate a salt
     *
     * @return Salt
     */
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
