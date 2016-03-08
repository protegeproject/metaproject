package edu.stanford.protege.metaproject.api;

/**
 * A representation of a generator of cryptographic salt
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface SaltGenerator {

    /**
     * Generate salt using default byte length
     *
     * @return Salt
     */
    Salt generate();

    /**
     * Generate salt using the specified byte length
     *
     * @param nrBytes   Byte length
     * @return Salt
     */
    Salt generate(int nrBytes);

    /**
     * Get the default byte length used for generating a salt
     *
     * @return Number of bytes
     */
    int getByteLength();

}
