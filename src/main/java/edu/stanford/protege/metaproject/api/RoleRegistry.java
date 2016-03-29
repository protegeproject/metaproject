package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UnknownRoleIdException;

import java.util.Set;

/**
 * A manager for roles, i.e., containers of operations that are allowed to be performed.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface RoleRegistry {

    /**
     * Add the specified role(s)
     *
     * @param roles  One or more roles
     */
    void add(Role... roles);

    /**
     * Remove the given role(s)
     *
     * @param roles  One or more roles
     */
    void remove(Role... roles);

    /**
     * Get the set of all roles
     *
     * @return Set of existing roles
     */
    Set<Role> getRoles();

    /**
     * A convenience method to fetch a role or throw an exception
     *
     * @param roleId    Role identifier
     * @return Role instance
     * @throws UnknownRoleIdException    Role identifier is not recognized
     */
    Role getRole(RoleId roleId) throws UnknownRoleIdException;

    /**
     * Change the name of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleName  New role name
     * @throws UnknownRoleIdException    Role identifier is not recognized
     */
    void setName(RoleId roleId, Name roleName) throws UnknownRoleIdException;

    /**
     * Change the description of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleDescription   New role description
     * @throws UnknownRoleIdException    Role identifier is not recognized
     */
    void setDescription(RoleId roleId, Description roleDescription) throws UnknownRoleIdException;

    /**
     * Add one or more operations to the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds   Operation identifier(s)
     * @throws UnknownRoleIdException    Role identifier is not recognized
     */
    void addOperation(RoleId roleId, OperationId... operationIds) throws UnknownRoleIdException;

    /**
     * Remove one or more operations from the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds   Operation identifier(s)
     * @throws UnknownRoleIdException    Role identifier is not recognized
     */
    void removeOperation(RoleId roleId, OperationId... operationIds) throws UnknownRoleIdException;

    /**
     * Check whether the role registry contains a role with the given identifier
     *
     * @param roleId    Role identifier
     * @return true if there is a role with the specified identifier, false otherwise
     */
    boolean contains(RoleId roleId);

}
