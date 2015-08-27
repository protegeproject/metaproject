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

    @Override
    public void addProject(Project... project) {
        for(Project p : project) {
            projects.add(checkNotNull(p));
        }
    }

    @Override
    public void removeProject(Project... project) throws ProjectNotFoundException {
        for(Project p : project) {
            if (!projects.contains(checkNotNull(p))) {
                throw new ProjectNotFoundException("The specified project does not exist");
            }
            projects.remove(p);
        }
    }

    @Override
    public Set<Project> getProjects() {
        return projects;
    }

    @Override
    public Project getProject(ProjectId projectId) throws ProjectNotFoundException {
        Optional<Project> project = getProjectOptional(checkNotNull(projectId));
        if(project.isPresent()) {
            return project.get();
        }
        else {
            throw new ProjectNotFoundException("The specified project identifier does not correspond to an existing project");
        }
    }

    @Override
    public Set<Project> getProjects(Name projectName) {
        return projects.stream().filter(project -> project.getName().equals(checkNotNull(projectName))).collect(Collectors.toSet());
    }

    @Override
    public void changeProjectName(ProjectId projectId, Name projectName) throws ProjectNotFoundException {
        Project project = getProject(projectId);
        removeProject(project);

        Project newProject = new ProjectImpl(project.getId(), checkNotNull(projectName), project.getDescription(), project.getAddress(), project.getOwner(), project.getAdministrators());
        addProject(newProject);
    }

    @Override
    public void changeProjectDescription(ProjectId projectId, Description projectDescription) throws ProjectNotFoundException {
        Project project = getProject(projectId);
        removeProject(project);

        Project newProject = new ProjectImpl(project.getId(), project.getName(), checkNotNull(projectDescription), project.getAddress(), project.getOwner(), project.getAdministrators());
        addProject(newProject);
    }

    @Override
    public void changeOwner(ProjectId projectId, UserId userId) throws ProjectNotFoundException {
        Project project = getProject(projectId);
        removeProject(project);

        Project newProject = new ProjectImpl(project.getId(), project.getName(), project.getDescription(), project.getAddress(), checkNotNull(userId), project.getAdministrators());
        addProject(newProject);
    }

    @Override
    public void changeLocation(ProjectId projectId, Address projectAddress) throws ProjectNotFoundException {
        Project project = getProject(projectId);
        removeProject(project);

        Project newProject = new ProjectImpl(project.getId(), project.getName(), project.getDescription(), checkNotNull(projectAddress), project.getOwner(), project.getAdministrators());
        addProject(newProject);
    }

    @Override
    public void addAdministrator(ProjectId projectId, UserId userId) throws ProjectNotFoundException {
        Set<UserId> users = new HashSet<>();
        users.add(checkNotNull(userId));
        addAdministrators(checkNotNull(projectId), users);
    }

    @Override
    public void addAdministrators(ProjectId projectId, Set<UserId> users) throws ProjectNotFoundException {
        Project project = getProject(projectId);
        removeProject(project);

        Set<UserId> administrators = project.getAdministrators();
        administrators.addAll(checkNotNull(users));

        Project newProject = new ProjectImpl(project.getId(), project.getName(), project.getDescription(), project.getAddress(), project.getOwner(), administrators);
        addProject(newProject);
    }

    @Override
    public void removeAdministrator(ProjectId projectId, UserId userId) throws ProjectNotFoundException {
        Set<UserId> users = new HashSet<>();
        users.add(checkNotNull(userId));
        removeAdministrators(checkNotNull(projectId), users);
    }

    @Override
    public void removeAdministrators(ProjectId projectId, Set<UserId> users) throws ProjectNotFoundException {
        Project project = getProject(projectId);
        removeProject(project);

        Set<UserId> administrators = project.getAdministrators();
        administrators.removeAll(checkNotNull(users));

        Project newProject = new ProjectImpl(project.getId(), project.getName(), project.getDescription(), project.getAddress(), project.getOwner(), administrators);
        addProject(newProject);
    }

    @Override
    public boolean exists(AccessControlObjectId projectId) {
        for(Project p : projects) {
            if(p.getId().equals(projectId)) {
                return true;
            }
        }
        return false;
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
