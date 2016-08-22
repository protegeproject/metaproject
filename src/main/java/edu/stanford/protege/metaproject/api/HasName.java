package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * A representation of things that have a name
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
interface HasName {

    /**
     * Get the name of the object
     *
     * @return Name
     */
    @Nonnull
    Name getName();

}
