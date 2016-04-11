package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.IdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;
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
public class RoleRegistryImpl implements RoleRegistry, Serializable {
    private static final long serialVersionUID = 669566289686711682L;
    private Set<Role> roles = new HashSet<>();

    /**
     * Constructor
     *
     * @param roles Set of roles
     */
    public RoleRegistryImpl(Set<Role> roles) {
        this.roles = checkNotNull(roles);
    }

    @Override
    public void add(Role role) throws IdAlreadyInUseException {
        checkNotNull(role);
        if (contains(role.getId())) {
            throw new IdAlreadyInUseException("The specified role identifier is already used in another role");
        }
        roles.add(checkNotNull(role));
    }

    @Override
    public void remove(Role role) {
        checkNotNull(role);
        roles.remove(role);
    }

    @Override
    public Set<Role> getEntries() {
        return roles;
    }

    @Override
    public <E extends MetaprojectObjectId> Role get(E id) throws UnknownMetaprojectObjectIdException {
        if(!(id instanceof RoleId)) {
            throw new IllegalArgumentException("Programmer error: Expected a role identifier");
        }
        Optional<Role> role = getRoleOptional((RoleId)id);
        if(role.isPresent()) {
            return role.get();
        }
        else {
            throw new UnknownRoleIdException("The specified role identifier does not correspond to an existing role");
        }
    }

    @Override
    public void setName(RoleId roleId, Name roleName) throws UnknownMetaprojectObjectIdException {
        checkNotNull(roleName);
        Role role = get(roleId);
        remove(role);
        Role newRole = createRole(role.getId(), roleName, role.getDescription(), role.getOperations());
        update(newRole);
    }

    @Override
    public void setDescription(RoleId roleId, Description roleDescription) throws UnknownMetaprojectObjectIdException {
        checkNotNull(roleDescription);
        Role role = get(roleId);
        remove(role);
        Role newRole = createRole(role.getId(), role.getName(), roleDescription, role.getOperations());
        update(newRole);
    }

    @Override
    public void addOperation(RoleId roleId, OperationId... operationIds) throws UnknownMetaprojectObjectIdException {
        checkNotNull(operationIds);
        Role role = get(roleId);
        remove(role);

        Set<OperationId> operations = new HashSet<>(role.getOperations());
        Collections.addAll(operations, operationIds);

        Role newRole = createRole(role.getId(), role.getName(), role.getDescription(), operations);
        update(newRole);
    }

    @Override
    public void removeOperation(RoleId roleId, OperationId... operationIds) throws UnknownMetaprojectObjectIdException {
        checkNotNull(operationIds);
        Role role = get(roleId);
        remove(role);

        Set<OperationId> operations = new HashSet<>(role.getOperations());
        for(OperationId operationId : operationIds) {
            operations.remove(checkNotNull(operationId));
        }

        Role newRole = createRole(role.getId(), role.getName(), role.getDescription(), operations);
        update(newRole);
    }

    @Override
    public boolean contains(Role obj) {
        checkNotNull(obj);
        return roles.contains(obj);
    }

    @Override
    public <E extends MetaprojectObjectId> boolean contains(E id) {
        checkNotNull(id);
        for(Role role : roles) {
            if(role.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Update the role registry with the given role
     *
     * @param role  Role
     */
    private void update(Role role) {
        roles.add(role);
    }

    /**
     * Create an instance of a role
     */
    private Role createRole(RoleId id, Name name, Description description, Set<OperationId> operations) {
        return Manager.getFactory().getRole(id, name, description, operations);
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
        RoleRegistryImpl that = (RoleRegistryImpl) o;
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
