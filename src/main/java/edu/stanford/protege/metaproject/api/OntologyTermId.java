package edu.stanford.protege.metaproject.api;

/**
 * A representation of an identifier of an ontology term, such as a class or property
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OntologyTermId extends Id {

    /**
     * Get the identifier prefix
     *
     * @return Identifier prefix
     */
    OntologyTermIdPrefix getPrefix();

    /**
     * Get the identifier suffix
     *
     * @return Identifier suffix
     */
    OntologyTermIdSuffix getSuffix();

}
