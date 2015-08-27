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

    @Override
    public void add(Role... roles) {
        for(Role r : roles) {
            this.roles.add(checkNotNull(r));
        }
    }

    @Override
    public void remove(Role... role) throws RoleNotFoundException {
        for(Role r : role) {
            if (!roles.contains(r)) {
                throw new RoleNotFoundException("The specified role does not exist");
            }
            roles.remove(r);
        }
    }

    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public Role getRole(RoleId roleId) throws RoleNotFoundException {
        Optional<Role> role = getRoleOptional(roleId);
        if(role.isPresent()) {
            return role.get();
        }
        else {
            throw new RoleNotFoundException("The specified role identifier does not correspond to an existing role");
        }
    }

    @Override
    public void changeRoleName(RoleId roleId, Name roleName) throws RoleNotFoundException {
        Role role = getRole(roleId);
        remove(role);
        Role newRole = new RoleImpl(role.getId(), roleName, role.getDescription(), role.getProjects(), role.getOperations());
        add(newRole);
    }

    @Override
    public void changeRoleDescription(RoleId roleId, Description roleDescription) throws RoleNotFoundException {
        Role role = getRole(roleId);
        remove(role);
        Role newRole = new RoleImpl(role.getId(), role.getName(), roleDescription, role.getProjects(), role.getOperations());
        add(newRole);
    }

    @Override
    public void addProject(RoleId roleId, ProjectId projectId) throws RoleNotFoundException {
        Set<ProjectId> projects = new HashSet<>();
        projects.add(projectId);
        addProjects(roleId, projects);
    }

    @Override
    public void addProjects(RoleId roleId, Set<ProjectId> projectIdSet) throws RoleNotFoundException {
        Role role = getRole(roleId);
        remove(role);

        Set<ProjectId> projects = role.getProjects();
        projects.addAll(projectIdSet);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), projects, role.getOperations());
        add(newRole);
    }

    @Override
    public void removeProject(RoleId roleId, ProjectId project) throws RoleNotFoundException {
        Set<ProjectId> projects = new HashSet<>();
        projects.add(project);
        removeProjects(roleId, projects);
    }

    @Override
    public void removeProjects(RoleId roleId, Set<ProjectId> projectIds) throws RoleNotFoundException {
        Role role = getRole(roleId);
        remove(role);

        Set<ProjectId> projects = role.getProjects();
        projects.removeAll(projectIds);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), projects, role.getOperations());
        add(newRole);
    }

    @Override
    public void addOperation(RoleId roleId, OperationId operationId) throws RoleNotFoundException {
        Set<OperationId> operations = new HashSet<>();
        operations.add(operationId);
        addOperations(roleId, operations);
    }

    @Override
    public void addOperations(RoleId roleId, Set<OperationId> operationIds) throws RoleNotFoundException {
        Role role = getRole(roleId);
        remove(role);

        Set<OperationId> operations = role.getOperations();
        operations.addAll(operationIds);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), role.getProjects(), operations);
        add(newRole);
    }

    @Override
    public void removeOperation(RoleId roleId, OperationId operationId) throws RoleNotFoundException {
        Set<OperationId> operations = new HashSet<>();
        operations.add(operationId);
        removeOperations(roleId, operations);
    }

    @Override
    public void removeOperations(RoleId roleId, Set<OperationId> operationIds) throws RoleNotFoundException {
        Role role = getRole(roleId);
        remove(role);

        Set<OperationId> operations = role.getOperations();
        operations.removeAll(operationIds);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), role.getProjects(), operations);
        add(newRole);
    }

    @Override
    public boolean exists(AccessControlObjectId roleId) {
        for(Role role : roles) {
            if(role.getId().equals(roleId)) {
                return true;
            }
        }
        return false;
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
