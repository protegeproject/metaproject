package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.PolicyException;
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
     * Add a user to the access control policy with the specified role(s)
     *
     * @param userId    User identifier
     * @param roleIds    Role identifier(s)
     * @throws PolicyException    Policy exception
     */
    void addPolicy(UserId userId, RoleId... roleIds) throws PolicyException;

    /**
     * Assign a role to one or more users in the access control policy
     *
     * @param roleId    Role identifier
     * @param userIds   User identifier(s)
     * @throws PolicyException    Policy exception
     */
    void addPolicy(RoleId roleId, UserId... userIds) throws PolicyException;

    /**
     * Remove a user-role tuple from the access control policy
     *
     * @param userId    User identifier
     * @param roleId    Role identifier
     * @throws PolicyException    Policy exception
     */
    void removePolicy(UserId userId, RoleId roleId) throws PolicyException;

    /**
     * Check whether the specified operation is allowed for the given user
     *
     * @param operationId Operation identifier
     * @param projectId   Project identifier
     * @param userId  User identifier
     * @return true if user is allowed to carry out the specified operation, false otherwise
     * @throws PolicyException    Policy exception
     */
    boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId) throws PolicyException;

    /**
     * Check if a given user has the specified role
     *
     * @param userId  User unique identifier as used to login
     * @param roleId  Role identifier
     * @return true if user has specified role, false otherwise
     * @throws PolicyException    Policy exception
     */
    boolean hasRole(UserId userId, RoleId roleId) throws PolicyException;

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
     * Get the policy role manager
     *
     * @return Role manager
     */
    RoleManager getRoleManager();

    /**
     * Get the policy operation manager
     *
     * @return Operation manager
     */
    OperationManager getOperationManager();

    /**
     * Get the policy user manager
     *
     * @return User manager
     */
    UserManager getUserManager();

    /**
     * Get the policy project manager
     *
     * @return Project manager
     */
    ProjectManager getProjectManager();

}
