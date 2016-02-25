package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.ProjectNotInPolicyException;
import edu.stanford.protege.metaproject.api.exception.UnknownAccessControlObjectIdException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;

import java.util.Map;
import java.util.Set;

/**
 * A representation of an access control policy where users are associated with roles. Roles have
 * a defined set of operations the user is allowed to perform within a set of projects
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Policy extends Registry {

    /**
     * Add a user to the access control policy with the specified role(s) in the given project
     *
     * @param userId    User identifier
     * @param projectId    Project identifier
     * @param roleIds    Role identifier(s)
     */
    void add(UserId userId, ProjectId projectId, RoleId... roleIds);

    /**
     * Assign a role to one or more users in the access control policy of a specific project
     *
     * @param roleId    Role identifier
     * @param projectId    Project identifier
     * @param userIds   User identifier(s)
     */
    void add(RoleId roleId, ProjectId projectId, UserId... userIds);

    /**
     * Remove a role from the specified user and project in the access control policy
     *
     * @param userId    User identifier
     * @param projectId    Project identifier
     * @param roleId    Role identifier
     */
    void remove(UserId userId, ProjectId projectId, RoleId roleId);

    /**
     * Check if a given user has the specified role
     *
     * @param userId  User unique identifier as used to login
     * @param roleId  Role identifier
     * @param projectId  Project identifier
     * @return true if user has specified role, false otherwise
     */
    boolean hasRole(UserId userId, ProjectId projectId, RoleId roleId);

    /**
     * Get the set of role identifiers that the given user has in the specified project
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @return Set of role identifiers the user has
     * @throws UserNotInPolicyException    User not registered in the access control policy
     * @throws ProjectNotInPolicyException  Project not registered for this user in the access control policy
     */
    Set<RoleId> getRoleIds(UserId userId, ProjectId projectId) throws UserNotInPolicyException, ProjectNotInPolicyException;

    /**
     * Get the set of project identifiers that the given user is assigned to
     *
     * @param userId    User identifier
     * @return Set of project identifiers the user works on
     * @throws UserNotInPolicyException User not registered in the access control policy
     */
    Set<ProjectId> getProjectIds(UserId userId) throws UserNotInPolicyException;

    /**
     * Get a map of user identifiers to their project-roles assignments
     *
     * @return Map of users to their project-role mappings
     */
    Map<UserId,Map<ProjectId,Set<RoleId>>> getPolicyMappings();

    /**
     * Get a map of project identifiers to the roles that a specified user can play
     *
     * @param userId    User identifier
     * @return Map of projects to roles
     */
    Map<ProjectId, Set<RoleId>> getUserRoleMap(UserId userId);

    /**
     * Check whether the policy manager contains any role assignments for
     * the user with the given identifier in the specified project
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @return true if user has role assignments in the specified project
     */
    boolean hasRoleAssignments(UserId userId, ProjectId projectId);

    /**
     * Check whether the specified operation is allowed for the given user
     *
     * @param operationId Operation identifier
     * @param projectId   Project identifier
     * @param userId  User identifier
     * @return true if user is allowed to carry out the specified operation, false otherwise
     * @throws UnknownAccessControlObjectIdException Unknown access control object exception
     * @throws UserNotInPolicyException UserId not registered in the access control policy
     * @throws ProjectNotInPolicyException  Project not registered in the access control policy
     */
    boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId)
            throws UnknownAccessControlObjectIdException, UserNotInPolicyException, ProjectNotInPolicyException;

    /**
     * Get the set of operations allowed for a given user in a specific project
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @return Set of operations
     * @throws UserNotInPolicyException UserId not registered in the access control policy
     * @throws ProjectNotInPolicyException  Project not registered in the access control policy
     */
    Set<Operation> getOperationsInProject(UserId userId, ProjectId projectId) throws UserNotInPolicyException, ProjectNotInPolicyException;

    /**
     * Get the set of projects that a user plays some role in
     *
     * @param userId    User identifier
     * @return Set of projects
     * @throws UserNotInPolicyException UserId not registered in the access control policy
     */
    Set<Project> getProjects(UserId userId) throws UserNotInPolicyException;

    /**
     * Get the set of roles that a given user has assigned
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @return Set of roles
     * @throws UserNotInPolicyException UserId not registered in the access control policy
     * @throws ProjectNotInPolicyException  Project not registered in the access control policy
     */
    Set<Role> getRoles(UserId userId, ProjectId projectId) throws UserNotInPolicyException, ProjectNotInPolicyException;

    /**
     * Get the set of users that have some role in the specified project
     *
     * @param projectId    Project identifier
     * @return Set of users
     * @throws UnknownAccessControlObjectIdException Unknown access control object exception
     */
    Set<User> getUsers(ProjectId projectId) throws UnknownAccessControlObjectIdException;

}
