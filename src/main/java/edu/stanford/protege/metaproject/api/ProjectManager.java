package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.AccessControlObjectNotFoundException;
import edu.stanford.protege.metaproject.api.exception.ProjectNotFoundException;

import java.util.Set;

/**
 * A manager for accessing, adding, removing or editing existing projects.
 *
 * The projects manager follows the singleton pattern; only one instance of this manager may exist.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ProjectManager extends Manager {

    /**
     * Add the given project(s) to the project registry
     *
     * @param project   One or more new projects
     */
    void addProject(Project... project);

    /**
     * Remove the specified project(s) from the project registry
     *
     * @param project   One or more projects to be removed
     * @throws ProjectNotFoundException Project not found
     */
    void removeProject(Project... project) throws AccessControlObjectNotFoundException;

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
     * @throws ProjectNotFoundException    Project not found
     */
    Project getProject(ProjectId projectId) throws ProjectNotFoundException;

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
     * @throws ProjectNotFoundException Project not found
     */
    void changeProjectName(ProjectId projectId, Name projectName) throws ProjectNotFoundException;

    /**
     * Change the description of the given project
     *
     * @param projectId   Project identifier
     * @param projectDescription    New project description
     * @throws ProjectNotFoundException Project not found
     */
    void changeProjectDescription(ProjectId projectId, Description projectDescription) throws ProjectNotFoundException;

    /**
     * Change the owner of the specified project
     *
     * @param projectId   Project identifier
     * @param userId    New owner user identifier
     * @throws ProjectNotFoundException Project not found
     */
    void changeOwner(ProjectId projectId, UserId userId) throws ProjectNotFoundException;

    /**
     * Change the location of the specified project
     *
     * @param projectId Project identifier
     * @param projectAddress   Project address
     * @throws ProjectNotFoundException Project not found
     */
    void changeLocation(ProjectId projectId, Address projectAddress) throws ProjectNotFoundException;

    /**
     * Add an administrator user to the specified project
     *
     * @param projectId   Project identifier
     * @param userId    Administrator user identifier user to be added
     * @throws ProjectNotFoundException Project not found
     */
    void addAdministrator(ProjectId projectId, UserId userId) throws ProjectNotFoundException;

    /**
     * Add a set of administrator users to the specified project
     *
     * @param projectId   Project identifier
     * @param users    Set of user identifiers of administrators to be added
     * @throws ProjectNotFoundException Project not found
     */
    void addAdministrators(ProjectId projectId, Set<UserId> users) throws ProjectNotFoundException;

    /**
     * Remove an administrator user from the specified project
     *
     * @param projectId   Project identifier
     * @param userId    User identifier of administrator to be removed
     * @throws ProjectNotFoundException Project not found
     */
    void removeAdministrator(ProjectId projectId, UserId userId) throws ProjectNotFoundException;

    /**
     * Remove a set of administrator users from the specified project
     *
     * @param projectId   Project identifier
     * @param users    Set of user identifiers of administrators to be removed
     * @throws ProjectNotFoundException Project not found
     */
    void removeAdministrators(ProjectId projectId, Set<UserId> users) throws ProjectNotFoundException;

}
