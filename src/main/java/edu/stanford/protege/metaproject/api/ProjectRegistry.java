package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UnknownProjectIdException;

import java.util.Set;

/**
 * A manager for accessing, adding, removing or editing existing projects.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ProjectRegistry {

    /**
     * Add the given project(s) to the project registry
     *
     * @param project   One or more new projects
     */
    void add(Project... project);

    /**
     * Remove the specified project(s) from the project registry
     *
     * @param project   One or more projects to be removed
     */
    void remove(Project... project);

    /**
     * Get the set of all projects
     *
     * @return Set of projects
     */
    Set<Project> getProjects();

    /**
     * A convenience method to fetch a project or die trying (with an exception)
     *
     * @param projectId    Project identifier
     * @return Project instance
     * @throws UnknownProjectIdException    Project identifier is not recognized
     */
    Project getProject(ProjectId projectId) throws UnknownProjectIdException;

    /**
     * Get the set of projects with the specified project name
     *
     * @param projectName   Project name
     * @return Set of projects
     */
    Set<Project> getProjects(Name projectName);

    /**
     * Change the name of the given project
     *
     * @param projectId   Project identifier
     * @param projectName   New project name
     * @throws UnknownProjectIdException    Project identifier is not recognized
     */
    void setName(ProjectId projectId, Name projectName) throws UnknownProjectIdException;

    /**
     * Change the description of the given project
     *
     * @param projectId   Project identifier
     * @param projectDescription    New project description
     * @throws UnknownProjectIdException    Project identifier is not recognized
     */
    void setDescription(ProjectId projectId, Description projectDescription) throws UnknownProjectIdException;

    /**
     * Change the owner of the specified project
     *
     * @param projectId   Project identifier
     * @param userId    New owner user identifier
     * @throws UnknownProjectIdException    Project identifier is not recognized
     */
    void setOwner(ProjectId projectId, UserId userId) throws UnknownProjectIdException;

    /**
     * Change the location of the specified project
     *
     * @param projectId Project identifier
     * @param projectAddress   Project address
     * @throws UnknownProjectIdException    Project identifier is not recognized
     */
    void setAddress(ProjectId projectId, Address projectAddress) throws UnknownProjectIdException;

    /**
     * Set the options for this project
     *
     * @param projectId Project identifier
     * @param projectOptions    Project options
     * @throws UnknownProjectIdException    Project identifier is not recognized
     */
    void setOptions(ProjectId projectId, ProjectOptions projectOptions) throws UnknownProjectIdException;

    /**
     * Check whether the project registry contains a projectwith the given identifier
     *
     * @param projectId    Project identifier
     * @return true if there is a project with the specified identifier, false otherwise
     */
    boolean contains(ProjectId projectId);

}
