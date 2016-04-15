package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.IdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;
import edu.stanford.protege.metaproject.api.exception.UnknownProjectIdException;

import java.io.File;
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
public class ProjectRegistryImpl implements ProjectRegistry, Serializable {
    private static final long serialVersionUID = 2396045530027040761L;
    private Set<Project> projects = new HashSet<>();

    /**
     * Constructor
     *
     * @param projects  Set of projects
     */
    public ProjectRegistryImpl(Set<Project> projects) {
        this.projects = checkNotNull(projects);
    }

    @Override
    public void add(Project project) throws IdAlreadyInUseException {
        checkNotNull(project);
        if (contains(project.getId())) {
            throw new IdAlreadyInUseException("The specified project identifier is already used by another project");
        }
        projects.add(project);
    }

    @Override
    public void remove(Project project) {
        checkNotNull(project);
        projects.remove(project);
    }

    @Override
    public Set<Project> getEntries() {
        return projects;
    }

    @Override
    public <E extends MetaprojectObjectId> Project get(E id) throws UnknownMetaprojectObjectIdException {
        if(!(id instanceof ProjectId)) {
            throw new IllegalArgumentException("Programmer error: Expected a project identifier");
        }
        Optional<Project> project = getProjectOptional((ProjectId)id);
        if(project.isPresent()) {
            return project.get();
        }
        else {
            throw new UnknownProjectIdException("The specified project identifier does not correspond to an existing project");
        }
    }

    @Override
    public Set<Project> getEntries(Name projectName) {
        checkNotNull(projectName);
        return projects.stream().filter(project -> project.getName().equals(projectName)).collect(Collectors.toSet());
    }

    @Override
    public void setName(ProjectId projectId, Name projectName) throws UnknownMetaprojectObjectIdException {
        checkNotNull(projectName);
        Project project = get(projectId);
        Project newProject = createProject(project.getId(), projectName, project.getDescription(), project.getFile(),
                project.getOwner(), project.getOptions());
        update(projectId, newProject);
    }

    @Override
    public void setDescription(ProjectId projectId, Description projectDescription) throws UnknownMetaprojectObjectIdException {
        checkNotNull(projectDescription);
        Project project = get(projectId);
        Project newProject = createProject(project.getId(), project.getName(), projectDescription, project.getFile(),
                project.getOwner(), project.getOptions());
        update(projectId, newProject);
    }

    @Override
    public void setOwner(ProjectId projectId, UserId userId) throws UnknownMetaprojectObjectIdException {
        checkNotNull(userId);
        Project project = get(projectId);
        Project newProject = createProject(project.getId(), project.getName(), project.getDescription(), project.getFile(),
                userId, project.getOptions());
        update(projectId, newProject);
    }

    @Override
    public void setFile(ProjectId projectId, File file) throws UnknownMetaprojectObjectIdException {
        checkNotNull(file);
        Project project = get(projectId);
        Project newProject = createProject(project.getId(), project.getName(), project.getDescription(), file,
                project.getOwner(), project.getOptions());
        update(projectId, newProject);
    }

    @Override
    public void setOptions(ProjectId projectId, ProjectOptions projectOptions) throws UnknownMetaprojectObjectIdException {
        checkNotNull(projectId);
        checkNotNull(projectOptions);
        Project project = get(projectId);
        Project newProject = createProject(projectId, project.getName(), project.getDescription(), project.getFile(),
                project.getOwner(), Optional.of(projectOptions));
        update(projectId, newProject);
    }

    @Override
    public <E extends MetaprojectObjectId> void update(E id, Project newObj) throws UnknownMetaprojectObjectIdException {
        remove(get(id));
        projects.add(newObj);
    }

    @Override
    public boolean contains(Project obj) {
        checkNotNull(obj);
        return projects.contains(obj);
    }

    @Override
    public <E extends MetaprojectObjectId> boolean contains(E id) {
        checkNotNull(id);
        for(Project p : projects) {
            if(p.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create an instance of a project
     */
    private Project createProject(ProjectId id, Name name, Description description, File file,
                                  UserId owner, Optional<ProjectOptions> projectOptions) {
        return Manager.getFactory().getProject(id, name, description, file, owner, projectOptions);
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
