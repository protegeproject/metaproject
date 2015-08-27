package edu.stanford.protege.metaproject.api;

/**
 * A representation of an access control object; a key element of the access control policy
 * such as a user, operation or project
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface AccessControlObject {

    /**
     * Get the identifier of the access control object
     *
     * @return The access control object identifier
     */
    AccessControlObjectId getId();

}
