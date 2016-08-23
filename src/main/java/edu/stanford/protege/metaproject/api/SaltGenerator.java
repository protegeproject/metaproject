package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * A representation of a generator of cryptographic salt
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface SaltGenerator {

    /**
     * Generate salt using default byte length
     *
     * @return Salt
     */
    @Nonnull
    Salt generate();

    /**
     * Generate salt using the specified byte length
     *
     * @param nrBytes   Byte length
     * @return Salt
     */
    @Nonnull
    Salt generate(int nrBytes);

    /**
     * Get the default byte length used for generating a salt
     *
     * @return Number of bytes
     */
    int getByteLength();

}
