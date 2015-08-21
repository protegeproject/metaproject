package edu.stanford.protege.metaproject.api;

import org.semanticweb.owlapi.model.IRI;

/**
 * A representation of an operation prerequisite (e.g., presence or absence of some OWL entity)
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OperationPrerequisite {

    /**
     * Get the prerequisite IRI
     *
     * @return IRI
     */
    IRI getPrerequisite();

    /**
     * Get the prerequisite modifier that determines whether, for example,
     * an OWL entity (prerequisite) should be present or absent from the ontology
     *
     * @return Operation prerequisite modifier
     */
    Modifier getModifier();

    /**
     * An operation prerequisite modifier determines the status of the OWL entity defined
     * as a prerequisite; this entity could be assumed to be present or absent
     */
    enum Modifier {
        PRESENT, ABSENT
    }

}