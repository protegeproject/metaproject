package edu.stanford.protege.metaproject.api;

/**
 * A prefixed term identifier generator
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public abstract class PrefixedIdGenerator {
    protected TermIdPrefix classIdPrefix, objectPropertyIdPrefix, dataPropertyIdPrefix, individualIdPrefix, annotationPropertyIdPrefix;

    /**
     * Get the prefix being used for class identifiers
     *
     * @return Prefix used for class identifiers
     */
    public TermIdPrefix getClassIdPrefix() {
        return classIdPrefix;
    }

    /**
     * Get the prefix being used for object property identifiers
     *
     * @return Prefix used for object properties identifiers
     */
    public TermIdPrefix getObjectPropertyIdPrefix() {
        return objectPropertyIdPrefix;
    }

    /**
     * Get the prefix being used for data property identifiers
     *
     * @return Prefix used for data property identifiers
     */
    public TermIdPrefix getDataPropertyIdPrefix() {
        return dataPropertyIdPrefix;
    }

    /**
     * Get the prefix being used for annotation property identifiers
     *
     * @return Prefix used for annotation property identifiers
     */
    public TermIdPrefix getAnnotationPropertyIdPrefix() {
        return annotationPropertyIdPrefix;
    }

    /**
     * Get the prefix being used for individual identifiers
     *
     * @return Prefix used for individual identifiers
     */
    public TermIdPrefix getIndividualIdPrefix() {
        return individualIdPrefix;
    }
}