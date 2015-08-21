package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.PolicyException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A manager for server access control where users are associated with roles that
 * have a defined set of operations the user is allowed to perform
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AccessControlPolicy implements Policy, Serializable {
    private static final long serialVersionUID = 6660583481655821761L;
    private Map<UserId,Set<RoleId>> userRoleMap = new HashMap<>();

    // access control objects managers
    private RoleManager roleManager;
    private OperationManager operationManager;
    private UserManager userManager;
    private ProjectManager projectManager;

    /**
     * Package-private constructor; use builder
     *
     * @param userRoleMap   Map of users to their roles
     */
    AccessControlPolicy(Map<UserId,Set<RoleId>> userRoleMap, Set<Role> roles, Set<Operation> operations, Set<User> users, Set<Project> projects) {
        this.userRoleMap = checkNotNull(userRoleMap);
        this.roleManager = new RoleManager(checkNotNull(roles));
        this.operationManager = new OperationManager(checkNotNull(operations));
        this.userManager = new UserManager(checkNotNull(users));
        this.projectManager = new ProjectManager(checkNotNull(projects));
    }

    /**
     * Add a user-role tuple to the access control policy
     *
     * @param userId    User identifier
     * @param roleId    Role identifier
     * @throws PolicyException    Policy exception
     */
    public void addPolicy(UserId userId, RoleId roleId) throws PolicyException {
        checkExistence(userManager, userId);
        checkExistence(roleManager, roleId);

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
     * @param roles Set of role identifiers
     * @throws PolicyException    Policy exception
     */
    public void addPolicy(UserId userId, Set<RoleId> roles) throws PolicyException {
        checkExistence(userManager, userId);
        checkExistence(roleManager, roles.toArray(new RoleId[roles.size()]));

        if(userRoleMap.containsKey(userId)) {
            Set<RoleId> roleSet = userRoleMap.get(userId);
            roleSet.addAll(roles);
            userRoleMap.put(userId, roleSet);
        }
        else {
            userRoleMap.put(userId, roles);
        }
    }

    /**
     * Add multiple users with a single role to the access control policy
     *
     * @param users Set of user identifiers
     * @param role    Role identifier
     * @throws PolicyException    Policy exception
     */
    public void addPolicy(Set<UserId> users, RoleId role) throws PolicyException {
        for(UserId user : users) {
            addPolicy(user, role);
        }
    }

    /**
     * Remove a user-role tuple from the access control policy
     *
     * @param userId    User identifier
     * @param roleId    Role identifier
     * @throws PolicyException    Policy exception
     */
    public void removePolicy(UserId userId, RoleId roleId) throws PolicyException {
        checkExistence(userManager, userId);
        checkExistence(roleManager, roleId);

        Set<RoleId> roles = userRoleMap.get(userId);
        roles.remove(roleId);
        userRoleMap.put(userId, roles);
    }

    /**
     * Check whether the specified operation is allowed for the given user
     *
     * @param operationId Operation identifier
     * @param projectId   Project identifier
     * @param userId  User identifier
     * @return true if user is allowed to carry out the specified operation, false otherwise
     * @throws PolicyException    Policy exception
     */
    public boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId) throws PolicyException {
        checkExistence(operationManager, operationId);
        checkExistence(projectManager, projectId);
        checkUserIsInPolicy(userId);

        Set<RoleId> roles = userRoleMap.get(userId);
        for (RoleId role : roles) {
            Role r = roleManager.getRoleOptional(role).get();
            if (r.getProjects().contains(projectId) && r.getOperations().contains(operationId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a given user has the specified role
     *
     * @param userId  User unique identifier as used to login
     * @param roleId  Role identifier
     * @return true if user has specified role, false otherwise
     * @throws PolicyException    Policy exception
     */
    public boolean hasRole(UserId userId, RoleId roleId) throws PolicyException {
        checkUserIsInPolicy(userId);
        checkExistence(roleManager, roleId);

        Set<RoleId> roles = userRoleMap.get(userId);
        return roles.contains(roleId);
    }

    /**
     * Get the set of all roles associated with a user
     *
     * @param userId  User identifier
     * @return Set of role identifiers the user has
     * @throws UserNotInPolicyException    User not registered in the access control policy
     */
    public Set<RoleId> getRoles(UserId userId) throws UserNotInPolicyException {
        checkUserIsInPolicy(userId);

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

    /**
     * Get the policy role manager
     *
     * @return Role manager
     */
    public RoleManager getRoleManager() {
        return roleManager;
    }

    /**
     * Get the policy operation manager
     *
     * @return Operation manager
     */
    public OperationManager getOperationManager() {
        return operationManager;
    }

    /**
     * Get the policy user manager
     *
     * @return User manager
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * Get the policy project manager
     *
     * @return Project manager
     */
    public ProjectManager getProjectManager() {
        return projectManager;
    }

    /**
     * Verify whether given user identifier(s) are registered in the access control policy, i.e., in the user-role map
     *
     * @param users   One or more user identifiers
     * @throws UserNotInPolicyException UserId not registered in the access control policy
     */
    private void checkUserIsInPolicy(UserId... users) throws UserNotInPolicyException {
        for(UserId user : users) {
            if (!userRoleMap.containsKey(user)) {
                throw new UserNotInPolicyException("The specified user is not registered in the access control policy");
            }
        }
    }

    /**
     * Verify whether access control object(s) are registered with the given manager
     *
     * @param manager   Manager for given access control object
     * @param objects   One or more access control object identifiers
     * @throws PolicyException  Policy exception
     */
    private void checkExistence(Manager manager, AccessControlObjectId... objects) throws PolicyException {
        for(AccessControlObjectId obj : objects) {
            if(!manager.exists(obj)) {
                throw new PolicyException("The specified access control object does not correspond to a known one. " +
                        "It may not have been registered with the appropriate manager");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessControlPolicy policy = (AccessControlPolicy) o;
        return Objects.equal(userRoleMap, policy.userRoleMap) &&
                Objects.equal(roleManager, policy.roleManager) &&
                Objects.equal(operationManager, policy.operationManager) &&
                Objects.equal(userManager, policy.userManager) &&
                Objects.equal(projectManager, policy.projectManager);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userRoleMap, roleManager, operationManager, userManager, projectManager);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userRoleMap", userRoleMap)
                .add("roleManager", roleManager)
                .add("operationManager", operationManager)
                .add("userManager", userManager)
                .add("projectManager", projectManager)
                .toString();
    }

    /**
     * Builder for access control policies
     */
    public static class Builder {
        private Map<UserId, Set<RoleId>> userRoleMap = new HashMap<>();
        private Set<Role> roles = new HashSet<>();
        private Set<Operation> operations = new HashSet<>();
        private Set<User> users = new HashSet<>();
        private Set<Project> projects = new HashSet<>();

        public Builder setUserRoleMap(Map<UserId, Set<RoleId>> userRoleMap) {
            this.userRoleMap = userRoleMap;
            return this;
        }

        public Builder setRoles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public Builder setOperations(Set<Operation> operations) {
            this.operations = operations;
            return this;
        }

        public Builder setUsers(Set<User> users) {
            this.users = users;
            return this;
        }

        public Builder setProjects(Set<Project> projects) {
            this.projects = projects;
            return this;
        }

        public AccessControlPolicy createAccessControlPolicy() {
            return new AccessControlPolicy(userRoleMap, roles, operations, users, projects);
        }
    }
}
