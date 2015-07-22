package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.RoleNotFoundException;
import edu.stanford.protege.metaproject.api.exception.UserNotFoundException;

import java.util.*;

/**
 * A manager for server access control where users are associated with roles that
 * have a defined set of operations the user is allowed to perform
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class AccessControlPolicy {
    private static AccessControlPolicy instance = null;
    private Map<UserId, Set<RoleId>> userRoleMap = new HashMap<>();
    private RoleManager roleManager = RoleManager.getInstance();

    private AccessControlPolicy() { }

    /**
     * Get the instance of the access control policy
     *
     * @return AccessControlPolicy instance
     */
    public static AccessControlPolicy getInstance() {
        if(instance == null) {
            instance = new AccessControlPolicy();
        }
        return instance;
    }

    /**
     * Add a user-role tuple to the access control policy
     *
     * @param userId    User identifier
     * @param roleId    Role identifier
     */
    public void addPolicy(UserId userId, RoleId roleId) {
        if(userRoleMap.containsKey(userId)) {
            Set<RoleId> roles = userRoleMap.get(userId);
            roles.add(roleId);
            userRoleMap.put(userId, roles);
        }
        else {
            Set<RoleId> roles = new HashSet<>();
            roles.add(roleId);
            userRoleMap.put(userId, roles);
        }
    }

    /**
     * Add a user with multiple roles to the access control policy
     *
     * @param userId    User identifier
     * @param roleIdSet Set of role identifiers
     */
    public void addPolicy(UserId userId, Set<RoleId> roleIdSet) {
        if(userRoleMap.containsKey(userId)) {
            Set<RoleId> roles = userRoleMap.get(userId);
            roles.addAll(roleIdSet);
            userRoleMap.put(userId, roles);
        }
        else {
            userRoleMap.put(userId, roleIdSet);
        }
    }

    /**
     * Add multiple users with a single role to the access control policy
     *
     * @param userIdSet Set of user identifiers
     * @param roleId    Role identifier
     */
    public void addPolicy(Set<UserId> userIdSet, RoleId roleId) {
        for(UserId userId : userIdSet) {
            addPolicy(userId, roleId);
        }
    }

    /**
     * Remove a user-role tuple from the access control policy
     *
     * @param userId    User identifier
     * @param roleId    Role identifier
     * @throws UserNotFoundException    User not found
     * @throws RoleNotFoundException    Role not found
     */
    public void removePolicy(UserId userId, RoleId roleId) throws UserNotFoundException, RoleNotFoundException {
        if(!userRoleMap.containsKey(userId)) {
            throw new UserNotFoundException("The specified user does not exist");
        }
        else {
            Set<RoleId> roles = userRoleMap.get(userId);
            if(!roles.contains(roleId)) {
                throw new RoleNotFoundException("The specified role does not exist");
            }
            roles.remove(roleId);
            userRoleMap.put(userId, roles);
        }
    }

    /**
     * Check whether the specified operation is allowed for the given user
     *
     * @param operationId Operation identifier
     * @param projectId   Project identifier
     * @param userId  User identifier
     * @return true if user is allowed to carry out the specified operation, false otherwise
     * @throws UserNotFoundException    User not found
     */
    public boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId) throws UserNotFoundException {
        if(!userRoleMap.containsKey(userId)) {
            throw new UserNotFoundException("The specified user does not exist");
        }
        Set<RoleId> roles = userRoleMap.get(userId);
        for(RoleId roleId : roles) {
            Optional<Role> r = roleManager.getRole(roleId);
            if(r.isPresent()) {
                Role role = r.get();
                if(role.contains(operationId, projectId)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if a given user has the specified role
     *
     * @param userId  User unique identifier as used to login
     * @param roleId  Role
     * @return true if user has specified role, false otherwise
     * @throws UserNotFoundException    User not found
     */
    public boolean hasRole(UserId userId, RoleId roleId) throws UserNotFoundException {
        if(!userRoleMap.containsKey(userId)) {
            throw new UserNotFoundException("The specified user does not exist");
        }
        Set<RoleId> roles = userRoleMap.get(userId);
        return roles.contains(roleId);
    }

    /**
     * Get the set of all roles associated with a user
     *
     * @param userId  User unique identifier as used to login
     * @return Set of roles the user has
     * @throws UserNotFoundException    User not found
     */
    public Set<RoleId> getRoles(UserId userId) throws UserNotFoundException {
        if(!userRoleMap.containsKey(userId)) {
            throw new UserNotFoundException("The specified user does not exist");
        }
        return userRoleMap.get(userId);
    }

    /**
     * Get a map of users with their corresponding roles
     *
     * @return Map of users to their roles
     */
    public Map<UserId,Set<RoleId>> getUserRoleMappings() {
        return userRoleMap;
    }
}
