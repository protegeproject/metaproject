package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.RoleNotFoundException;

import java.util.Set;

/**
 * A manager for roles played by users, i.e., the set of operations that are allowed to be
 * performed on a set of projects defined as part of the role.
 *
 * The role manager follows the singleton pattern; only one instance of this manager may exist.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface RoleManager extends Manager {

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
     * Create a new role
     *
     * @param name  Role name
     * @param description   Role description
     * @param projects  Set of projects
     * @param operations    Set of operations
     * @return New role instance
     */
    Role create(String name, String description, Set<ProjectId> projects, Set<OperationId> operations);

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
     * @throws RoleNotFoundException    Role not found
     */
    Role getRole(RoleId roleId) throws RoleNotFoundException;

    /**
     * Change the name of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleName  New role name
     * @throws RoleNotFoundException    Role not found
     */
    void changeName(RoleId roleId, Name roleName) throws RoleNotFoundException;

    /**
     * Change the description of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleDescription   New role description
     * @throws RoleNotFoundException    Role not found
     */
    void changeDescription(RoleId roleId, Description roleDescription) throws RoleNotFoundException;

    /**
     * Add one or more projects to the working projects of the given role
     *
     * @param roleId    Role identifier
     * @param projectIds Project identifier(s)
     * @throws RoleNotFoundException    Role not found
     */
    void addProject(RoleId roleId, ProjectId... projectIds) throws RoleNotFoundException;

    /**
     * Remove one or more projects from the working projects of the given role
     *
     * @param roleId    Role identifier
     * @param projectIds Project identifier(s)
     * @throws RoleNotFoundException    Role not found
     */
    void removeProject(RoleId roleId, ProjectId... projectIds) throws RoleNotFoundException;

    /**
     * Add one or more operations to the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds   Operation identifier(s)
     * @throws RoleNotFoundException    Role not found
     */
    void addOperation(RoleId roleId, OperationId... operationIds) throws RoleNotFoundException;

    /**
     * Remove one or more operations from the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds   Operation identifier(s)
     * @throws RoleNotFoundException    Role not found
     */
    void removeOperation(RoleId roleId, OperationId... operationIds) throws RoleNotFoundException;

}
