package edu.stanford.protege.metaproject.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * A representation of some project wide options. These options may concern entities in the ontology, in which case they
 * are expected to be represented as strings that correspond to their IRIs.
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface ProjectOptions {

    /**
     * Get the options map
     *
     * @return Map of strings to sets of strings
     */
    @Nonnull
    ImmutableMap<String,Set<String>> getOptions();

    /**
     * Get the values for the given property key. If there are no values, an empty set is returned
     *
     * @param key   Property key
     * @return Set of strings
     */
    @Nonnull
    ImmutableSet<String> getValues(@Nonnull String key);

    /**
     * Get the single string value for the given property key. This method should be used when the key is mapped to a set with
     * one single element that will be returned. In case the (set) value for the given key contains multiple elements, the first
     * to be fetched is returned.
     *
     * @param key   Property key
     * @return String
     */
    @Nullable
    String getValue(@Nonnull String key);

}
