package edu.stanford.protege.metaproject.api;

/**
 * A prefixed term identifier generator
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OntologyTermPrefixedIdGenerator extends OntologyTermIdGenerator {

    /**
     * Get the prefix being used for class identifiers
     *
     * @return Prefix used for class identifiers
     */
    OntologyTermIdPrefix getClassIdPrefix();

    /**
     * Get the prefix being used for object property identifiers
     *
     * @return Prefix used for object properties identifiers
     */
    OntologyTermIdPrefix getObjectPropertyIdPrefix();

    /**
     * Get the prefix being used for data property identifiers
     *
     * @return Prefix used for data property identifiers
     */
    OntologyTermIdPrefix getDataPropertyIdPrefix();

    /**
     * Get the prefix being used for annotation property identifiers
     *
     * @return Prefix used for annotation property identifiers
     */
    OntologyTermIdPrefix getAnnotationPropertyIdPrefix();

    /**
     * Get the prefix being used for individual identifiers
     *
     * @return Prefix used for individual identifiers
     */
    OntologyTermIdPrefix getIndividualIdPrefix();

}