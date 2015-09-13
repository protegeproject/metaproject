package edu.stanford.protege.metaproject.api;

import java.util.Optional;

/**
 * A representation of the entities' IRIs status, i.e., the IRI prefix, entity name prefixes,
 * and the last generated entity name suffixes.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface EntityIriStatus {

    /**
     * Get the entity IRI prefix, i.e., the IRI fragment preceding
     * the entity name
     *
     * @return Entity IRI prefix
     */
    EntityIriPrefix getEntityIriPrefix();

    /**
     * Get the class name prefix
     *
     * @return Entity name prefix
     */
    Optional<EntityNamePrefix> getClassNamePrefix();

    /**
     * Get the object property name prefix
     *
     * @return Entity name prefix
     */
    Optional<EntityNamePrefix> getObjectPropertyNamePrefix();

    /**
     * Get the data property name prefix
     *
     * @return Entity name prefix
     */
    Optional<EntityNamePrefix> getDataPropertyNamePrefix();

    /**
     * Get the annotation property name prefix
     *
     * @return Entity name prefix
     */
    Optional<EntityNamePrefix> getAnnotationPropertyNamePrefix();

    /**
     * Get the individual name prefix
     *
     * @return Entity name prefix
     */
    Optional<EntityNamePrefix> getIndividualNamePrefix();

    /**
     * Get the last generated suffix of classes
     *
     * @return Entity name suffix
     */
    Optional<EntityNameSuffix> getClassNameSuffix();

    /**
     * Get the last generated suffix of object properties
     *
     * @return Entity name suffix
     */
    Optional<EntityNameSuffix> getObjectPropertyNameSuffix();

    /**
     * Get the last generated suffix of data properties
     *
     * @return Entity name suffix
     */
    Optional<EntityNameSuffix> getDataPropertyNameSuffix();

    /**
     * Get the last generated suffix of annotation properties
     *
     * @return Entity name suffix
     */
    Optional<EntityNameSuffix> getAnnotationPropertyNameSuffix();

    /**
     * Get the last generated suffix of individuals
     *
     * @return Entity name suffix
     */
    Optional<EntityNameSuffix> getIndividualNameSuffix();

}
