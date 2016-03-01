package edu.stanford.protege.metaproject.api;

/**
 * A representation of an operation restriction (e.g., ability to add a specific axiom type)
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OperationRestriction<T> {

    /**
     * Get the operation restriction
     *
     * @return Restriction
     */
    T getRestriction();

    /**
     * Get the restriction modality that specifies whether, for example,
     * an OWL axiom type should only be added or removed (or both)
     *
     * @return Operation restriction modality
     */
    ChangeModality getModality();

    /**
     * Verify whether this restriction represents an axiom (type) restriction
     *
     * @return true if restriction constrains axiom type(s), false otherwise
     */
    boolean isAxiomRestriction();

}