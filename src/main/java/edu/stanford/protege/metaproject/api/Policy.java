package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.ProjectNotInPolicyException;
import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;
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
public interface Policy {

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
     * Remove all role assignments on the specified project by the given user
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     */
    void remove(UserId userId, ProjectId projectId);

    /**
     * Remove all policy and registry entries involving the metaproject object with the specified identifier
     *
     * @param obj   Metaproject object identifier
     * @param <E>   Type of identifier
     */
    <E extends MetaprojectObjectId> void remove(E obj);

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
     * Check whether the policy manager contains any role assignments for
     * the user with the given identifier in the specified project
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @return true if user has role assignments in the specified project
     */
    boolean hasRole(UserId userId, ProjectId projectId);

    /**
     * Check whether the policy contains role assignments for the user with the given identifier in some project
     *
     * @param userId    User identifier
     * @return true if policy has role assignments for the user with the specified identifier, false otherwise
     */
    boolean hasRole(UserId userId);

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
     * Get the set of role identifiers that a given user has assigned within the specified project
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @param globalPermissions Whether global permissions should be taken into account
     * @return Set of role identifiers
     * @throws UserNotInPolicyException UserId not registered in the access control policy
     * @throws ProjectNotInPolicyException  Project not registered in the access control policy
     */
    Set<RoleId> getRoles(UserId userId, ProjectId projectId, GlobalPermissions globalPermissions) throws UserNotInPolicyException, ProjectNotInPolicyException;

    /**
     * Get the set of role identifiers that a given user has assigned
     *
     * @param userId    User identifier
     * @param globalPermissions Whether global permissions should be taken into account
     * @return Set of role identifiers
     * @throws UserNotInPolicyException UserId not registered in the access control policy
     */
    Set<RoleId> getRoles(UserId userId, GlobalPermissions globalPermissions) throws UserNotInPolicyException;

    /**
     * Get the set of user identifiers that have some role in the specified project
     *
     * @param projectId    Project identifier
     * @return Set of user identifiers
     * @throws UnknownMetaprojectObjectIdException Unknown access control object exception
     */
    Set<UserId> getUsers(ProjectId projectId) throws UnknownMetaprojectObjectIdException;

    /**
     * Get the set of project identifiers that the given user is assigned to
     *
     * @param userId    User identifier
     * @return Set of project identifiers the user works on
     * @throws UserNotInPolicyException User not registered in the access control policy
     */
    Set<ProjectId> getProjects(UserId userId) throws UserNotInPolicyException;

}
