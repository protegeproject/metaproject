package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.ProjectNotFoundException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A manager for accessing, adding, removing or editing existing projects.
 *
 * The projects manager follows the singleton pattern; only one instance of this manager may exist.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectManagerImpl implements ProjectManager, Serializable {
    private static final long serialVersionUID = -5071816958481766140L;
    private Set<Project> projects = new HashSet<>();

    /**
     * Constructor
     *
     * @param projects  Set of projects
     */
    public ProjectManagerImpl(Set<Project> projects) {
        this.projects = checkNotNull(projects);
    }

    /**
     * No-arguments constructor
     */
    public ProjectManagerImpl() { }

    /**
     * Add the given project(s) to the project registry
     *
     * @param project   One or more new projects
     */
    public void addProject(Project... project) {
        for(Project p : project) {
            projects.add(checkNotNull(p));
        }
    }

    /**
     * Remove the specified project(s) from the project registry
     *
     * @param project   One or more projects to be removed
     * @throws ProjectNotFoundException Project not found
     */
    public void removeProject(Project... project) throws ProjectNotFoundException {
        for(Project p : project) {
            if (!projects.contains(checkNotNull(p))) {
                throw new ProjectNotFoundException("The specified project does not exist");
            }
            projects.remove(p);
        }
    }

    /**
     * Get the set of all projects
     *
     * @return Set of projects
     */
    public Set<Project> getProjects() {
        return projects;
    }

    /**
     * Get a project by its identifier
     *
     * @param projectId   Project identifier
     * @return Project instance
     */
    private Optional<Project> getProjectOptional(Id projectId) {
        Project project = null;
        for(Project p : projects) {
            if(p.getId().equals(projectId)) {
                project = p; break;
            }
        }
        return Optional.ofNullable(project);
    }

    /**
     * A convenience method to fetch a project or die trying (with an exception)
     *
     * @param projectId    Project identifier
     * @return Project instance
     * @throws ProjectNotFoundException    Project not found
     */
    public Project getProject(ProjectId projectId) throws ProjectNotFoundException {
        Optional<Project> project = getProjectOptional(checkNotNull(projectId));
        if(project.isPresent()) {
            return project.get();
        }
        else {
            throw new ProjectNotFoundException("The specified project identifier does not correspond to an existing project");
        }
    }

    /**
     * Get the set of projects with the specified project name
     *
     * @param projectName   Project name
     * @return Set of projects
     */
    public Set<Project> getProjects(Name projectName) {
        return projects.stream().filter(project -> project.getName().equals(checkNotNull(projectName))).collect(Collectors.toSet());
    }

    /**
     * Change the name of the given project
     *
     * @param projectId   Project identifier
     * @param projectName   New project name
     * @throws ProjectNotFoundException Project not found
     */
    public void changeProjectName(ProjectId projectId, Name projectName) throws ProjectNotFoundException {
        Project project = getProject(projectId);
        removeProject(project);

        Project newProject = new ProjectImpl(project.getId(), checkNotNull(projectName), project.getDescription(), project.getAddress(), project.getOwner(), project.getAdministrators());
        addProject(newProject);
    }

    /**
     * Change the description of the given project
     *
     * @param projectId   Project identifier
     * @param projectDescription    New project description
     * @throws ProjectNotFoundException Project not found
     */
    public void changeProjectDescription(ProjectId projectId, Description projectDescription) throws ProjectNotFoundException {
        Project project = getProject(projectId);
        removeProject(project);

        Project newProject = new ProjectImpl(project.getId(), project.getName(), checkNotNull(projectDescription), project.getAddress(), project.getOwner(), project.getAdministrators());
        addProject(newProject);
    }

    /**
     * Change the owner of the specified project
     *
     * @param projectId   Project identifier
     * @param userId    New owner user identifier
     * @throws ProjectNotFoundException Project not found
     */
    public void changeOwner(ProjectId projectId, UserId userId) throws ProjectNotFoundException {
        Project project = getProject(projectId);
        removeProject(project);

        Project newProject = new ProjectImpl(project.getId(), project.getName(), project.getDescription(), project.getAddress(), checkNotNull(userId), project.getAdministrators());
        addProject(newProject);
    }

    /**
     * Change the location of the specified project
     *
     * @param projectId Project identifier
     * @param projectAddress   Project address
     * @throws ProjectNotFoundException Project not found
     */
    public void changeLocation(ProjectId projectId, Address projectAddress) throws ProjectNotFoundException {
        Project project = getProject(projectId);
        removeProject(project);

        Project newProject = new ProjectImpl(project.getId(), project.getName(), project.getDescription(), checkNotNull(projectAddress), project.getOwner(), project.getAdministrators());
        addProject(newProject);
    }

    /**
     * Add an administrator user to the specified project
     *
     * @param projectId   Project identifier
     * @param userId    Administrator user identifier user to be added
     * @throws ProjectNotFoundException Project not found
     */
    public void addAdministrator(ProjectId projectId, UserId userId) throws ProjectNotFoundException {
        Set<UserId> users = new HashSet<>();
        users.add(checkNotNull(userId));
        addAdministrators(checkNotNull(projectId), users);
    }

    /**
     * Add a set of administrator users to the specified project
     *
     * @param projectId   Project identifier
     * @param users    Set of user identifiers of administrators to be added
     * @throws ProjectNotFoundException Project not found
     */
    public void addAdministrators(ProjectId projectId, Set<UserId> users) throws ProjectNotFoundException {
        Project project = getProject(projectId);
        removeProject(project);

        Set<UserId> administrators = project.getAdministrators();
        administrators.addAll(checkNotNull(users));

        Project newProject = new ProjectImpl(project.getId(), project.getName(), project.getDescription(), project.getAddress(), project.getOwner(), administrators);
        addProject(newProject);
    }

    /**
     * Remove an administrator user from the specified project
     *
     * @param projectId   Project identifier
     * @param userId    User identifier of administrator to be removed
     * @throws ProjectNotFoundException Project not found
     */
    public void removeAdministrator(ProjectId projectId, UserId userId) throws ProjectNotFoundException {
        Set<UserId> users = new HashSet<>();
        users.add(checkNotNull(userId));
        removeAdministrators(checkNotNull(projectId), users);
    }

    /**
     * Remove a set of administrator users from the specified project
     *
     * @param projectId   Project identifier
     * @param users    Set of user identifiers of administrators to be removed
     * @throws ProjectNotFoundException Project not found
     */
    public void removeAdministrators(ProjectId projectId, Set<UserId> users) throws ProjectNotFoundException {
        Project project = getProject(projectId);
        removeProject(project);

        Set<UserId> administrators = project.getAdministrators();
        administrators.removeAll(checkNotNull(users));

        Project newProject = new ProjectImpl(project.getId(), project.getName(), project.getDescription(), project.getAddress(), project.getOwner(), administrators);
        addProject(newProject);
    }

    /**
     * Verify whether a given project identifier corresponds to a registered project
     *
     * @param projectId Project identifier
     * @return true if project with the given project identifier exists, false otherwise
     */
    public boolean exists(AccessControlObjectId projectId) {
        for(Project p : projects) {
            if(p.getId().equals(projectId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectManagerImpl that = (ProjectManagerImpl) o;
        return Objects.equal(projects, that.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(projects);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("projects", projects)
                .toString();
    }
}
