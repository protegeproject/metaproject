package edu.stanford.protege.metaproject.api;

import java.util.Optional;

/**
 * A generator of (unique) entity IRIs that may keep track of the status
 * of entity IRI generation where this is necessary (e.g., numeric sequential
 * entity names).
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface EntityIriGenerator {

    EntityIriPrefix getEntityIriPrefix();

    /**
     * Get the next class IRI
     *
     * @return Entity IRI
     */
    EntityIri getNextClassIri();

    /**
     * Get the next object property IRI
     *
     * @return Entity IRI
     */
    EntityIri getNextObjectPropertyIri();

    /**
     * Get the next data property IRI
     *
     * @return Entity IRI
     */
    EntityIri getNextDataPropertyIri();

    /**
     * Get the next annotation property IRI
     *
     * @return Entity IRI
     */
    EntityIri getNextAnnotationPropertyIri();

    /**
     * Get the next individual IRI
     *
     * @return Entity IRI
     */
    EntityIri getNextIndividualIri();

    /**
     * Get the current status of entity IRIs, i.e., the last generated IRIs
     * for each entity type, where tracking of IRIs is necessary (e.g., for
     * sequential identifiers, but not necessarily for UUIDs).
     *
     * @return Entity IRI status
     */
    Optional<EntityIriStatus> getEntityIriStatus();

}