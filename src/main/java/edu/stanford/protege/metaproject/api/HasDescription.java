package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * A representation of things that have a description
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
interface HasDescription {

    /**
     * Get the description of the object
     *
     * @return Description
     */
    @Nonnull
    Description getDescription();

}
