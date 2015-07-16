package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.RoleNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A manager for roles played by users, i.e., the set of operations that are allowed to be
 * performed on a set of projects defined as part of the role
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleManager {
    private Set<Role> roles = new HashSet<>();

    public RoleManager() { }

    /**
     * Add the specified role
     *
     * @param role  Role
     */
    public void addRole(Role role) {
        roles.add(checkNotNull(role));
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
    public Optional<Role> getRole(RoleId roleId) {
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
    private Role getRoleOrFail(RoleId roleId) throws RoleNotFoundException {
        Optional<Role> role = getRole(roleId);
        if(role.isPresent()) {
            return role.get();
        }
        else {
            throw new RoleNotFoundException("The specified role does not exist");
        }
    }

    /**
     * Change the name of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleName  New role name
     * @throws RoleNotFoundException    Role not found
     */
    public void changeRoleName(RoleId roleId, RoleName roleName) throws RoleNotFoundException {
        Role role = getRoleOrFail(roleId);
        removeRole(role);

        Role newRole = new Role(roleId, roleName, role.getDescription(), role.getProjects(), role.getOperations());
        addRole(newRole);
    }

    /**
     * Change the description of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleDescription   New role description
     * @throws RoleNotFoundException    Role not found
     */
    public void changeRoleDescription(RoleId roleId, RoleDescription roleDescription) throws RoleNotFoundException {
        Role role = getRoleOrFail(roleId);
        removeRole(role);

        Role newRole = new Role(roleId, role.getName(), roleDescription, role.getProjects(), role.getOperations());
        addRole(newRole);
    }

    /**
     * Add a project to the working projects of this role
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
     * Add a set of projects to the working projects of this role
     *
     * @param roleId    Role identifier
     * @param projectIdSet  Set of projects
     * @throws RoleNotFoundException    Role not found
     */
    public void addProjects(RoleId roleId, Set<ProjectId> projectIdSet) throws RoleNotFoundException {
        Role role = getRoleOrFail(roleId);
        removeRole(role);

        Set<ProjectId> projects = role.getProjects();
        projects.addAll(projectIdSet);

        Role newRole = new Role(roleId, role.getName(), role.getDescription(), projects, role.getOperations());
        addRole(newRole);
    }

    /**
     * Remove a project from the working projects of this role
     *
     * @param roleId    Role identifier
     * @param projectId Project identifier
     * @throws RoleNotFoundException    Role not found
     */
    public void removeProject(RoleId roleId, ProjectId projectId) throws RoleNotFoundException {
        Set<ProjectId> projects = new HashSet<>();
        projects.add(projectId);
        removeProjects(roleId, projects);
    }

    /**
     * Remove a project from the working projects of this role
     *
     * @param roleId    Role identifier
     * @param projectIdSet  Set of project identifiers
     * @throws RoleNotFoundException    Role not found
     */
    public void removeProjects(RoleId roleId, Set<ProjectId> projectIdSet) throws RoleNotFoundException {
        Role role = getRoleOrFail(roleId);
        removeRole(role);

        Set<ProjectId> projects = role.getProjects();
        projects.removeAll(projectIdSet);

        Role newRole = new Role(roleId, role.getName(), role.getDescription(), projects, role.getOperations());
        addRole(newRole);
    }

    /**
     * Add an operation to the permitted operations of this role
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
     * Add a set of operations to the permitted operations of this role
     *
     * @param roleId    Role identifier
     * @param operationIdSet    Set of operation identifiers
     * @throws RoleNotFoundException    Role not found
     */
    public void addOperations(RoleId roleId, Set<OperationId> operationIdSet) throws RoleNotFoundException {
        Role role = getRoleOrFail(roleId);
        removeRole(role);

        Set<OperationId> operations = role.getOperations();
        operations.addAll(operationIdSet);

        Role newRole = new Role(roleId, role.getName(), role.getDescription(), role.getProjects(), operations);
        addRole(newRole);
    }

    /**
     * Remove an operation from the permitted operations of this role
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
     * Remove a set of operations from the permitted operations of this role
     *
     * @param roleId    Role identifier
     * @param operationIdSet    Set of operation identifiers
     * @throws RoleNotFoundException    Role not found
     */
    public void removeOperations(RoleId roleId, Set<OperationId> operationIdSet) throws RoleNotFoundException {
        Role role = getRoleOrFail(roleId);
        removeRole(role);

        Set<OperationId> operations = role.getOperations();
        operations.removeAll(operationIdSet);

        Role newRole = new Role(roleId, role.getName(), role.getDescription(), role.getProjects(), operations);
        addRole(newRole);
    }
}
