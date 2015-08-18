package edu.stanford.protege.metaproject.api.impl;

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
public final class RoleManager implements Manager, Serializable {
    private static final long serialVersionUID = -6811413456868519795L;
    private static RoleManager instance = null;
    private static Set<Role> roles;

    /**
     * Private constructor
     *
     * @param roles Set of roles
     */
    private RoleManager(Set<Role> roles) {
        this.roles = checkNotNull(roles);
    }

    /**
     * Get the singleton instance of the role manager. If the instance has not been created, or the given set of roles is different
     * than that in the existing instance, then a new role manager instance is created with the given role set
     *
     * @param roleSet   Set of roles
     * @return Role manager
     */
    public static RoleManager getInstance(Set<Role> roleSet) {
        if(instance == null || !roles.equals(roleSet)) {
            instance = new RoleManager(roleSet);
        }
        return instance;
    }

    /**
     * Get the singleton instance of the role manager. If the instance has not been created, then a role manager instance is
     * created with an empty set of roles
     *
     * @return Role manager
     */
    public static RoleManager getInstance() {
        if(instance == null) {
            instance = new RoleManager(new HashSet<>());
        }
        return instance;
    }

    /**
     * Add the specified role
     *
     * @param role  Role
     */
    public void addRole(Role role) {
        roles.add(checkNotNull(role));
    }

    /**
     * Add a given set of roles
     *
     * @param roles Set of roles
     */
    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    /**
     * Remove a given role
     *
     * @param role  Role
     * @throws RoleNotFoundException    Role not found
     */
    public void removeRole(Role role) throws RoleNotFoundException {
        if(!roles.contains(role)) {
            throw new RoleNotFoundException("The specified role does not exist");
        }
        roles.remove(role);
    }

    /**
     * Remove a given set of roles
     *
     * @param roles Set of roles
     * @throws RoleNotFoundException    Role not found
     */
    public void removeRoles(Set<Role> roles) throws RoleNotFoundException {
        for(Role role : roles) {
            removeRole(role);
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
    public Optional<Role> getRoleOptional(Id roleId) {
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
    public Role getRole(Id roleId) throws RoleNotFoundException {
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
     * @param role  Role
     * @param roleName  New role name
     * @throws RoleNotFoundException    Role not found
     */
    public void changeRoleName(Role role, Name roleName) throws RoleNotFoundException {
        removeRole(role);
        Role newRole = new RoleImpl(role.getId(), roleName, role.getDescription(), role.getProjects(), role.getOperations());
        addRole(newRole);
    }

    /**
     * Change the description of the given role to a new one
     *
     * @param role  Role
     * @param roleDescription   New role description
     * @throws RoleNotFoundException    Role not found
     */
    public void changeRoleDescription(Role role, Description roleDescription) throws RoleNotFoundException {
        removeRole(role);
        Role newRole = new RoleImpl(role.getId(), role.getName(), roleDescription, role.getProjects(), role.getOperations());
        addRole(newRole);
    }

    /**
     * Add a project to the working projects of the given role
     *
     * @param role    Role
     * @param projectId Project identifier
     * @throws RoleNotFoundException    Role not found
     */
    public void addProject(Role role, ProjectId projectId) throws RoleNotFoundException {
        Set<ProjectId> projects = new HashSet<>();
        projects.add(projectId);
        addProjects(role, projects);
    }

    /**
     * Add a set of projects to the working projects of the given role
     *
     * @param role    Role
     * @param projectIdSet  Set of project identifiers
     * @throws RoleNotFoundException    Role not found
     */
    public void addProjects(Role role, Set<ProjectId> projectIdSet) throws RoleNotFoundException {
        removeRole(role);

        Set<ProjectId> projects = role.getProjects();
        projects.addAll(projectIdSet);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), projects, role.getOperations());
        addRole(newRole);
    }

    /**
     * Remove a project from the working projects of the given role
     *
     * @param role    Role
     * @param project Project identifier
     * @throws RoleNotFoundException    Role not found
     */
    public void removeProject(Role role, ProjectId project) throws RoleNotFoundException {
        Set<ProjectId> projects = new HashSet<>();
        projects.add(project);
        removeProjects(role, projects);
    }

    /**
     * Remove a project from the working projects of the given role
     *
     * @param role    Role
     * @param projectIds  Set of project identifiers
     * @throws RoleNotFoundException    Role not found
     */
    public void removeProjects(Role role, Set<ProjectId> projectIds) throws RoleNotFoundException {
        removeRole(role);

        Set<ProjectId> projects = role.getProjects();
        projects.removeAll(projectIds);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), projects, role.getOperations());
        addRole(newRole);
    }

    /**
     * Add an operation to the permitted operations of the given role
     *
     * @param role    Role
     * @param operationId   Operation identifier
     * @throws RoleNotFoundException    Role not found
     */
    public void addOperation(Role role, OperationId operationId) throws RoleNotFoundException {
        Set<OperationId> operations = new HashSet<>();
        operations.add(operationId);
        addOperations(role, operations);
    }

    /**
     * Add a set of operations to the permitted operations of the given role
     *
     * @param role    Role
     * @param operationIds    Set of operation identifiers
     * @throws RoleNotFoundException    Role not found
     */
    public void addOperations(Role role, Set<OperationId> operationIds) throws RoleNotFoundException {
        removeRole(role);

        Set<OperationId> operations = role.getOperations();
        operations.addAll(operationIds);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), role.getProjects(), operations);
        addRole(newRole);
    }

    /**
     * Remove an operation from the permitted operations of the given role
     *
     * @param role    Role
     * @param operationId   Operation identifier
     * @throws RoleNotFoundException    Role not found
     */
    public void removeOperation(Role role, OperationId operationId) throws RoleNotFoundException {
        Set<OperationId> operations = new HashSet<>();
        operations.add(operationId);
        removeOperations(role, operations);
    }

    /**
     * Remove a set of operations from the permitted operations of the given role
     *
     * @param role    Role
     * @param operationIds    Set of operation identifiers
     * @throws RoleNotFoundException    Role not found
     */
    public void removeOperations(Role role, Set<OperationId> operationIds) throws RoleNotFoundException {
        removeRole(role);

        Set<OperationId> operations = role.getOperations();
        operations.removeAll(operationIds);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), role.getProjects(), operations);
        addRole(newRole);
    }

    /**
     * Verify whether a given role identifier corresponds to a registered role
     *
     * @param roleId Role identifier
     * @return true if role with the given role identifier exists, false otherwise
     */
    public boolean exists(Id roleId) {
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
        RoleManager that = (RoleManager) o;
        return Objects.equal(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roles);
    }
}
