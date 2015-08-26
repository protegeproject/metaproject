package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.RoleNotFoundException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A manager for roles played by users, i.e., the set of operations that are allowed to be
 * performed on a set of projects defined as part of the role.
 *
 * The role manager follows the singleton pattern; only one instance of this manager may exist.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleManagerImpl implements RoleManager, Serializable {
    private static final long serialVersionUID = 4502007814521085197L;
    private Set<Role> roles = new HashSet<>();

    /**
     * Constructor
     *
     * @param roles Set of roles
     */
    public RoleManagerImpl(Set<Role> roles) {
        this.roles = checkNotNull(roles);
    }

    /**
     * No-arguments constructor
     */
    public RoleManagerImpl() { }

    /**
     * Add the specified role(s)
     *
     * @param roles  One or more roles
     */
    public void add(Role... roles) {
        for(Role r : roles) {
            this.roles.add(checkNotNull(r));
        }
    }

    /**
     * Remove the given role(s)
     *
     * @param role  One or more roles
     * @throws RoleNotFoundException    Role not found
     */
    public void remove(Role... role) throws RoleNotFoundException {
        for(Role r : role) {
            if (!roles.contains(r)) {
                throw new RoleNotFoundException("The specified role does not exist");
            }
            roles.remove(r);
        }
    }

    /**
     * Get the set of all roles
     *
     * @return Set of existing roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Get a role based on its identifier
     *
     * @param roleId    Role identifier
     * @return Role instance
     */
    private Optional<Role> getRoleOptional(Id roleId) {
        Role role = null;
        for(Role r : roles) {
            if(r.getId().equals(roleId)) {
                role = r; break;
            }
        }
        return Optional.ofNullable(role);
    }

    /**
     * A convenience method to fetch a role or die trying (with an exception)
     *
     * @param roleId    Role identifier
     * @return Role instance
     * @throws RoleNotFoundException    Role not found
     */
    public Role getRole(RoleId roleId) throws RoleNotFoundException {
        Optional<Role> role = getRoleOptional(roleId);
        if(role.isPresent()) {
            return role.get();
        }
        else {
            throw new RoleNotFoundException("The specified role identifier does not correspond to an existing role");
        }
    }

    /**
     * Change the name of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleName  New role name
     * @throws RoleNotFoundException    Role not found
     */
    public void changeRoleName(RoleId roleId, Name roleName) throws RoleNotFoundException {
        Role role = getRole(roleId);
        remove(role);
        Role newRole = new RoleImpl(role.getId(), roleName, role.getDescription(), role.getProjects(), role.getOperations());
        add(newRole);
    }

    /**
     * Change the description of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleDescription   New role description
     * @throws RoleNotFoundException    Role not found
     */
    public void changeRoleDescription(RoleId roleId, Description roleDescription) throws RoleNotFoundException {
        Role role = getRole(roleId);
        remove(role);
        Role newRole = new RoleImpl(role.getId(), role.getName(), roleDescription, role.getProjects(), role.getOperations());
        add(newRole);
    }

    /**
     * Add a project to the working projects of the given role
     *
     * @param roleId    Role identifier
     * @param projectId Project identifier
     * @throws RoleNotFoundException    Role not found
     */
    public void addProject(RoleId roleId, ProjectId projectId) throws RoleNotFoundException {
        Set<ProjectId> projects = new HashSet<>();
        projects.add(projectId);
        addProjects(roleId, projects);
    }

    /**
     * Add a set of projects to the working projects of the given role
     *
     * @param roleId    Role identifier
     * @param projectIdSet  Set of project identifiers
     * @throws RoleNotFoundException    Role not found
     */
    public void addProjects(RoleId roleId, Set<ProjectId> projectIdSet) throws RoleNotFoundException {
        Role role = getRole(roleId);
        remove(role);

        Set<ProjectId> projects = role.getProjects();
        projects.addAll(projectIdSet);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), projects, role.getOperations());
        add(newRole);
    }

    /**
     * Remove a project from the working projects of the given role
     *
     * @param roleId    Role identifier
     * @param project Project identifier
     * @throws RoleNotFoundException    Role not found
     */
    public void removeProject(RoleId roleId, ProjectId project) throws RoleNotFoundException {
        Set<ProjectId> projects = new HashSet<>();
        projects.add(project);
        removeProjects(roleId, projects);
    }

    /**
     * Remove a project from the working projects of the given role
     *
     * @param roleId    Role identifier
     * @param projectIds  Set of project identifiers
     * @throws RoleNotFoundException    Role not found
     */
    public void removeProjects(RoleId roleId, Set<ProjectId> projectIds) throws RoleNotFoundException {
        Role role = getRole(roleId);
        remove(role);

        Set<ProjectId> projects = role.getProjects();
        projects.removeAll(projectIds);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), projects, role.getOperations());
        add(newRole);
    }

    /**
     * Add an operation to the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationId   Operation identifier
     * @throws RoleNotFoundException    Role not found
     */
    public void addOperation(RoleId roleId, OperationId operationId) throws RoleNotFoundException {
        Set<OperationId> operations = new HashSet<>();
        operations.add(operationId);
        addOperations(roleId, operations);
    }

    /**
     * Add a set of operations to the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds    Set of operation identifiers
     * @throws RoleNotFoundException    Role not found
     */
    public void addOperations(RoleId roleId, Set<OperationId> operationIds) throws RoleNotFoundException {
        Role role = getRole(roleId);
        remove(role);

        Set<OperationId> operations = role.getOperations();
        operations.addAll(operationIds);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), role.getProjects(), operations);
        add(newRole);
    }

    /**
     * Remove an operation from the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationId   Operation identifier
     * @throws RoleNotFoundException    Role not found
     */
    public void removeOperation(RoleId roleId, OperationId operationId) throws RoleNotFoundException {
        Set<OperationId> operations = new HashSet<>();
        operations.add(operationId);
        removeOperations(roleId, operations);
    }

    /**
     * Remove a set of operations from the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds    Set of operation identifiers
     * @throws RoleNotFoundException    Role not found
     */
    public void removeOperations(RoleId roleId, Set<OperationId> operationIds) throws RoleNotFoundException {
        Role role = getRole(roleId);
        remove(role);

        Set<OperationId> operations = role.getOperations();
        operations.removeAll(operationIds);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), role.getProjects(), operations);
        add(newRole);
    }

    /**
     * Verify whether a given role identifier corresponds to a registered role
     *
     * @param roleId Role identifier
     * @return true if role with the given role identifier exists, false otherwise
     */
    public boolean exists(AccessControlObjectId roleId) {
        for(Role role : roles) {
            if(role.getId().equals(roleId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleManagerImpl that = (RoleManagerImpl) o;
        return Objects.equal(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roles);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("roles", roles)
                .toString();
    }
}
