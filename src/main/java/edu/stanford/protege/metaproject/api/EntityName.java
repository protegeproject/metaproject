package edu.stanford.protege.metaproject.api;

/**
 * A representation of an entity name, composed of a prefix (e.g., "class") and a suffix (e.g. "A")
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface EntityName {

    /**
     * Get the full entity name, i.e., concatenation of prefix and suffix
     *
     * @return Full entity name
     */
    String get();

    /**
     * Get the entity name prefix
     *
     * @return Name prefix
     */
    EntityNamePrefix getPrefix();

    /**
     * Get the entity name suffix
     *
     * @return Name suffix
     */
    EntityNameSuffix getSuffix();

}
