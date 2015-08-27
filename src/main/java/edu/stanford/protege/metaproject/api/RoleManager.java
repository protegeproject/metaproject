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
     * @param role  One or more roles
     * @throws RoleNotFoundException    Role not found
     */
    void remove(Role... role) throws RoleNotFoundException;

    /**
     * Get the set of all roles
     *
     * @return Set of existing roles
     */
    Set<Role> getRoles();

    /**
     * A convenience method to fetch a role or die trying (with an exception)
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
    void changeRoleName(RoleId roleId, Name roleName) throws RoleNotFoundException;

    /**
     * Change the description of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleDescription   New role description
     * @throws RoleNotFoundException    Role not found
     */
    void changeRoleDescription(RoleId roleId, Description roleDescription) throws RoleNotFoundException;

    /**
     * Add a project to the working projects of the given role
     *
     * @param roleId    Role identifier
     * @param projectId Project identifier
     * @throws RoleNotFoundException    Role not found
     */
    void addProject(RoleId roleId, ProjectId projectId) throws RoleNotFoundException;

    /**
     * Add a set of projects to the working projects of the given role
     *
     * @param roleId    Role identifier
     * @param projectIdSet  Set of project identifiers
     * @throws RoleNotFoundException    Role not found
     */
    void addProjects(RoleId roleId, Set<ProjectId> projectIdSet) throws RoleNotFoundException;

    /**
     * Remove a project from the working projects of the given role
     *
     * @param roleId    Role identifier
     * @param project Project identifier
     * @throws RoleNotFoundException    Role not found
     */
    void removeProject(RoleId roleId, ProjectId project) throws RoleNotFoundException;

    /**
     * Remove a project from the working projects of the given role
     *
     * @param roleId    Role identifier
     * @param projectIds  Set of project identifiers
     * @throws RoleNotFoundException    Role not found
     */
    void removeProjects(RoleId roleId, Set<ProjectId> projectIds) throws RoleNotFoundException;

    /**
     * Add an operation to the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationId   Operation identifier
     * @throws RoleNotFoundException    Role not found
     */
    void addOperation(RoleId roleId, OperationId operationId) throws RoleNotFoundException;

    /**
     * Add a set of operations to the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds    Set of operation identifiers
     * @throws RoleNotFoundException    Role not found
     */
    void addOperations(RoleId roleId, Set<OperationId> operationIds) throws RoleNotFoundException;

    /**
     * Remove an operation from the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationId   Operation identifier
     * @throws RoleNotFoundException    Role not found
     */
    void removeOperation(RoleId roleId, OperationId operationId) throws RoleNotFoundException;

    /**
     * Remove a set of operations from the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds    Set of operation identifiers
     * @throws RoleNotFoundException    Role not found
     */
    void removeOperations(RoleId roleId, Set<OperationId> operationIds) throws RoleNotFoundException;

}
