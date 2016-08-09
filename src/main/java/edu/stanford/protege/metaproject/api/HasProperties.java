package edu.stanford.protege.metaproject.api;

import com.google.common.collect.ImmutableMap;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface HasProperties {

    /**
     * Get the map of properties
     *
     * @return Map of string properties
     */
    ImmutableMap<String,String> getProperties();

    /**
     * Get the value for the given property key
     *
     * @param key   Property key
     * @return Property value
     */
    String getProperty(String key);

}
