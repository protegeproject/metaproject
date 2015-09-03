package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.PolicyException;
import edu.stanford.protege.metaproject.api.exception.RoleNotFoundException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;

import java.io.Serializable;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of an access control policy where users are associated with roles. Roles have
 * a defined set of operations the user is allowed to perform within a set of projects
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PolicyImpl implements Policy, Serializable {
    private static final long serialVersionUID = -6333147884251670498L;
    private Map<UserId,Set<RoleId>> userRoleMap;

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
    PolicyImpl(Map<UserId, Set<RoleId>> userRoleMap, RoleManager roleManager, OperationManager operationManager, UserManager userManager, ProjectManager projectManager) {
        this.userRoleMap = checkNotNull(userRoleMap);
        this.roleManager = checkNotNull(roleManager);
        this.operationManager = checkNotNull(operationManager);
        this.userManager = checkNotNull(userManager);
        this.projectManager = checkNotNull(projectManager);
    }

    /**
     * Add a user to the access control policy with the specified role(s)
     *
     * @param userId    User identifier
     * @param roleIds    Role identifier(s)
     * @throws PolicyException    Policy exception
     */
    @Override
    public void addPolicy(UserId userId, RoleId... roleIds) throws PolicyException {
        checkExistence(userManager, userId);
        checkExistence(roleManager, roleIds);

        if(userRoleMap.containsKey(userId)) {
            Set<RoleId> roles = userRoleMap.get(userId);
            Collections.addAll(roles, roleIds);
            userRoleMap.put(userId, roles);
        }
        else {
            Set<RoleId> roles = new HashSet<>();
            Collections.addAll(roles, roleIds);
            userRoleMap.put(userId, roles);
        }
    }

    /**
     * Assign a role to one or more users in the access control policy
     *
     * @param roleId    Role identifier
     * @param userIds   User identifier(s)
     * @throws PolicyException    Policy exception
     */
    @Override
    public void addPolicy(RoleId roleId, UserId... userIds) throws PolicyException {
        for(UserId userId : userIds) {
            addPolicy(userId, roleId);
        }
    }

    /**
     * Remove a user-role tuple from the access control policy
     *
     * @param userId    User identifier
     * @param roleId    Role identifier
     * @throws PolicyException    Policy exception
     */
    @Override
    public void removePolicy(UserId userId, RoleId roleId) throws PolicyException {
        checkExistence(userManager, userId);
        checkExistence(roleManager, roleId);

        Set<RoleId> roles = new HashSet<>(getRoles(userId));
        roles.remove(roleId);

        if(roles.isEmpty()) {
            userRoleMap.remove(userId);
        }
        else {
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
     * @throws PolicyException    Policy exception
     */
    @Override
    public boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId) throws PolicyException {
        checkExistence(operationManager, operationId);
        checkExistence(projectManager, projectId);

        Set<RoleId> roles = getRoles(userId);
        for(RoleId role : roles) {
            try {
                Role r = roleManager.getRole(role);
                if(r.getProjects().contains(projectId) && r.getOperations().contains(operationId)) {
                    return true;
                }
            } catch (RoleNotFoundException e) {
                e.printStackTrace();
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
    @Override
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
    @Override
    public Set<RoleId> getRoles(UserId userId) throws UserNotInPolicyException {
        checkUserIsInPolicy(userId);
        return userRoleMap.get(userId);
    }

    /**
     * Get a map of users with their corresponding roles
     *
     * @return Map of users to their roles
     */
    @Override
    public Map<UserId,Set<RoleId>> getUserRoleMappings() {
        return userRoleMap;
    }

    /**
     * Get the policy role manager
     *
     * @return Role manager
     */
    @Override
    public RoleManager getRoleManager() {
        return roleManager;
    }

    /**
     * Get the policy operation manager
     *
     * @return Operation manager
     */
    @Override
    public OperationManager getOperationManager() {
        return operationManager;
    }

    /**
     * Get the policy user manager
     *
     * @return User manager
     */
    @Override
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * Get the policy project manager
     *
     * @return Project manager
     */
    @Override
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
            checkNotNull(obj);
            if(!manager.contains(obj)) {
                throw new PolicyException("The specified access control object does not correspond to a known one. " +
                        "It may not have been registered with the appropriate manager");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolicyImpl policy = (PolicyImpl) o;
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
        private RoleManager roleManager = new RoleManagerImpl();
        private OperationManager operationManager = new OperationManagerImpl();
        private UserManager userManager = new UserManagerImpl();
        private ProjectManager projectManager = new ProjectManagerImpl();

        public Builder setUserRoleMap(Map<UserId, Set<RoleId>> userRoleMap) {
            this.userRoleMap = userRoleMap;
            return this;
        }

        public Builder setRoleManager(RoleManager roleManager) {
            this.roleManager = roleManager;
            return this;
        }

        public Builder setOperationManager(OperationManager operationManager) {
            this.operationManager = operationManager;
            return this;
        }

        public Builder setUserManager(UserManager userManager) {
            this.userManager = userManager;
            return this;
        }

        public Builder setProjectManager(ProjectManager projectManager) {
            this.projectManager = projectManager;
            return this;
        }

        public PolicyImpl createAccessControlPolicy() {
            return new PolicyImpl(userRoleMap, roleManager, operationManager, userManager, projectManager);
        }
    }
}
