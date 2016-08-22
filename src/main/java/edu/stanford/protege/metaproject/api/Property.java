package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface Property<T> {

    /**
     * Get the property value
     *
     * @return Property value
     */
    @Nonnull
    T get();

}
