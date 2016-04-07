package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.ProjectOptions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builder for project options. These options primarily concern entities in the ontology,
 * so all strings (except for possibly the custom properties) correspond to entity IRIs.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectOptionsBuilder {
    private Map<String, Set<String>> requiredAnnotationsMap = new HashMap<>();
    private Map<String, Set<String>> requiredEntities = new HashMap<>();
    private Map<String, Set<String>> optionalAnnotationsMap = new HashMap<>();
    private Set<String> complexAnnotations = new HashSet<>();
    private Set<String> immutableAnnotations = new HashSet<>();
    private Map<String, String> customProperties = new HashMap<>();

    /**
     * Set the map of entities to the sets of annotation properties that must accompany each entity's addition
     * to the ontology signature. Entities are expected to be represented here as strings that correspond to
     * their IRIs.
     *
     * @param requiredAnnotationsMap    Map of entities to sets of required annotations
     * @return ProjectOptionsBuilder
     */
    public ProjectOptionsBuilder setRequiredAnnotationsMap(Map<String, Set<String>> requiredAnnotationsMap) {
        this.requiredAnnotationsMap = checkNotNull(requiredAnnotationsMap);
        return this;
    }

    /**
     * Set the map of entities to the sets of annotation properties that can (optionally) accompany each
     * entity's addition to the ontology signature. Entities are expected to be represented here as strings that
     * correspond to their IRIs.
     *
     * @param optionalAnnotationsMap    Map of entities to sets of optional annotation properties.
     *                                  Entities are represented as strings corresponding to their IRIs.
     * @return ProjectOptionsBuilder
     */
    public ProjectOptionsBuilder setOptionalAnnotationsMap(Map<String, Set<String>> optionalAnnotationsMap) {
        this.optionalAnnotationsMap = checkNotNull(optionalAnnotationsMap);
        return this;
    }

    /**
     * Set the annotation set that must be further annotated with some other properties
     *
     * @param complexAnnotations  Set of annotation properties represented as strings
     *                              corresponding to their IRIs.
     * @return ProjectOptionsBuilder
     */
    public ProjectOptionsBuilder setComplexAnnotations(Set<String> complexAnnotations) {
        this.complexAnnotations = checkNotNull(complexAnnotations);
        return this;
    }

    /**
     * Set the annotation set whose values, once set, cannot be modified
     *
     * @param immutableAnnotations  Set of annotation properties
     * @return ProjectOptionsBuilder
     */
    public ProjectOptionsBuilder setImmutableAnnotations(Set<String> immutableAnnotations) {
        this.immutableAnnotations = checkNotNull(immutableAnnotations);
        return this;
    }

    /**
     * Get the map of entities to entities that are required for some operation
     *
     * @param requiredEntities  Map of entities to entities that are required for some operation
     * @return ProjectOptionsBuilder
     */
    public ProjectOptionsBuilder setRequiredEntities(Map<String,Set<String>> requiredEntities) {
        this.requiredEntities = checkNotNull(requiredEntities);
        return this;
    }

    /**
     * Set the custom property map
     *
     * @param customProperties  Map of custom properties
     * @return ProjectOptionsBuilder
     */
    public ProjectOptionsBuilder setCustomProperties(Map<String, String> customProperties) {
        this.customProperties = checkNotNull(customProperties);
        return this;
    }

    /**
     * Create an instance of project options
     *
     * @return ProjectOptions
     */
    public ProjectOptions createProjectOptions() {
        return new ProjectOptionsImpl(requiredAnnotationsMap, optionalAnnotationsMap, complexAnnotations, immutableAnnotations, requiredEntities, customProperties);
    }
}