package edu.stanford.protege.metaproject.api;

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
    Id getNextClassId();

    /**
     * Get the next object property identifier
     *
     * @return Object property identifier
     */
    Id getNextObjectPropertyId();

    /**
     * Get the next data property identifier
     *
     * @return Data property identifier
     */
    Id getNextDataPropertyId();

    /**
     * Get the next annotation property identifier
     *
     * @return Annotation property identifier
     */
    Id getNextAnnotationPropertyId();

    /**
     * Get the next individual identifier
     *
     * @return Individual identifier
     */
    Id getNextIndividualId();

}