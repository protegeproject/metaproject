package edu.stanford.protege.metaproject;

/**
 * A strategy for automatically generating unique term (i.e., classes, properties, ...) identifiers
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface TermIdentifierGenerator {

    /**
     * Get the next class identifier
     *
     * @return Class identifier
     */
    TermId getNextClassId();

    /**
     * Get the next object property identifier
     *
     * @return Object property identifier
     */
    TermId getNextObjectPropertyId();

    /**
     * Get the next data property identifier
     *
     * @return Data property identifier
     */
    TermId getNextDataPropertyId();

    /**
     * Get the next individual identifier
     *
     * @return Individual identifier
     */
    TermId getNextIndividualId();

}