package edu.stanford.protege.metaproject.api;

import java.util.Map;
import java.util.Set;

/**
 * A representation of some project wide options centered around entities. Entities are expected
 * to be represented as strings that correspond to their IRIs.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ProjectOptions extends HasProperties {

    /**
     * Get the set of annotation properties that, when introduced, need to be annotated with
     * specific properties that can be obtained via getRequiredAnnotationsForAnnotation().
     *
     * @return Set of annotation properties
     */
    Set<String> getComplexAnnotationProperties();

    /**
     * Get the set of annotation properties whose fillers, once inserted,
     * cannot be modified.
     *
     * @return Set of annotation properties with immutable fillers
     */
    Set<String> getImmutableAnnotationProperties();

    /**
     * Get the map of entities to their entity presence requirements
     *
     * @return Map of entities to entities that are required for some operation
     */
    Map<String,Set<String>> getRequiredEntities();

    /**
     * Get the map of annotation properties to the set of annotation properties that
     * must be used to annotate the annotation property map key
     *
     * @return Map of annotation properties to required annotation properties
     */
    Map<String,Set<String>> getRequiredAnnotationsForAnnotation();

    /**
     * Get the map of annotation properties to the set of annotation properties that
     * can optionally be used to annotate the annotation property map key
     *
     * @return Map of annotation properties to optional annotation properties
     */
    Map<String,Set<String>> getOptionalAnnotationAnnotations();

}
