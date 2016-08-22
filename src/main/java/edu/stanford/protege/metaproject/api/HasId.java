package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface HasId<T> {

    /**
     * Get the identifier of the object
     *
     * @return Identifier
     */
    @Nonnull
    T getId();

}
