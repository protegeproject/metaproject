package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.UnknownProjectIdException;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectRegistryImpl implements ProjectRegistry, Serializable {
    private static final long serialVersionUID = -3204696327513123400L;
    private Set<Project> projects = new HashSet<>();

    /**
     * Constructor
     *
     * @param projects  Set of projects
     */
    public ProjectRegistryImpl(Set<Project> projects) {
        this.projects = checkNotNull(projects);
    }

    /**
     * No-arguments constructor
     */
    public ProjectRegistryImpl() { }

    @Override
    public void add(Project... projects) {
        checkNotNull(projects);
        for(Project p : projects) {
            this.projects.add(checkNotNull(p));
        }
    }

    @Override
    public void remove(Project... projects) {
        checkNotNull(projects);
        for(Project p : projects) {
            checkNotNull(p);
            this.projects.remove(p);
        }
    }

    @Override
    public Set<Project> getProjects() {
        return projects;
    }

    @Override
    public Project getProject(ProjectId projectId) throws UnknownProjectIdException {
        Optional<Project> project = getProjectOptional(projectId);
        if(project.isPresent()) {
            return project.get();
        }
        else {
            throw new UnknownProjectIdException("The specified project identifier does not correspond to an existing project");
        }
    }

    @Override
    public Set<Project> getProjects(Name projectName) {
        checkNotNull(projectName);
        return projects.stream().filter(project -> project.getName().equals(projectName)).collect(Collectors.toSet());
    }

    @Override
    public void changeName(ProjectId projectId, Name projectName) throws UnknownProjectIdException {
        checkNotNull(projectName);
        Project project = getProject(projectId);
        remove(project);

        Project newProject = getProject(project.getId(), projectName, project.getDescription(), project.getAddress(), project.getOwner(), project.getAdministrators());
        add(newProject);
    }

    @Override
    public void changeDescription(ProjectId projectId, Description projectDescription) throws UnknownProjectIdException {
        checkNotNull(projectDescription);
        Project project = getProject(projectId);
        remove(project);

        Project newProject = getProject(project.getId(), project.getName(), projectDescription, project.getAddress(), project.getOwner(), project.getAdministrators());
        add(newProject);
    }

    @Override
    public void changeOwner(ProjectId projectId, UserId userId) throws UnknownProjectIdException {
        checkNotNull(userId);
        Project project = getProject(projectId);
        remove(project);

        Project newProject = getProject(project.getId(), project.getName(), project.getDescription(), project.getAddress(), userId, project.getAdministrators());
        add(newProject);
    }

    @Override
    public void changeAddress(ProjectId projectId, Address projectAddress) throws UnknownProjectIdException {
        checkNotNull(projectAddress);
        Project project = getProject(projectId);
        remove(project);

        Project newProject = getProject(project.getId(), project.getName(), project.getDescription(), projectAddress, project.getOwner(), project.getAdministrators());
        add(newProject);
    }

    @Override
    public void addAdministrator(ProjectId projectId, UserId... userIds) throws UnknownProjectIdException {
        checkNotNull(userIds);
        Project project = getProject(projectId);
        remove(project);

        Set<UserId> administrators = new HashSet<>(project.getAdministrators());
        Collections.addAll(administrators, userIds);

        Project newProject = getProject(project.getId(), project.getName(), project.getDescription(), project.getAddress(), project.getOwner(), administrators);
        add(newProject);
    }

    @Override
    public void removeAdministrator(ProjectId projectId, UserId... userIds) throws UnknownProjectIdException {
        checkNotNull(userIds);
        Project project = getProject(projectId);
        remove(project);

        Set<UserId> administrators = new HashSet<>(project.getAdministrators());
        for(UserId userId : userIds) {
            administrators.remove(checkNotNull(userId));
        }

        Project newProject = getProject(project.getId(), project.getName(), project.getDescription(), project.getAddress(), project.getOwner(), administrators);
        add(newProject);
    }

    @Override
    public boolean contains(AccessControlObjectId projectId) {
        checkNotNull(projectId);
        for(Project p : projects) {
            if(p.getId().equals(projectId)) {
                return true;
            }
        }
        return false;
    }

    private Project getProject(ProjectId id, Name name, Description description, Address address, UserId owner, Set<UserId> administrators) {
        return Manager.getFactory().createProject(id, name, description, address, owner, administrators);
    }

    /**
     * Get a project by its identifier
     *
     * @param projectId   Project identifier
     * @return Project instance
     */
    private Optional<Project> getProjectOptional(ProjectId projectId) {
        checkNotNull(projectId);
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
        ProjectRegistryImpl that = (ProjectRegistryImpl) o;
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
