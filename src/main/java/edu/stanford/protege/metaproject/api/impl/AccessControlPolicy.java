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
public final class AccessControlPolicy implements Policy, Serializable {
    private static final long serialVersionUID = 1623781444755044301L;
    private static AccessControlPolicy instance = null;
    private static Map<User,Set<Role>> userRoleMap = new HashMap<>();

    private RoleManager roleManager = RoleManager.getInstance();
    private OperationManager operationManager = OperationManager.getInstance();
    private UserManager userManager = UserManager.getInstance();
    private ProjectManager projectManager = ProjectManager.getInstance();

    /**
     * Private constructor
     *
     * @param userRoleMap   Map of users to their roles
     */
    private AccessControlPolicy(Map<User,Set<Role>> userRoleMap) {
        this.userRoleMap = checkNotNull(userRoleMap);
    }

    /**
     * Get the singleton instance of the access control policy
     *
     * @param userRoles Map of users to their roles
     * @return Access control policy
     */
    public static AccessControlPolicy getInstance(Map<User,Set<Role>> userRoles) {
        if(instance == null || !userRoleMap.equals(userRoles)) {
            instance = new AccessControlPolicy(userRoles);
        }
        return instance;
    }

    /**
     * Get the singleton instance of the access control policy
     *
     * @return Access control policy
     */
    public static AccessControlPolicy getInstance() {
        if(instance == null) {
            instance = new AccessControlPolicy(new HashMap<>());
        }
        return instance;
    }

    /**
     * Add a user-role tuple to the access control policy
     *
     * @param user    User
     * @param role    Role
     * @throws PolicyException    Policy exception
     */
    public void addPolicy(User user, Role role) throws PolicyException {
        checkExistence(userManager, user); checkExistence(roleManager, role);
        if(userRoleMap.containsKey(user)) {
            Set<Role> roles = userRoleMap.get(user);
            roles.add(role);
            userRoleMap.put(user, roles);
        }
        else {
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            userRoleMap.put(user, roles);
        }
    }

    /**
     * Add a user with multiple roles to the access control policy
     *
     * @param user    User
     * @param roles Set of roles
     * @throws PolicyException    Policy exception
     */
    public void addPolicy(User user, Set<Role> roles) throws PolicyException {
        checkExistence(userManager, user); checkExistence(roleManager, roles.toArray(new Role[roles.size()]));
        if(userRoleMap.containsKey(user)) {
            Set<Role> roleSet = userRoleMap.get(user);
            roleSet.addAll(roles);
            userRoleMap.put(user, roleSet);
        }
        else {
            userRoleMap.put(user, roles);
        }
    }

    /**
     * Add multiple users with a single role to the access control policy
     *
     * @param users Set of users
     * @param role    Role
     * @throws PolicyException    Policy exception
     */
    public void addPolicy(Set<User> users, Role role) throws PolicyException {
        for(User user : users) {
            addPolicy(user, role);
        }
    }

    /**
     * Remove a user-role tuple from the access control policy
     *
     * @param user    User
     * @param role    Role
     * @throws PolicyException    Policy exception
     */
    public void removePolicy(User user, Role role) throws PolicyException {
        checkExistence(userManager, user); checkExistence(roleManager, role);
        Set<Role> roles = userRoleMap.get(user);
        roles.remove(role);
        userRoleMap.put(user, roles);
    }

    /**
     * Check whether the specified operation is allowed for the given user
     *
     * @param operation Operation
     * @param project   Project
     * @param user  User
     * @return true if user is allowed to carry out the specified operation, false otherwise
     * @throws UserNotInPolicyException    User not registered in the access control policy
     * @throws PolicyException    Policy exception
     */
    public boolean isOperationAllowed(Operation operation, Project project, User user) throws UserNotInPolicyException, PolicyException {
        checkExistence(operationManager, operation); checkExistence(projectManager, project); checkUserIsInPolicy(user);
        Set<Role> roles = userRoleMap.get(user);
        for (Role role : roles) {
            if (role.getProjects().contains(project) && role.getOperations().contains(operation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a given user has the specified role
     *
     * @param user  User unique identifier as used to login
     * @param role  Role
     * @return true if user has specified role, false otherwise
     * @throws PolicyException    Policy exception
     */
    public boolean hasRole(User user, Role role) throws PolicyException {
        checkUserIsInPolicy(user); checkExistence(roleManager, role);
        Set<Role> roles = userRoleMap.get(user);
        return roles.contains(role);
    }

    /**
     * Get the set of all roles associated with a user
     *
     * @param user  User
     * @return Set of roles the user has
     * @throws UserNotInPolicyException    User not registered in the access control policy
     */
    public Set<Role> getRoles(User user) throws UserNotInPolicyException {
        checkUserIsInPolicy(user);
        return userRoleMap.get(user);
    }

    /**
     * Get a map of users with their corresponding roles
     *
     * @return Map of users to their roles
     */
    public Map<User,Set<Role>> getUserRoleMappings() {
        return userRoleMap;
    }

    /**
     * Verify whether given user identifier(s) are registered in the access control policy, i.e., in the user-role map
     *
     * @param users   One or more users
     * @throws UserNotInPolicyException User not registered in the access control policy
     */
    private void checkUserIsInPolicy(User... users) throws UserNotInPolicyException {
        for(User user : users) {
            if (!userRoleMap.containsKey(user)) {
                throw new UserNotInPolicyException("The specified user is not registered in the access control policy");
            }
        }
    }

    /**
     * Verify whether access control object(s) are registered with the given manager
     *
     * @param manager   Manager for given access control object
     * @param objects   One or more access control objects
     * @throws PolicyException  Policy exception
     */
    private void checkExistence(Manager manager, AccessControlObject... objects) throws PolicyException {
        for(AccessControlObject obj : objects) {
            if(!manager.exists(obj.getId())) {
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
}
