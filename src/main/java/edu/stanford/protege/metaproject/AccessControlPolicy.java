package edu.stanford.protege.metaproject;

import java.util.Map;
import java.util.Set;

/**
 * A manager for server access control where users are associated with roles that
 * have a defined set of operations the user is allowed to perform
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface AccessControlPolicy {

    /**
     * Add a user-role tuple to the access control policy
     *
     * @param userId    User identifier
     * @param roleId    Role identifier
     */
    void addPolicy(UserId userId, RoleId roleId);

    /**
     * Add a user with multiple roles to the access control policy
     *
     * @param userId    User identifier
     * @param roleIdSet Set of role identifiers
     */
    void addPolicy(UserId userId, Set<RoleId> roleIdSet);

    /**
     * Add multiple users with a single role to the access control policy
     *
     * @param userIdSet Set of user identifiers
     * @param roleId    Role identifier
     */
    void addPolicy(Set<UserId> userIdSet, RoleId roleId);

    /**
     * Remove a user-role tuple from the access control policy
     *
     * @param userId    User identifier
     * @param roleId    Role identifier
     */
    void removePolicy(UserId userId, RoleId roleId);

    /**
     * Check whether the specified operation is allowed for the given user
     *
     * @param operationId Operation identifier
     * @param projectId   Project identifier
     * @param userId  User identifier
     * @return true if user is allowed to carry out the specified operation, false otherwise
     */
    boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId);

    /**
     * Check if a given user has the specified role
     *
     * @param userId  User unique identifier as used to login
     * @param roleId  Role
     * @return true if user has specified role, false otherwise
     */
    boolean hasRole(UserId userId, RoleId roleId);

    /**
     * Get the set of all roles associated with a user
     *
     * @param userId  User unique identifier as used to login
     * @return Set of roles the user has
     */
    Set<RoleId> getRoles(UserId userId);

    /**
     * Get a map of users with their corresponding roles
     *
     * @return Map of users to their roles
     */
    Map<UserId,Set<RoleId>> getUserRoleMappings();

}
