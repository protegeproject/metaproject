package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface HasId<T> {

    /**
     * Get identifier
     *
     * @return Identifier
     */
    T getId();

}
