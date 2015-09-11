package edu.stanford.protege.metaproject.api;

/**
 * A representation of salt data
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Salt {

    /**
     * Get salt as a byte array
     *
     * @return Byte array
     */
    byte[] getBytes();

    /**
     * Get string representation of salt
     *
     * @return Salt string
     */
    String getString();

}