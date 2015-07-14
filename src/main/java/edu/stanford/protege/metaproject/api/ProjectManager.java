package edu.stanford.protege.metaproject.api;

import java.util.Set;

/**
 * A manager for projects
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ProjectManager {

    /**
     * Add a project to the project registry
     *
     * @param project   New project
     */
    void addProject(Project project);

    /**
     * Remove a project from the project registry
     *
     * @param project   Project to be removed
     */
    void removeProject(Project project);

    /**
     * Get the set of all projects
     *
     * @return Set of projects
     */
    Set<Project> getProjects();

    /**
     * Get a project by its identifier
     *
     * @param projectId   Project identifier
     * @return Project instance
     */
    Project getProject(ProjectId projectId);

    /**
     * Get the set of projects with the specified project name
     *
     * @param projectName   Project name
     * @return Set of projects
     */
    Set<Project> getProjects(ProjectName projectName);

    /**
     * Change the name of the given project
     *
     * @param project   Project instance
     * @param projectName   New project name
     */
    void changeProjectName(Project project, ProjectName projectName);

    /**
     * Change the description of the given project
     *
     * @param project   Project instance
     * @param projectDescription    New project description
     */
    void changeProjectDescription(Project project, ProjectDescription projectDescription);

}
