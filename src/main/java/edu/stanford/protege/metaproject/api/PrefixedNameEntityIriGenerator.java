package edu.stanford.protege.metaproject.api;

/**
 * A prefixed entity name generator
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface PrefixedNameEntityIriGenerator extends EntityIriGenerator {

    /**
     * Get the prefix being used for class names
     *
     * @return Prefix used for class names
     */
    EntityNamePrefix getClassNamePrefix();

    /**
     * Get the prefix being used for object property names
     *
     * @return Prefix used for object properties names
     */
    EntityNamePrefix getObjectPropertyNamePrefix();

    /**
     * Get the prefix being used for data property names
     *
     * @return Prefix used for data property names
     */
    EntityNamePrefix getDataPropertyNamePrefix();

    /**
     * Get the prefix being used for annotation property names
     *
     * @return Prefix used for annotation property names
     */
    EntityNamePrefix getAnnotationPropertyNamePrefix();

    /**
     * Get the prefix being used for individual names
     *
     * @return Prefix used for individual names
     */
    EntityNamePrefix getIndividualNamePrefix();

}