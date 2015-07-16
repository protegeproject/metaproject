package edu.stanford.protege.metaproject.api;

import com.google.common.collect.ImmutableSet;

import java.io.Serializable;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A role defines a group of allowed operations within some project
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class Role implements Serializable, HasDetails {
    private static final long serialVersionUID = 5181332634932631114L;
    private final RoleId roleId;
    private final RoleName roleName;
    private final RoleDescription roleDescription;
    private final ImmutableSet<ProjectId> projects;
    private final ImmutableSet<OperationId> operations;

    /**
     * Constructor
     *
     * @param roleId    Role identifier
     * @param roleName  Role name
     * @param roleDescription   Role description
     * @param projects  Set of projects within which the specified operations can be performed
     * @param operations    Set of operations that can be performed on the given projects
     */
    public Role(RoleId roleId, RoleName roleName, RoleDescription roleDescription, Set<ProjectId> projects, Set<OperationId> operations) {
        this.roleId = checkNotNull(roleId);
        this.roleName = checkNotNull(roleName);
        this.roleDescription = checkNotNull(roleDescription);

        ImmutableSet<ProjectId> projectsCopy = new ImmutableSet.Builder<ProjectId>().addAll(checkNotNull(projects)).build();
        this.projects = checkNotNull(projectsCopy);

        ImmutableSet<OperationId> operationsCopy = new ImmutableSet.Builder<OperationId>().addAll(checkNotNull(operations)).build();
        this.operations = checkNotNull(operationsCopy);
    }

    /**
     * Get the role identifier
     *
     * @return Role identifier
     */
    public RoleId getId() {
        return roleId;
    }

    /**
     * Get the role name
     * @return  Role name
     */
    public RoleName getName() {
        return roleName;
    }

    /**
     * Get the role description
     *
     * @return Role description
     */
    public RoleDescription getDescription() {
        return roleDescription;
    }

    /**
     * Get all projects associated with this role
     *
     * @return Set of project identifiers
     */
    public Set<ProjectId> getProjects() {
        return projects;
    }

    /**
     * Get the set of operations associated with this role
     *
     * @return Set of operations identifiers
     */
    public Set<OperationId> getOperations() {
        return operations;
    }
}
