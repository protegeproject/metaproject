package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class ServerConfigurationImpl implements ServerConfiguration, Serializable {
    private static final long serialVersionUID = -8439017057476248949L;
    private static final Logger logger = LoggerFactory.getLogger(ServerConfigurationImpl.class.getName());
    @Nonnull private final ImmutableMap<UserId, Map<ProjectId, Set<RoleId>>> policyMap;
    @Nonnull private final ImmutableSet<User> users;
    @Nonnull private final ImmutableSet<Project> projects;
    @Nonnull private final ImmutableSet<Role> roles;
    @Nonnull private final ImmutableSet<Operation> operations;
    @Nonnull private final ImmutableSet<AuthenticationDetails> authDetails;
    @Nonnull private final ImmutableMap<String,String> properties;
    @Nonnull private final Host host;
    @Nonnull private final String root;

    /**
     * Package-private constructor; use {@link ConfigurationBuilder}
     *
     * @param host    Host
     * @param root  Root directory of the server
     * @param policyMap    Policy map
     * @param roles Set of roles
     * @param operations    Set of operations
     * @param users Set of users
     * @param projects   Set of projects
     * @param authDetails   Set of user authentication details
     * @param properties   Map of custom configuration properties
     */
    ServerConfigurationImpl(@Nonnull Host host, @Nonnull String root, @Nonnull Map<UserId, Map<ProjectId, Set<RoleId>>> policyMap,
                            @Nonnull Set<User> users, @Nonnull Set<Project> projects, @Nonnull Set<Role> roles, @Nonnull Set<Operation> operations,
                            @Nonnull Set<AuthenticationDetails> authDetails, @Nonnull Map<String,String> properties) {
        this.host = checkNotNull(host);
        this.root = checkNotNull(root);
        this.policyMap = ImmutableMap.copyOf(checkNotNull(policyMap));
        this.users = ImmutableSet.copyOf(checkNotNull(users));
        this.projects = ImmutableSet.copyOf(checkNotNull(projects));
        this.roles = ImmutableSet.copyOf(checkNotNull(roles));
        this.operations = ImmutableSet.copyOf(checkNotNull(operations));
        this.authDetails = ImmutableSet.copyOf(checkNotNull(authDetails));
        this.properties = ImmutableMap.copyOf(checkNotNull(properties));
    }

    @Override
    @Nonnull
    public Host getHost() {
        return host;
    }

    @Override
    @Nonnull
    public String getServerRoot() {
        return root;
    }

    @Override
    @Nonnull
    public ImmutableMap<String,String> getProperties() {
        return properties;
    }

    @Override
    @Nullable
    public String getProperty(@Nonnull String key) {
        return properties.get(key);
    }



     /* access control policy */

    @Override
    @Nonnull
    public ImmutableMap<UserId, Map<ProjectId, Set<RoleId>>> getPolicyMap() {
        return policyMap;
    }

    @Override
    public boolean hasRole(@Nonnull UserId userId, @Nonnull ProjectId projectId, @Nonnull RoleId roleId) {
        Map<UserId,Map<ProjectId,Set<RoleId>>> policyMap = getPolicyMap();
        if (policyMap.containsKey(userId)) {
            Map<ProjectId, Set<RoleId>> assignments = getUserRoleMap(userId);
            if (assignments.containsKey(projectId)) {
                if(assignments.get(projectId).contains(roleId)) {
                    return true;
                }
            }
            if (assignments.containsKey(ConfigurationUtils.getUniversalProjectId())) {
                return assignments.get(ConfigurationUtils.getUniversalProjectId()).contains(roleId);
            }
        }
        return false;
    }

    @Override
    @Nonnull
    public Set<RoleId> getRoleIds(@Nonnull UserId userId, @Nonnull ProjectId projectId, @Nonnull GlobalPermissions globalPermissions) {
        Map<ProjectId,Set<RoleId>> assignments = getUserRoleMap(userId);
        Set<RoleId> roles = new HashSet<>();
        if(assignments.containsKey(projectId)) {
            roles.addAll(assignments.get(projectId));
        }
        if(globalPermissions.equals(GlobalPermissions.INCLUDED) && assignments.containsKey(ConfigurationUtils.getUniversalProjectId())) {
            roles.addAll(assignments.get(ConfigurationUtils.getUniversalProjectId()));
        }
        return roles;
    }

    @Override
    @Nonnull
    public Set<RoleId> getRoleIds(@Nonnull UserId userId, @Nonnull GlobalPermissions globalPermissions) {
        Map<ProjectId,Set<RoleId>> map = getUserRoleMap(userId);
        Set<RoleId> roles = new HashSet<>();
        for(ProjectId p : map.keySet()) {
            if(p.equals(ConfigurationUtils.getUniversalProjectId()) && globalPermissions.equals(GlobalPermissions.EXCLUDED)) {
                continue;
            }
            roles.addAll(map.get(p));
        }
        return roles;
    }

    @Override
    @Nonnull
    public Set<ProjectId> getProjectIds(@Nonnull UserId userId) {
        return new HashSet<>(getUserRoleMap(userId).keySet());
    }

    @Override
    public boolean isOperationAllowed(@Nonnull OperationId operationId, @Nonnull ProjectId projectId, @Nonnull UserId userId) {
        try {
            Set<RoleId> roles = new HashSet<>(getRoleIds(userId, projectId, GlobalPermissions.INCLUDED));
            for (RoleId role : roles) {
                Role r = getRole(role);
                if (r.getOperations().contains(operationId)) {
                    return true;
                }
            }
        } catch (UnknownRoleIdException e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean isOperationAllowed(@Nonnull OperationId operationId, @Nonnull UserId userId) {
        try {
            Set<RoleId> roles = getRoleIds(userId, GlobalPermissions.INCLUDED);
            for (RoleId role : roles) {
                Role r = getRole(role);
                if (r.getOperations().contains(operationId)) {
                    return true;
                }
            }
        } catch (UnknownRoleIdException e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean hasRole(@Nonnull UserId userId, @Nonnull ProjectId projectId) {
        Map<UserId,Map<ProjectId,Set<RoleId>>> policyMap = getPolicyMap();
        for (UserId user : policyMap.keySet()) {
            if (user.equals(userId)) {
                for (ProjectId project : policyMap.get(userId).keySet()) {
                    if (project.equals(projectId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    @Nonnull
    public Map<ProjectId, Set<RoleId>> getUserRoleMap(@Nonnull UserId userId) {
        Map<ProjectId, Set<RoleId>> map = new HashMap<>();
        if(getPolicyMap().get(userId) != null) {
            return getPolicyMap().get(userId);
        }
        return map;
    }

    @Override
    @Nonnull
    public Set<UserId> getUserIds(@Nonnull ProjectId projectId) {
        Map<UserId,Map<ProjectId,Set<RoleId>>> policyMap = getPolicyMap();
        return policyMap.keySet().stream().filter(userId ->
                policyMap.get(userId).keySet().contains(projectId)).collect(Collectors.toSet());
    }

    @Override
    public boolean hasRole(@Nonnull UserId id) {
        for (UserId userId : getPolicyMap().keySet()) {
            if (userId.equals(id)) {
                return true;
            }
        }
        return false;
    }



    /* users */

    @Override
    @Nonnull
    public ImmutableSet<User> getUsers() {
        return users;
    }

    @Override
    @Nonnull
    public User getUser(@Nonnull UserId userId) throws UnknownUserIdException {
        checkNotNull(userId);
        Set<User> users = getUsers();
        User user = null;
        for(User u : users) {
            if(u.getId().equals(userId)) {
                user = u;
                break;
            }
        }
        if(user != null) {
            return user;
        } else {
            throw new UnknownUserIdException("The specified user identifier does not correspond to an existing user");
        }
    }

    @Override
    @Nonnull
    public Set<User> getUsers(@Nonnull Name userName) {
        checkNotNull(userName);
        return getUsers().stream().filter(user -> user.getName().get().equals(userName.get())).collect(Collectors.toSet());
    }

    @Override
    @Nonnull
    public Set<User> getUsers(@Nonnull EmailAddress emailAddress) {
        checkNotNull(emailAddress);
        return getUsers().stream().filter(user -> user.getEmailAddress().equals(emailAddress)).collect(Collectors.toSet());
    }

    @Override
    public boolean containsUser(@Nonnull User user) {
        checkNotNull(user);
        return getUsers().contains(user);
    }

    @Override
    public boolean containsUser(@Nonnull UserId userId) {
        checkNotNull(userId);
        try {
            return containsUser(getUser(userId));
        } catch (UnknownUserIdException e) {
            /* no-op */
        }
        return false;
    }

    @Override
    public boolean isEmailAddressInUse(@Nonnull EmailAddress address) {
        checkNotNull(address);
        for(User u : getUsers()) {
            if(u.getEmailAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }



    /* projects */

    @Override
    @Nonnull
    public ImmutableSet<Project> getProjects() {
        return projects;
    }

    @Override
    @Nonnull
    public Project getProject(@Nonnull ProjectId projectId) throws UnknownProjectIdException {
        checkNotNull(projectId);
        if(projectId.equals(ConfigurationUtils.getUniversalProjectId())) {
            return ConfigurationUtils.getUniversalProject();
        }
        Set<Project> projects = getProjects();
        Project project = null;
        for(Project p : projects) {
            if(p.getId().equals(projectId)) {
                project = p;
                break;
            }
        }
        if(project != null) {
            return project;
        } else {
            throw new UnknownProjectIdException("The specified project identifier does not correspond to an existing project");
        }
    }

    @Override
    @Nonnull
    public Set<Project> getProjects(@Nonnull Name projectName) {
        checkNotNull(projectName);
        return getProjects().stream().filter(project -> project.getName().equals(projectName) &&
                !project.getId().equals(ConfigurationUtils.getUniversalProjectId())).collect(Collectors.toSet());
    }

    @Override
    @Nonnull
    public Set<Project> getProjects(@Nonnull UserId userId) {
        Set<Project> projects = new HashSet<>();
        Set<ProjectId> projectIds = getProjectIds(userId);
        for (ProjectId projectId : projectIds) {
            try {
                Project p = getProject(projectId);
                projects.add(p);
            } catch (UnknownProjectIdException e) {
                logger.debug("The project with identifier '" + projectId.get() + "' is stated in the access control policy " +
                        "but there is no project with that identifier in the project registry.");
            }
        }
        return projects;
    }

    @Override
    public boolean containsProject(@Nonnull Project project) {
        checkNotNull(project);
        return getProjects().contains(project);
    }

    @Override
    public boolean containsProject(@Nonnull ProjectId projectId) {
        checkNotNull(projectId);
        try {
            return containsProject(getProject(projectId));
        } catch (UnknownProjectIdException e) {
            /* no-op */
        }
        return false;
    }



    /* roles */

    @Override
    @Nonnull
    public ImmutableSet<Role> getRoles() {
        return roles;
    }

    @Override
    @Nonnull
    public Role getRole(@Nonnull RoleId roleId) throws UnknownRoleIdException {
        checkNotNull(roleId);
        Role role = null;
        for(Role r : roles) {
            if(r.getId().equals(roleId)) {
                role = r; break;
            }
        }
        if(role != null) {
            return role;
        } else {
            throw new UnknownRoleIdException("The specified role identifier does not correspond to an existing role");
        }
    }

    @Override
    @Nonnull
    public Set<Role> getRoles(@Nonnull UserId userId, @Nonnull GlobalPermissions globalPermissions) {
        Set<RoleId> roleIds = getRoleIds(userId, globalPermissions);
        return getRoles(roleIds);
    }

    @Override
    @Nonnull
    public Set<Role> getRoles(@Nonnull UserId userId, @Nonnull ProjectId projectId, @Nonnull GlobalPermissions globalPermissions) {
        Set<RoleId> roleIds = getRoleIds(userId, projectId, globalPermissions);
        return getRoles(roleIds);
    }

    private Set<Role> getRoles(@Nonnull Set<RoleId> roleIds) {
        Set<Role> roles = new HashSet<>();
        for (RoleId roleId : roleIds) {
            try {
                roles.add(getRole(roleId));
            } catch (UnknownRoleIdException e) {
                logger.debug("The role with identifier '" + roleId.get() + "' is stated in the access control policy " +
                        "but there is no role with that identifier in the role registry.");
            }
        }
        return roles;
    }

    @Override
    public boolean containsRole(@Nonnull Role role) {
        checkNotNull(role);
        return getRoles().contains(role);
    }

    @Override
    public boolean containsRole(@Nonnull RoleId roleId) {
        checkNotNull(roleId);
        try {
            return containsRole(getRole(roleId));
        } catch (UnknownRoleIdException e) {
            /* no-op */
        }
        return false;
    }



    /* operations */

    @Override
    @Nonnull
    public ImmutableSet<Operation> getOperations() {
        return operations;
    }

    @Override
    @Nonnull
    public Operation getOperation(@Nonnull OperationId operationId) throws UnknownOperationIdException {
        checkNotNull(operationId);
        Set<Operation> operations = getOperations();
        Operation operation = null;
        for(Operation o : operations) {
            if(o.getId().equals(operationId)) {
                operation = o; break;
            }
        }
        if(operation != null) {
            return operation;
        } else {
            throw new UnknownOperationIdException("The specified operation identifier does not correspond to an existing operation");
        }
    }

    @Override
    @Nonnull
    public Set<Operation> getOperations(@Nonnull UserId userId, @Nonnull GlobalPermissions globalPermissions) {
        Set<Role> roles = getRoles(userId, globalPermissions);
        return getOperations(roles);
    }

    @Override
    @Nonnull
    public Set<Operation> getOperations(@Nonnull UserId userId, @Nonnull ProjectId projectId, @Nonnull GlobalPermissions globalPermissions) {
        Set<Role> roles = getRoles(userId, projectId, globalPermissions);
        return getOperations(roles);
    }

    @Override
    @Nonnull
    public Set<Operation> getOperations(@Nonnull Set<Role> roles) {
        Set<Operation> operations = new HashSet<>();
        for (Role role : roles) {
            operations.addAll(getOperations(role));
        }
        return operations;
    }

    @Override
    @Nonnull
    public Set<Operation> getOperations(@Nonnull Role role) {
        Set<Operation> operations = new HashSet<>();
        for (OperationId opId : role.getOperations()) {
            try {
                operations.add(getOperation(opId));
            } catch (UnknownOperationIdException e) {
                logger.debug("The operation with identifier '" + opId.get() + "' is stated in the operation list of the role '" +
                        role.getName() + "', but there is no operation with that identifier in the operation registry.");
            }
        }
        return operations;
    }

    @Override
    public boolean containsOperation(@Nonnull Operation operation) {
        checkNotNull(operation);
        return getOperations().contains(operation);
    }

    @Override
    public boolean containsOperation(@Nonnull OperationId operationId) {
        checkNotNull(operationId);
        try {
            return containsOperation(getOperation(operationId));
        } catch (UnknownOperationIdException e) {
            /* no-op */
        }
        return false;
    }



    /* authentication */

    @Override
    @Nonnull
    public ImmutableSet<AuthenticationDetails> getAuthenticationDetails() {
        return authDetails;
    }

    @Override
    @Nonnull
    public AuthenticationDetails getAuthenticationDetails(@Nonnull UserId userId) throws UserNotRegisteredException {
        AuthenticationDetails details = null;
        for(AuthenticationDetails userDetails : getAuthenticationDetails()) {
            if (userDetails.getUserId().equals(userId)) {
                details = userDetails;
                break;
            }
        }
        if(details == null) {
            throw new UserNotRegisteredException("The specified user identifier does not correspond to a user registered" +
                    " with the authentication manager.");
        }
        return details;
    }

    @Override
    @Nonnull
    public Salt getSalt(@Nonnull UserId userId) throws UserNotRegisteredException {
        return getAuthenticationDetails(userId).getPassword().getSalt();
    }

    @Override
    public boolean isRegistered(@Nonnull UserId userId) {
        for(AuthenticationDetails userDetails : getAuthenticationDetails()) {
            if(userDetails.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasValidCredentials(@Nonnull UserId userId, @Nonnull SaltedPasswordDigest password) {
        SaltedPasswordDigest correctHash;
        try {
            correctHash = getAuthenticationDetails(userId).getPassword();
        } catch (UserNotRegisteredException e) {
            logger.debug("User identifier " + userId.get() + " does not correspond to a registered user");
            return false;
        }
        return slowEquals(password.getPassword().getBytes(), correctHash.getPassword().getBytes());
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method is used so that password hashes
     * cannot be extracted from an on-line system using a timing attack and then attacked off-line
     *
     * @param a First byte array
     * @param b Second byte array
     * @return true if both byte arrays are the same, false otherwise
     */
    private boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServerConfiguration)) {
            return false;
        }
        ServerConfiguration that = (ServerConfiguration) o;
        return Objects.equal(policyMap, that.getPolicyMap()) &&
                Objects.equal(roles, that.getRoles()) &&
                Objects.equal(operations, that.getOperations()) &&
                Objects.equal(users, that.getUsers()) &&
                Objects.equal(projects, that.getProjects()) &&
                Objects.equal(authDetails, that.getAuthenticationDetails()) &&
                Objects.equal(properties, that.getProperties()) &&
                Objects.equal(host, that.getHost()) &&
                Objects.equal(root, that.getServerRoot());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(policyMap, roles, operations, users, projects, authDetails, properties, host, root);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("policyMap", policyMap)
                .add("roles", roles)
                .add("operations", operations)
                .add("users", users)
                .add("projects", projects)
                .add("authDetails", authDetails)
                .add("properties", properties)
                .add("host", host)
                .add("root", root)
                .toString();
    }
}