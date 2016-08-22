package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * A representation of salt data
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface Salt {

    /**
     * Get salt as a byte array
     *
     * @return Byte array
     */
    @Nonnull
    byte[] getBytes();

    /**
     * Get string representation of salt
     *
     * @return Salt string
     */
    @Nonnull
    String getString();

}