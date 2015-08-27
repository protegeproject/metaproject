package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OntologyTermId extends Id {

    OntologyTermIdPrefix getPrefix();

    OntologyTermIdSuffix getSuffix();

}
