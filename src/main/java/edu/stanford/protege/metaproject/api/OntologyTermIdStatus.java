package edu.stanford.protege.metaproject.api;

import java.util.Optional;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OntologyTermIdStatus {

    // suffixes
    Optional<OntologyTermIdSuffix> getClassIdSuffix();

    Optional<OntologyTermIdSuffix> getObjectPropertyIdSuffix();

    Optional<OntologyTermIdSuffix> getDataPropertyIdSuffix();

    Optional<OntologyTermIdSuffix> getAnnotationPropertyIdSuffix();

    Optional<OntologyTermIdSuffix> getIndividualIdSuffix();

    // prefixes
    Optional<OntologyTermIdPrefix> getClassIdPrefix();

    Optional<OntologyTermIdPrefix> getObjectPropertyIdPrefix();

    Optional<OntologyTermIdPrefix> getDataPropertyIdPrefix();

    Optional<OntologyTermIdPrefix> getAnnotationPropertyIdPrefix();

    Optional<OntologyTermIdPrefix> getIndividualIdPrefix();

}
