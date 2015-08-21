package edu.stanford.protege.metaproject.api;

import java.util.Optional;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OntologyTermIdStatus {

    // suffixes
    Optional<IdSuffix> getClassIdSuffix();

    Optional<IdSuffix> getObjectPropertyIdSuffix();

    Optional<IdSuffix> getDataPropertyIdSuffix();

    Optional<IdSuffix> getAnnotationPropertyIdSuffix();

    Optional<IdSuffix> getIndividualIdSuffix();

    // prefixes
    Optional<IdPrefix> getClassIdPrefix();

    Optional<IdPrefix> getObjectPropertyIdPrefix();

    Optional<IdPrefix> getDataPropertyIdPrefix();

    Optional<IdPrefix> getAnnotationPropertyIdPrefix();

    Optional<IdPrefix> getIndividualIdPrefix();

}
