package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.Salt;
import edu.stanford.protege.metaproject.api.SaltGenerator;
import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class SaltGeneratorImpl implements SaltGenerator {
    private final static int DEFAULT_BYTE_LENGTH = 24;

    /**
     * No-arguments constructor
     */
    public SaltGeneratorImpl() { }

    @Override
    public Salt generate() {
        return generate(DEFAULT_BYTE_LENGTH);
    }

    @Override
    public Salt generate(int nrBytes) {
        byte[] bytes = new byte[nrBytes];
        new SecureRandom().nextBytes(bytes);
        return Manager.getFactory().createSalt(Hex.encodeHexString(bytes));
    }

    @Override
    public int getByteLength() {
        return DEFAULT_BYTE_LENGTH;
    }
}
