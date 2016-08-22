package edu.stanford.protege.metaproject.api;

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface HasProperties {

    /**
     * Get the map of properties
     *
     * @return Map of string properties
     */
    @Nonnull
    ImmutableMap<String,String> getProperties();

    /**
     * Get the value for the given property key
     *
     * @param key   Property key
     * @return Property value
     */
    @Nullable
    String getProperty(@Nonnull String key);

}
