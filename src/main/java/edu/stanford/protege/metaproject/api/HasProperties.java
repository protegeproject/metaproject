package edu.stanford.protege.metaproject.api;

import java.util.Map;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface HasProperties {

    /**
     * Get the map of client configuration properties
     *
     * @return Map of string properties
     */
    Map<String,String> getProperties();

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

    /**
     * Get the value for the given property key
     *
     * @param key   Property key
     * @return Property value
     */
    String getProperty(String key);

}
