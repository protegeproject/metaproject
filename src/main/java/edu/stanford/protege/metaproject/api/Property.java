package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Property<T> {

    /**
     * Get the property value
     *
     * @return Property value
     */
    T get();

}
