package edu.stanford.protege.metaproject;

import java.util.Set;

/**
 * A representation of a project
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Project {

    /**
     * Get the identifier of the project
     *
     * @return Project identifier instance
     */
    ProjectId getId();

    /**
     * Get the description of the project
     *
     * @return Project description instance
     */
    ProjectDescription getDescription();

    /**
     * Get the owners of the project
     *
     * @return Set of users that own the project
     */
    Set<User> getOwners();

    /**
     * Get the administrators of the project
     *
     * @return Set of users that administrate the project
     */
    Set<User> getAdministrators();

}
