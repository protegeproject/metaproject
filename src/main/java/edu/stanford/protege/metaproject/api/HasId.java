package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface HasId<T> {

    /**
     * Get the identifier of the object
     *
     * @return Identifier
     */
    T getId();

}
