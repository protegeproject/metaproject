package edu.stanford.protege.metaproject.api;

import java.util.Optional;

/**
 * A strategy for automatically generating unique term identifiers (i.e., classes,
 * object properties, data properties, annotation properties, and individuals)
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OntologyTermIdGenerator extends IdGenerator {

    /**
     * Get the next class identifier
     *
     * @return Class identifier
     */
    OntologyTermId getNextClassId();

    /**
     * Get the next object property identifier
     *
     * @return Object property identifier
     */
    OntologyTermId getNextObjectPropertyId();

    /**
     * Get the next data property identifier
     *
     * @return Data property identifier
     */
    OntologyTermId getNextDataPropertyId();

    /**
     * Get the next annotation property identifier
     *
     * @return Annotation property identifier
     */
    OntologyTermId getNextAnnotationPropertyId();

    /**
     * Get the next individual identifier
     *
     * @return Individual identifier
     */
    OntologyTermId getNextIndividualId();

    /**
     * Get the current status of ontology term identifiers, i.e., the last generated identifiers
     *
     * @return Ontology term identifier status instance
     */
    Optional<OntologyTermIdStatus> getCurrentOntologyTermIdStatus();

}