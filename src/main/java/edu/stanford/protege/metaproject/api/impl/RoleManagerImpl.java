package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.UnknownRoleIdException;

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
    private static final long serialVersionUID = 7856274800871421233L;
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
    public void remove(Role... roles) {
        checkNotNull(roles);
        for(Role r : roles) {
            checkNotNull(r);
            this.roles.remove(r);
        }
    }

    @Override
    public Role create(String name, String description, Set<OperationId> operations) {
        AccessControlObjectUuidGenerator gen = new AccessControlObjectUuidGenerator();
        return new RoleImpl(gen.getRoleId(), new NameImpl(name), new DescriptionImpl(description), operations);
    }

    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public Role getRole(RoleId roleId) throws UnknownRoleIdException {
        Optional<Role> role = getRoleOptional(roleId);
        if(role.isPresent()) {
            return role.get();
        }
        else {
            throw new UnknownRoleIdException("The specified role identifier does not correspond to an existing role");
        }
    }

    @Override
    public void changeName(RoleId roleId, Name roleName) throws UnknownRoleIdException {
        checkNotNull(roleName);
        Role role = getRole(roleId);
        remove(role);
        Role newRole = new RoleImpl(role.getId(), roleName, role.getDescription(), role.getOperations());
        add(newRole);
    }

    @Override
    public void changeDescription(RoleId roleId, Description roleDescription) throws UnknownRoleIdException {
        checkNotNull(roleDescription);
        Role role = getRole(roleId);
        remove(role);
        Role newRole = new RoleImpl(role.getId(), role.getName(), roleDescription, role.getOperations());
        add(newRole);
    }

    @Override
    public void addOperation(RoleId roleId, OperationId... operationIds) throws UnknownRoleIdException {
        checkNotNull(operationIds);
        Role role = getRole(roleId);
        remove(role);

        Set<OperationId> operations = new HashSet<>(role.getOperations());
        Collections.addAll(operations, operationIds);

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), operations);
        add(newRole);
    }

    @Override
    public void removeOperation(RoleId roleId, OperationId... operationIds) throws UnknownRoleIdException {
        checkNotNull(operationIds);
        Role role = getRole(roleId);
        remove(role);

        Set<OperationId> operations = new HashSet<>(role.getOperations());
        for(OperationId operationId : operationIds) {
            operations.remove(checkNotNull(operationId));
        }

        Role newRole = new RoleImpl(role.getId(), role.getName(), role.getDescription(), operations);
        add(newRole);
    }

    @Override
    public boolean contains(AccessControlObjectId roleId) {
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
