package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Manager {

    /**
     * Verify whether the access control object with the given identifier exists in the registry
     *
     * @param id    Access control object identifier
     * @return true if the identifier corresponds to an existing, registered element, false otherwise
     */
    boolean exists(AccessControlObjectId id);

}
