package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.ProjectOptions;

import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectOptionsImpl implements ProjectOptions {
    private final Map<String,Set<String>> requiredAnnotations, optionalAnnotationsMap;
    private final Set<String> complexAnnotations, immutableAnnotations, requiredEntities;
    private Map<String,String> customProperties;

    /**
     * Package-private constructor; user {@link ProjectOptionsBuilder}
     *
     * Entities are expected to be represented as strings that correspond to their IRIs.
     *
     * @param requiredAnnotations    Map of entities to the sets of annotation properties that must
     *                                  accompany each entity's addition to the ontology signature
     * @param optionalAnnotationsMap    Map of entities to the sets of annotation properties that can
     *                                  (optionally) accompany each entity's addition to the ontology
     *                                  signature
     * @param complexAnnotations  Set of complex annotations
     * @param immutableAnnotations  Set of annotation properties whose value cannot be modified
     * @param requiredEntities  Set of entities that must exist in the ontology's signature in order
     *                          to perform some operation within the project with these options.
     * @param customProperties  Map of custom properties for the project
     */
    ProjectOptionsImpl(Map<String,Set<String>> requiredAnnotations, Map<String,Set<String>> optionalAnnotationsMap, Set<String> complexAnnotations,
                       Set<String> immutableAnnotations, Set<String> requiredEntities, Map<String,String> customProperties) {
        this.requiredAnnotations = checkNotNull(requiredAnnotations);
        this.optionalAnnotationsMap = checkNotNull(optionalAnnotationsMap);
        this.complexAnnotations = checkNotNull(complexAnnotations);
        this.immutableAnnotations = checkNotNull(immutableAnnotations);
        this.requiredEntities = checkNotNull(requiredEntities);
        this.customProperties = checkNotNull(customProperties);
    }

    @Override
    public Set<String> getComplexAnnotationProperties() {
        return complexAnnotations;
    }

    @Override
    public Set<String> getImmutableAnnotationProperties() {
        return immutableAnnotations;
    }

    @Override
    public Set<String> getRequiredEntities() {
        return requiredEntities;
    }

    @Override
    public Map<String, Set<String>> getRequiredAnnotationsForAnnotation() {
        return requiredAnnotations;
    }

    @Override
    public Map<String, Set<String>> getOptionalAnnotationAnnotations() {
        return optionalAnnotationsMap;
    }

    @Override
    public Map<String,String> getProperties() {
        return customProperties;
    }

    @Override
    public void addProperty(String key, String value) {
        customProperties.put(checkNotNull(key), checkNotNull(value));
    }

    @Override
    public void removeProperty(String key) {
        customProperties.remove(checkNotNull(key));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectOptionsImpl that = (ProjectOptionsImpl) o;
        return Objects.equal(requiredAnnotations, that.requiredAnnotations) &&
                Objects.equal(optionalAnnotationsMap, that.optionalAnnotationsMap) &&
                Objects.equal(complexAnnotations, that.complexAnnotations) &&
                Objects.equal(immutableAnnotations, that.immutableAnnotations) &&
                Objects.equal(requiredEntities, that.requiredEntities) &&
                Objects.equal(customProperties, that.customProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(requiredAnnotations, optionalAnnotationsMap, complexAnnotations,
                immutableAnnotations, requiredEntities, customProperties);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("requiredAnnotations", requiredAnnotations)
                .add("optionalAnnotationsMap", optionalAnnotationsMap)
                .add("complexAnnotations", complexAnnotations)
                .add("immutableAnnotations", immutableAnnotations)
                .add("requiredEntities", requiredEntities)
                .add("customProperties", customProperties)
                .toString();
    }
}
