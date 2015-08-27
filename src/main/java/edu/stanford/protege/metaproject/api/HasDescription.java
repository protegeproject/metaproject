package edu.stanford.protege.metaproject.api;

/**
 * A representation of things that have a description
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
interface HasDescription {

    /**
     * Get the description of the object
     *
     * @return Description
     */
    Description getDescription();

}
