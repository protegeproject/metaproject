package edu.stanford.protege.metaproject.api;

import org.semanticweb.owlapi.model.OWLAxiom;

import java.util.Set;

/**
 * A representation of a change set consisting of added and removed axioms
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ChangeSet {

    /**
     * Get the set of added axioms
     *
     * @return Set of added axioms
     */
    Set<OWLAxiom> getAdditions();


    /**
     * Get the set of removed axioms
     *
     * @return Set of removed axioms
     */
    Set<OWLAxiom> getRemovals();

}
