package edu.stanford.protege.metaproject.api;

/**
 * A representation of a generator of cryptographic salt
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface SaltGenerator {

    /**
     * Generate a salt
     *
     * @return Salt
     */
    Salt generate();

    /**
     * Get the byte length used for generating a salt
     *
     * @return Number of bytes
     */
    int getByteLength();

}
