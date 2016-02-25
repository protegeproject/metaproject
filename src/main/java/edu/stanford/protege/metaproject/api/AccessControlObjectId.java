package edu.stanford.protege.metaproject.api;

/**
 * An identifier of an access control object (such as a user, operation, ...)
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface AccessControlObjectId extends TextProperty {

    /**
     * Get the type of access control object
     *
     * @return Type of access control object
     */
    AccessControlObjectIdType getType();

}
