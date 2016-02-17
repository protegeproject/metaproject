package edu.stanford.protege.metaproject.api;

import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface PolicyManager extends Manager {

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
    Set<RoleId> getRoles(UserId userId, ProjectId projectId) throws UserNotInPolicyException, ProjectNotInPolicyException;

    /**
     * Get the set of project identifiers that the given user is assigned to
     *
     * @param userId    User identigier
     * @return Set of project identifiers the user works on
     * @throws UserNotInPolicyException User not registered in the access control policy
     */
    Set<ProjectId> getProjects(UserId userId) throws UserNotInPolicyException;

    /**
     * Get a map of user identifiers to their project-roles assignments
     *
     * @return Map of users to their project-role
     */
    Map<UserId,Map<ProjectId,Set<RoleId>>> getUserRoleMappings();

    /**
     * Check whether the policy manager contains any role assignments for
     * the user with the given identifier in the specified project
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @return true if user has role assignments in the specified project
     */
    boolean hasRoleAssignments(UserId userId, ProjectId projectId);

}
