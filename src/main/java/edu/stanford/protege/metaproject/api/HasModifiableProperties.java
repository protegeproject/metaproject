package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface HasModifiableProperties extends HasProperties {

    /**
     * Add a property
     *
     * @param key   Key
     * @param value Value for key
     */
    void addProperty(String key, String value);

    /**
     * Remove a property from the map
     *
     * @param key   Key
     */
    void removeProperty(String key);

}
