package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.RoleNotFoundException;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleManagerImpl implements RoleManager, Serializable {
    private static final long serialVersionUID = -584068152904153123L;
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
        checkNotNull(roles);
        for(Role r : roles) {
            this.roles.add(checkNotNull(r));
        }
    }

    @Override
    public void remove(Role... role) throws RoleNotFoundException {
        checkNotNull(role);
        for(Role r : role) {
            checkNotNull(r);
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
    public void changeName(RoleId roleId, Name roleName) throws RoleNotFoundException {
        checkNotNull(roleName);
        Role role = getRole(roleId);
        remove(role);
        Role newRole = new RoleImpl(role.getId(), roleName, role.getDescription(), role.getProjects(), role.getOperations());
        add(newRole);
    }

    @Override
    public void changeDescription(RoleId roleId, Description roleDescription) throws RoleNotFoundException {
        checkNotNull(roleDescription);
        Role role = getRole(roleId);
        remove(role);
        Role newRole = new RoleImpl(role.getId(), role.getName(), roleDescription, role.getProjects(), role.getOperations());
        add(newRole);
    }

    @Override
    public void addProject(RoleId roleId, ProjectId... projectIds) throws RoleNotFoundException {
        checkNotNull(projectIds);
        Role role = getRole(roleId);
        remove(role);

        Set<ProjectId> projects = new HashSet<>(role.getProjects());
        Collections.addAll(projects, projectIds);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), projects, role.getOperations());
        add(newRole);
    }

    @Override
    public void removeProject(RoleId roleId, ProjectId... projectIds) throws RoleNotFoundException {
        checkNotNull(projectIds);
        Role role = getRole(roleId);
        remove(role);

        Set<ProjectId> projects = new HashSet<>(role.getProjects());
        for(ProjectId projectId : projectIds) {
            projects.remove(checkNotNull(projectId));
        }

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), projects, role.getOperations());
        add(newRole);
    }

    @Override
    public void addOperation(RoleId roleId, OperationId... operationIds) throws RoleNotFoundException {
        checkNotNull(operationIds);
        Role role = getRole(roleId);
        remove(role);

        Set<OperationId> operations = new HashSet<>(role.getOperations());
        Collections.addAll(operations, operationIds);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), role.getProjects(), operations);
        add(newRole);
    }

    @Override
    public void removeOperation(RoleId roleId, OperationId... operationIds) throws RoleNotFoundException {
        checkNotNull(operationIds);
        Role role = getRole(roleId);
        remove(role);

        Set<OperationId> operations = new HashSet<>(role.getOperations());
        for(OperationId operationId : operationIds) {
            operations.remove(checkNotNull(operationId));
        }

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), role.getProjects(), operations);
        add(newRole);
    }

    @Override
    public boolean exists(AccessControlObjectId roleId) {
        checkNotNull(roleId);
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
    private Optional<Role> getRoleOptional(RoleId roleId) {
        checkNotNull(roleId);
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