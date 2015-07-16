package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.ProjectNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A manager for projects
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectManager {
    private Set<Project> projects = new HashSet<>();

    public ProjectManager() { }

    /**
     * Add a project to the project registry
     *
     * @param project   New project
     */
    public void addProject(Project project) {
        projects.add(checkNotNull(project));
    }

    /**
     * Remove a project from the project registry
     *
     * @param project   Project to be removed
     * @throws ProjectNotFoundException Project not found
     */
    public void removeProject(Project project) throws ProjectNotFoundException {
        if(!projects.contains(project)) {
            throw new ProjectNotFoundException("The specified project does not exist");
        }
        projects.remove(project);
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
    public Optional<Project> getProject(ProjectId projectId) {
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
    private Project getProjectOrFail(ProjectId projectId) throws ProjectNotFoundException {
        Optional<Project> project = getProject(projectId);
        if(project.isPresent()) {
            return project.get();
        }
        else {
            throw new ProjectNotFoundException("The specified project does not exist");
        }
    }

    /**
     * Get the set of projects with the specified project name
     *
     * @param projectName   Project name
     * @return Set of projects
     */
    public Set<Project> getProjects(ProjectName projectName) {
        return projects.stream().filter(project -> project.getName().equals(projectName)).collect(Collectors.toSet());
    }

    /**
     * Change the name of the given project
     *
     * @param projectId   Project identifier
     * @param projectName   New project name
     * @throws ProjectNotFoundException Project not found
     */
    public void changeProjectName(ProjectId projectId, ProjectName projectName) throws ProjectNotFoundException {
        Project project = getProjectOrFail(projectId);
        removeProject(project);

        Project newProject = new Project(project.getId(), projectName, project.getDescription(), project.getOwner(), project.getAdministrators());
        addProject(newProject);
    }

    /**
     * Change the description of the given project
     *
     * @param projectId   Project identifier
     * @param projectDescription    New project description
     * @throws ProjectNotFoundException Project not found
     */
    public void changeProjectDescription(ProjectId projectId, ProjectDescription projectDescription) throws ProjectNotFoundException {
        Project project = getProjectOrFail(projectId);
        removeProject(project);

        Project newProject = new Project(project.getId(), project.getName(), projectDescription, project.getOwner(), project.getAdministrators());
        addProject(newProject);
    }

    /**
     * Change the owner of the specified project
     *
     * @param projectId   Project identifier
     * @param userId    User identifier of new owner
     * @throws ProjectNotFoundException Project not found
     */
    public void changeOwner(ProjectId projectId, UserId userId) throws ProjectNotFoundException {
        Project project = getProjectOrFail(projectId);
        removeProject(project);

        Project newProject = new Project(project.getId(), project.getName(), project.getDescription(), userId, project.getAdministrators());
        addProject(newProject);
    }

    /**
     * Add an administrator user to the specified project
     *
     * @param projectId   Project identifier
     * @param userId    User identifier of administrator to be added
     * @throws ProjectNotFoundException Project not found
     */
    public void addAdministrator(ProjectId projectId, UserId userId) throws ProjectNotFoundException {
        Set<UserId> users = new HashSet<>();
        users.add(userId);
        addAdministrators(projectId, users);
    }

    /**
     * Add a set of administrator users to the specified project
     *
     * @param projectId   Project identifier
     * @param userIdSet    Set of user identifiers of administrators to be added
     * @throws ProjectNotFoundException Project not found
     */
    public void addAdministrators(ProjectId projectId, Set<UserId> userIdSet) throws ProjectNotFoundException {
        Project project = getProjectOrFail(projectId);
        removeProject(project);

        Set<UserId> administrators = project.getAdministrators();
        administrators.addAll(userIdSet);

        Project newProject = new Project(project.getId(), project.getName(), project.getDescription(), project.getOwner(), administrators);
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
        users.add(userId);
        removeAdministrators(projectId, users);
    }

    /**
     * Remove a set of administrator users from the specified project
     *
     * @param projectId   Project identifier
     * @param userIdSet    Set of user identifiers of administrators to be removed
     * @throws ProjectNotFoundException Project not found
     */
    public void removeAdministrators(ProjectId projectId, Set<UserId> userIdSet) throws ProjectNotFoundException {
        Project project = getProjectOrFail(projectId);
        removeProject(project);

        Set<UserId> administrators = project.getAdministrators();
        administrators.removeAll(userIdSet);

        Project newProject = new Project(project.getId(), project.getName(), project.getDescription(), project.getOwner(), administrators);
        addProject(newProject);
    }
}
