package edu.stanford.protege.metaproject.api;

import java.util.Map;
import java.util.Set;

/**
 * A representation of some project wide options. These options may concern entities in the ontology, in which case they
 * are expected to be represented as strings that correspond to their IRIs.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ProjectOptions {

    /**
     * Get the options map
     *
     * @return Map of strings to sets of strings
     */
    Map<String,Set<String>> getOptions();

    /**
     * Get the value, in the form of a set of strings, for the given property key
     *
     * @param key   Property key
     * @return Set of strings
     */
    Set<String> getOption(String key);

    /**
     * Get the single string value for the given property key. This method should be used when the key is mapped to a set with
     * one single element that will be returned. In case the (set) value for the given key contains multiple elements, the first
     * to be fetched is returned.
     *
     * @param key   Property key
     * @return String
     */
    String getSingletonOption(String key);

}
