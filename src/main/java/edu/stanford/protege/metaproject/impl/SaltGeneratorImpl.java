package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.ConfigurationManager;
import edu.stanford.protege.metaproject.api.Salt;
import edu.stanford.protege.metaproject.api.SaltGenerator;
import org.apache.commons.codec.binary.Hex;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.security.SecureRandom;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class SaltGeneratorImpl implements SaltGenerator {
    private final static int DEFAULT_BYTE_LENGTH = 24;

    /**
     * No-arguments constructor
     */
    public SaltGeneratorImpl() { }

    @Override
    @Nonnull
    public Salt generate() {
        return generate(DEFAULT_BYTE_LENGTH);
    }

    @Override
    @Nonnull
    public Salt generate(int nrBytes) {
        byte[] bytes = new byte[nrBytes];
        new SecureRandom().nextBytes(bytes);
        return ConfigurationManager.getFactory().getSalt(Hex.encodeHexString(bytes));
    }

    @Override
    public int getByteLength() {
        return DEFAULT_BYTE_LENGTH;
    }
}
