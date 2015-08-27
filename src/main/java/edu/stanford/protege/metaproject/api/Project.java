package edu.stanford.protege.metaproject.api;

import java.util.Set;

/**
 * A representation of a project, consisting of a unique (internal) identifier, a (display) name, and a
 * description. A project is owned by a single user, and can have multiple administrators.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Project extends AccessControlObject, HasName, HasDescription, HasAddress, Comparable<Project> {

    /**
     * Get the identifier of the project
     *
     * @return Project identifier
     */
    ProjectId getId();

    /**
     * Get the owner user of the project
     *
     * @return Project owner user
     */
    UserId getOwner();

    /**
     * Get the administrators of the project
     *
     * @return Set of users that administrate the project
     */
    Set<UserId> getAdministrators();

    /**
     * Check whether the specified user is an administrator of the project
     *
     * @param userId  User identifier
     * @return true if user is an administrator of this project, false otherwise
     */
    boolean hasAdministrator(UserId userId);

}
