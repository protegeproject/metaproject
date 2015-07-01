package edu.stanford.protege.metaproject.project;

import java.util.Set;

/**
 * A project manager
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
     * Get a project by its identifier
     *
     * @param projectId   Project identifier
     * @return Project instance
     */
    Project getProject(ProjectId projectId);

    /**
     * Get the set of all projects
     *
     * @return Set of projects
     */
    Set<Project> getProjects();

}
