package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;

import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface PolicyManager {

    /**
     * Add a user to the access control policy with the specified role(s)
     *
     * @param userId    User identifier
     * @param roleIds    Role identifier(s)
     */
    void add(UserId userId, RoleId... roleIds);

    /**
     * Assign a role to one or more users in the access control policy
     *
     * @param roleId    Role identifier
     * @param userIds   User identifier(s)
     */
    void add(RoleId roleId, UserId... userIds);

    /**
     * Remove a user-role tuple from the access control policy
     *
     * @param userId    User identifier
     * @param roleId    Role identifier
     * @throws UserNotInPolicyException    User not registered in the access control policy
     */
    void remove(UserId userId, RoleId roleId) throws UserNotInPolicyException;

    /**
     * Check if a given user has the specified role
     *
     * @param userId  User unique identifier as used to login
     * @param roleId  Role identifier
     * @return true if user has specified role, false otherwise
     * @throws UserNotInPolicyException    User not registered in the access control policy
     */
    boolean hasRole(UserId userId, RoleId roleId) throws UserNotInPolicyException;

    /**
     * Get the set of all roles associated with a user
     *
     * @param userId  User identifier
     * @return Set of role identifiers the user has
     * @throws UserNotInPolicyException    User not registered in the access control policy
     */
    Set<RoleId> getRoles(UserId userId) throws UserNotInPolicyException;

    /**
     * Get a map of users with their corresponding roles
     *
     * @return Map of users to their roles
     */
    Map<UserId,Set<RoleId>> getUserRoleMappings();

    /**
     * Check whether the policy manager contains any role assignments for
     * the user with the given identifier
     *
     * @param id    User identifier
     * @return true if user has role assignments
     */
    boolean hasRoleAssignments(UserId id);

}
