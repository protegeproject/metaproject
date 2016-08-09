package edu.stanford.protege.metaproject.api;

/**
 * Marker interface for a policy object such as user, role, project, etc.
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface PolicyObject<T extends PolicyObjectId> extends HasId<T>, HasName {

    /**
     * Check whether this object is of type {@link User}
     *
     * @return true if object is a user, false otherwise
     */
    boolean isUser();

    /**
     * Check whether this object is of type {@link Project}
     *
     * @return true if object is a project, false otherwise
     */
    boolean isProject();

    /**
     * Check whether this object is of type {@link Role}
     *
     * @return true if object is a role, false otherwise
     */
    boolean isRole();

    /**
     * Check whether this object is of type {@link Operation}
     *
     * @return true if object is an operation, false otherwise
     */
    boolean isOperation();

}
