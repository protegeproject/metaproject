package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public final class ServerConfigurationImpl implements ServerConfiguration, Serializable {
    private static final long serialVersionUID = 7907697651569583698L;
    private static final Logger logger = LoggerFactory.getLogger(ServerConfigurationImpl.class.getName());
    private final ConfigurationManager configManager;
    private final ImmutableMap<UserId, Map<ProjectId, Set<RoleId>>> policyMap;
    private final ImmutableSet<User> users;
    private final ImmutableSet<Project> projects;
    private final ImmutableSet<Role> roles;
    private final ImmutableSet<Operation> operations;
    private final ImmutableSet<AuthenticationDetails> authDetails;
    private final ImmutableMap<String,String> properties;
    private final Host host;
    private final File root;

    /**
     * Package-private constructor; use {@link ServerConfigurationBuilder}
     *
     * @param configManager Configuration manager
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
    ServerConfigurationImpl(ConfigurationManager configManager, Host host, File root, Map<UserId, Map<ProjectId, Set<RoleId>>> policyMap, Set<User> users,
                            Set<Project> projects, Set<Role> roles, Set<Operation> operations, Set<AuthenticationDetails> authDetails, Map<String,String> properties) {
        this.configManager = checkNotNull(configManager);
        this.host = checkNotNull(host);
        this.root = checkNotNull(root);
        ImmutableMap<UserId, Map<ProjectId, Set<RoleId>>> policyMapCopy =
                new ImmutableMap.Builder<UserId, Map<ProjectId, Set<RoleId>>>().putAll(checkNotNull(policyMap)).build();
        ImmutableSet<User> usersCopy = new ImmutableSet.Builder<User>().addAll(checkNotNull(users)).build();
        ImmutableSet<Project> projectsCopy = new ImmutableSet.Builder<Project>().addAll(checkNotNull(projects)).build();
        ImmutableSet<Role> rolesCopy = new ImmutableSet.Builder<Role>().addAll(checkNotNull(roles)).build();
        ImmutableSet<Operation> operationsCopy = new ImmutableSet.Builder<Operation>().addAll(checkNotNull(operations)).build();
        ImmutableSet<AuthenticationDetails> authDetailsCopy = new ImmutableSet.Builder<AuthenticationDetails>().addAll(checkNotNull(authDetails)).build();
        ImmutableMap<String,String> propertiesCopy = new ImmutableMap.Builder<String,String>().putAll(checkNotNull(properties)).build();
        this.policyMap = checkNotNull(policyMapCopy);
        this.users = checkNotNull(usersCopy);
        this.projects = checkNotNull(projectsCopy);
        this.roles = checkNotNull(rolesCopy);
        this.operations = checkNotNull(operationsCopy);
        this.authDetails = checkNotNull(authDetailsCopy);
        this.properties = checkNotNull(propertiesCopy);
    }

    @Override
    public ConfigurationManager getConfigurationManager() {
        return configManager;
    }

    @Override
    public Host getHost() {
        return host;
    }

    @Override
    public File getServerRoot() {
        return root;
    }

    @Override
    public ImmutableMap<String,String> getProperties() {
        return properties;
    }

    @Override
    public String getProperty(String key) {
        return properties.get(key);
    }



     /* access control policy */

    @Override
    public ImmutableMap<UserId, Map<ProjectId, Set<RoleId>>> getPolicyMap() {
        return policyMap;
    }

    @Override
    public synchronized boolean hasRole(UserId userId, ProjectId projectId, RoleId roleId) {
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
    public synchronized Set<RoleId> getRoleIds(UserId userId, ProjectId projectId, GlobalPermissions globalPermissions) throws ProjectNotInPolicyException {
        Map<ProjectId,Set<RoleId>> assignments = getUserRoleMap(userId);
        if(globalPermissions.equals(GlobalPermissions.EXCLUDED)) {
            checkProjectIsInPolicy(userId, projectId);
            return new HashSet<>(assignments.get(projectId));
        } else {
            Set<RoleId> roles = new HashSet<>();
            if(assignments.containsKey(projectId)) {
                roles.addAll(assignments.get(projectId));
            }
            if(assignments.containsKey(ConfigurationUtils.getUniversalProjectId())) {
                roles.addAll(assignments.get(ConfigurationUtils.getUniversalProjectId()));
            }
            return roles;
        }
    }

    @Override
    public synchronized Set<RoleId> getRoleIds(UserId userId, GlobalPermissions globalPermissions) {
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
    public Set<ProjectId> getProjectIds(UserId userId) {
        return getUserRoleMap(userId).keySet();
    }

    @Override
    public synchronized boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId) {
        try {
            Set<RoleId> roles = new HashSet<>(getRoleIds(userId, projectId, GlobalPermissions.INCLUDED));
            for (RoleId role : roles) {
                Role r = getRole(role);
                if (r.getOperations().contains(operationId)) {
                    return true;
                }
            }
        } catch (UnknownRoleIdException | ProjectNotInPolicyException e) {
            return false;
        }
        return false;
    }

    @Override
    public synchronized boolean isOperationAllowed(OperationId operationId, UserId userId) {
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
    public synchronized boolean hasRole(UserId userId, ProjectId projectId) {
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
    public Map<ProjectId, Set<RoleId>> getUserRoleMap(UserId userId) {
        return new HashMap<>(getPolicyMap().get(userId));
    }

    @Override
    public synchronized Set<UserId> getUserIds(ProjectId projectId) {
        Map<UserId,Map<ProjectId,Set<RoleId>>> policyMap = getPolicyMap();
        return policyMap.keySet().stream().filter(userId ->
                policyMap.get(userId).keySet().contains(projectId)).collect(Collectors.toSet());
    }

    @Override
    public synchronized boolean hasRole(UserId id) {
        for (UserId userId : getPolicyMap().keySet()) {
            if (userId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verify whether given project identifier(s) are registered in the access control policy for the specified user
     *
     * @param userId User identifier
     * @throws ProjectNotInPolicyException  Project with given identifier not found in the access control policy
     */
    private synchronized void checkProjectIsInPolicy(UserId userId, ProjectId... projects) throws ProjectNotInPolicyException {
        Map<ProjectId, Set<RoleId>> roles = getPolicyMap().get(userId);
        for (ProjectId project : projects) {
            if (!roles.containsKey(project)) {
                throw new ProjectNotInPolicyException();
            }
        }
    }



    /* users */

    @Override
    public ImmutableSet<User> getUsers() {
        return users;
    }

    @Override
    public synchronized User getUser(UserId userId) throws UnknownUserIdException {
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
    public synchronized Set<User> getUsers(Name userName) {
        checkNotNull(userName);
        return getUsers().stream().filter(user -> user.getName().get().equals(userName.get())).collect(Collectors.toSet());
    }

    @Override
    public synchronized Set<User> getUsers(EmailAddress emailAddress) {
        checkNotNull(emailAddress);
        return getUsers().stream().filter(user -> user.getEmailAddress().equals(emailAddress)).collect(Collectors.toSet());
    }

    @Override
    public boolean containsUser(User user) {
        checkNotNull(user);
        return getUsers().contains(user);
    }

    @Override
    public boolean containsUser(UserId userId) {
        checkNotNull(userId);
        try {
            return containsUser(getUser(userId));
        } catch (UnknownUserIdException e) {
            /* no-op */
        }
        return false;
    }

    @Override
    public synchronized boolean isEmailAddressInUse(EmailAddress address) {
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
    public ImmutableSet<Project> getProjects() {
        return projects;
    }
    @Override
    public synchronized Project getProject(ProjectId projectId) throws UnknownProjectIdException {
        checkNotNull(projectId);
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
    public synchronized Set<Project> getProjects(Name projectName) {
        checkNotNull(projectName);
        return getProjects().stream().filter(project -> project.getName().equals(projectName) &&
                !project.getId().equals(ConfigurationUtils.getUniversalProjectId())).collect(Collectors.toSet());
    }

    @Override
    public synchronized Set<Project> getProjects(UserId userId) {
        Set<Project> projects = new HashSet<>();
        Set<ProjectId> projectIds = getProjectIds(userId);
        for (ProjectId projectId : projectIds) {
            try {
                projects.add(getProject(projectId));
            } catch (UnknownProjectIdException e) {
                logger.debug("The project with identifier '" + projectId.get() + "' is stated in the access control policy " +
                        "but there is no project with that identifier in the project registry.");
            }
        }
        return projects;
    }

    @Override
    public boolean containsProject(Project project) {
        checkNotNull(project);
        return getProjects().contains(project);
    }

    @Override
    public boolean containsProject(ProjectId projectId) {
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
    public ImmutableSet<Role> getRoles() {
        return roles;
    }

    @Override
    public synchronized Role getRole(RoleId roleId) throws UnknownRoleIdException {
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
    public synchronized Set<Role> getRoles(UserId userId, GlobalPermissions globalPermissions) {
        Set<RoleId> roleIds = getRoleIds(userId, globalPermissions);
        return getRoles(roleIds);
    }

    @Override
    public synchronized Set<Role> getRoles(UserId userId, ProjectId projectId, GlobalPermissions globalPermissions) throws ProjectNotInPolicyException {
        Set<RoleId> roleIds = getRoleIds(userId, projectId, globalPermissions);
        return getRoles(roleIds);
    }

    private synchronized Set<Role> getRoles(Set<RoleId> roleIds) {
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
    public boolean containsRole(Role role) {
        checkNotNull(role);
        return getRoles().contains(role);
    }

    @Override
    public boolean containsRole(RoleId roleId) {
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
    public ImmutableSet<Operation> getOperations() {
        return operations;
    }

    @Override
    public synchronized Operation getOperation(OperationId operationId) throws UnknownOperationIdException {
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
    public synchronized Set<Operation> getOperations(UserId userId, GlobalPermissions globalPermissions) {
        Set<Role> roles = getRoles(userId, globalPermissions);
        return getOperations(roles);
    }

    @Override
    public synchronized Set<Operation> getOperations(UserId userId, ProjectId projectId, GlobalPermissions globalPermissions) throws ProjectNotInPolicyException {
        Set<Role> roles = getRoles(userId, projectId, globalPermissions);
        return getOperations(roles);
    }

    @Override
    public synchronized Set<Operation> getOperations(Set<Role> roles) {
        Set<Operation> operations = new HashSet<>();
        for (Role role : roles) {
            operations.addAll(getOperations(role));
        }
        return operations;
    }

    @Override
    public synchronized Set<Operation> getOperations(Role role) {
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
    public boolean containsOperation(Operation operation) {
        checkNotNull(operation);
        return getOperations().contains(operation);
    }

    @Override
    public boolean containsOperation(OperationId operationId) {
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
    public ImmutableSet<AuthenticationDetails> getAuthenticationDetails() {
        return authDetails;
    }

    @Override
    public AuthenticationDetails getAuthenticationDetails(UserId userId) throws UserNotRegisteredException {
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
    public Salt getSalt(UserId userId) throws UserNotRegisteredException {
        return getAuthenticationDetails(userId).getPassword().getSalt();
    }

    @Override
    public boolean isRegistered(UserId userId) {
        for(AuthenticationDetails userDetails : getAuthenticationDetails()) {
            if(userDetails.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean hasValidCredentials(UserId userId, SaltedPasswordDigest password) throws UserNotRegisteredException {
        SaltedPasswordDigest correctHash = getAuthenticationDetails(userId).getPassword();
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
    private synchronized boolean slowEquals(byte[] a, byte[] b) {
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
        return Objects.equal(configManager, that.getConfigurationManager()) &&
                Objects.equal(policyMap, that.getPolicyMap()) &&
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
        return Objects.hashCode(configManager, policyMap, roles, operations, users, projects, authDetails, properties, host, root);
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