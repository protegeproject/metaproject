package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

import java.io.Serializable;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of a project, consisting of a unique (internal) identifier, a (display) name, and a
 * description. A project is owned by a single user, and can have multiple administrators.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class Project implements Serializable, HasDetails {
    private static final long serialVersionUID = -7194799156371898377L;
    private final ProjectId projectId;
    private final ProjectName projectName;
    private final ProjectDescription projectDescription;
    private final UserId owner;
    private final ImmutableSet<UserId> administrators;

    /**
     * Constructor
     *
     * @param projectId Project identifier
     * @param projectName   Project name
     * @param projectDescription    Project description
     * @param owner Owner of the project
     * @param administrators    Administrators of the project
     */
    public Project(ProjectId projectId, ProjectName projectName, ProjectDescription projectDescription, UserId owner, Set<UserId> administrators) {
        this.projectId = checkNotNull(projectId);
        this.projectName = checkNotNull(projectName);
        this.projectDescription = checkNotNull(projectDescription);
        this.owner = checkNotNull(owner);

        ImmutableSet<UserId> administratorsCopy = new ImmutableSet.Builder<UserId>().addAll(checkNotNull(administrators)).build();
        this.administrators = checkNotNull(administratorsCopy);
    }

    /**
     * Get the identifier of the project
     *
     * @return Project identifier
     */
    public ProjectId getId() {
        return projectId;
    }

    /**
     * Get the name of the project
     *
     * @return Project name
     */
    public ProjectName getName() {
        return projectName;
    }

    /**
     * Get the description of the project
     *
     * @return Project description
     */
    public ProjectDescription getDescription() {
        return projectDescription;
    }

    /**
     * Get the user identifier of the owner of the project
     *
     * @return User identifier of the project owner
     */
    public UserId getOwner() {
        return owner;
    }

    /**
     * Get the administrators of the project
     *
     * @return Set of identifiers of the users that administrate the project
     */
    public Set<UserId> getAdministrators() {
        return administrators;
    }

    /**
     * Check whether the specified user is an administrator of the project
     *
     * @param userId  User identifier
     * @return true if user is an administrator of this project, false otherwise
     */
    public boolean hasAdministrator(UserId userId) {
        if(administrators.contains(userId)) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equal(projectId, project.projectId) &&
                Objects.equal(projectName, project.projectName) &&
                Objects.equal(projectDescription, project.projectDescription) &&
                Objects.equal(owner, project.owner) &&
                Objects.equal(administrators, project.administrators);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(projectId, projectName, projectDescription, owner, administrators);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("projectId", projectId)
                .add("projectName", projectName)
                .add("projectDescription", projectDescription)
                .add("owner", owner)
                .add("administrators", administrators)
                .toString();
    }
}
