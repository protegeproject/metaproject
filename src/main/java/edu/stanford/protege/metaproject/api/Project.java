package edu.stanford.protege.metaproject.api;

import java.util.Set;

/**
 * A representation of a project, consisting of a unique (internal) identifier, a (display) name, and a
 * description. A project is owned by a single user, and can have multiple administrators.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Project {

    /**
     * Get the identifier of the project
     *
     * @return Project identifier
     */
    ProjectId getId();

    /**
     * Get the name of the project
     *
     * @return Project name
     */
    ProjectName getName();

    /**
     * Get the description of the project
     *
     * @return Project description
     */
    ProjectDescription getDescription();

    /**
     * Get the user identifier of the owner of the project
     *
     * @return User identifier of the project owner
     */
    UserId getOwner();

    /**
     * Get the administrators of the project
     *
     * @return Set of identifiers of the users that administrate the project
     */
    Set<UserId> getAdministrators();

}
