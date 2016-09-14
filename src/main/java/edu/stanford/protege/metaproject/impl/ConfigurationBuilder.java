package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.ConfigurationManager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builder for server configuration instances
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ConfigurationBuilder {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationBuilder.class.getName());
    private Host host = ConfigurationUtils.getServerHost();
    private File root = ConfigurationUtils.getServerRoot();
    private Map<UserId, Map<ProjectId, Set<RoleId>>> policyMap = ConfigurationUtils.getDefaultPolicy();
    private Set<Role> roles = ConfigurationUtils.getDefaultRoles();
    private Set<Operation> operations = ConfigurationUtils.getDefaultOperations();
    private Set<User> users = ConfigurationUtils.getDefaultUsers();
    private Set<Project> projects = ConfigurationUtils.getDefaultProjects();
    private Set<AuthenticationDetails> authDetails = ConfigurationUtils.getDefaultAuthenticationDetails();
    private Map<String,String> properties = new HashMap<>();
    private PolicyFactory factory = ConfigurationManager.getFactory();

    /**
     * No-arguments constructor
     */
    public ConfigurationBuilder() { }

    /**
     * Constructor that reuses the given server configuration
     *
     * @param config   Server configuration
     */
    public ConfigurationBuilder(ServerConfiguration config) {
        this.host = checkNotNull(config.getHost());
        this.root = checkNotNull(config.getServerRoot());
        this.policyMap = new HashMap<>(checkNotNull(config.getPolicyMap()));
        this.roles = new HashSet<>(checkNotNull(config.getRoles()));
        this.operations = new HashSet<>(checkNotNull(config.getOperations()));
        this.users = new HashSet<>(checkNotNull(config.getUsers()));
        this.projects = new HashSet<>(checkNotNull(config.getProjects()));
        this.authDetails = new HashSet<>(checkNotNull(config.getAuthenticationDetails()));
        this.properties = new HashMap<>(checkNotNull(config.getProperties()));
    }

    /**
     * Set the server host information
     *
     * @param host  Host
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setHost(Host host) {
        this.host = host;
        return this;
    }

    /**
     * Set the root directory of the server
     *
     * @param root  Root directory of the server
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setServerRoot(File root) {
        this.root = root;
        return this;
    }

    /**
     * Set the metaproject policy map, defining what users have what roles in which projects
     *
     * @param policyMap    Policy
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setPolicyMap(Map<UserId, Map<ProjectId, Set<RoleId>>> policyMap) {
        this.policyMap = new HashMap<>(policyMap);
        return this;
    }

    /**
     * Set the collection of roles
     *
     * @param roles  Roles
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setRoles(Set<Role> roles) {
        this.roles = new HashSet<>(roles);
        return this;
    }

    /**
     * Set the collection of operations
     *
     * @param operations Operations
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setOperations(Set<Operation> operations) {
        this.operations = new HashSet<>(operations);
        return this;
    }

    /**
     * Set the collection of users
     *
     * @param users  Users
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setUsers(Set<User> users) {
        this.users = new HashSet<>(users);
        return this;
    }

    /**
     * Set the collection of projects
     *
     * @param projects    Projects
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setProjects(Set<Project> projects) {
        this.projects = new HashSet<>(projects);
        return this;
    }

    /**
     * Set the authentication details
     *
     * @param authDetails   Set of authentication details
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setAuthenticationDetails(Set<AuthenticationDetails> authDetails) {
        this.authDetails = new HashSet<>(authDetails);
        return this;
    }

    /**
     * Set the map of custom server configuration properties
     *
     * @param propertyMap   Custom properties map
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setProperties(Map<String,String> propertyMap) {
        this.properties = new HashMap<>(propertyMap);
        return this;
    }


    /* server settings */

    /**
     * Add a custom property to the configuration property map
     *
     * @param key   Property key
     * @param value Property value
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder addProperty(String key, String value) {
        checkNotNull(key);
        checkNotNull(value);
        properties.put(key, value);
        return this;
    }

    /**
     * Remove the custom property with the given key from the configuration property map
     *
     * @param key   Property key
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder removeProperty(String key) {
        checkNotNull(key);
        properties.remove(key);
        return this;
    }


    /* users */

    /**
     * Add the given user to the configuration
     *
     * @param user  New user
     * @throws IdAlreadyInUseException  User identifier already in use
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder addUser(User user) throws IdAlreadyInUseException {
        checkNotNull(user);
        if (contains(user.getId(), users)) {
            throw new IdAlreadyInUseException("The specified user identifier is already used by another user");
        }
        users.add(user);
        return this;
    }

    /**
     * Remove the given user from the configuration
     *
     * @param user  User
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder removeUser(User user) {
        checkNotNull(user);
        users.remove(user);
        return this;
    }

    /**
     * Modify the user that has the specified user identifier with the given user
     *
     * @param userId    User identifier
     * @param user  User
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setUser(UserId userId, User user) {
        checkNotNull(userId);
        checkNotNull(user);
        getUser(userId).ifPresent(this::removeUser);
        try {
            addUser(user);
        } catch (IdAlreadyInUseException e) {
            logger.debug("Attempted to replace a user but the original user instance was not properly deleted beforehand");
        }
        return this;
    }

    /**
     * Change the display name of the given user
     *
     * @param userId    User identifier
     * @param userName  New name
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setUserName(UserId userId, Name userName) {
        checkNotNull(userId);
        checkNotNull(userName);
        getUser(userId).ifPresent(user -> setUser(userId, factory.getUser(userId, userName, user.getEmailAddress())));
        return this;
    }

    /**
     * Change the email address of a user
     *
     * @param userId  User identifier
     * @param emailAddress New email address
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setUserEmailAddress(UserId userId, EmailAddress emailAddress) {
        checkNotNull(userId);
        checkNotNull(emailAddress);
        getUser(userId).ifPresent(user -> setUser(userId, factory.getUser(userId, user.getName(), emailAddress)));
        return this;
    }

    private Optional<User> getUser(UserId userId) {
        for(User u : users) {
            if(u.getId().equals(userId)) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }


    /* projects */

    /**
     * Add the given project to the configuration
     *
     * @param project   New project
     * @throws IdAlreadyInUseException  Project identifier already in use
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder addProject(Project project) throws IdAlreadyInUseException {
        checkNotNull(project);
        if (projects.contains(project)) {
            throw new IdAlreadyInUseException("The specified project identifier is already used by another project");
        }
        projects.add(project);
        return this;
    }

    /**
     * Remove the given project from the configuration
     *
     * @param project   Project
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder removeProject(Project project) {
        checkNotNull(project);
        projects.remove(project);
        return this;
    }

    /**
     * Modify the project with the specified project identifier with the given project
     *
     * @param projectId Project identifier
     * @param project   Project
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setProject(ProjectId projectId, Project project) {
        checkNotNull(projectId);
        checkNotNull(project);
        getProject(projectId).ifPresent(this::removeProject);
        try {
            addProject(project);
        } catch (IdAlreadyInUseException e) {
            logger.debug("Attempted to replace a project but the original project instance was not properly deleted beforehand");
        }
        return this;
    }

    /**
     * Change the name of the given project
     *
     * @param projectId   Project identifier
     * @param projectName   New project name
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setProjectName(ProjectId projectId, Name projectName) {
        checkNotNull(projectId);
        checkNotNull(projectName);
        getProject(projectId).ifPresent(project -> setProject(projectId,
                factory.getProject(project.getId(), projectName, project.getDescription(), project.getFile(), project.getOwner(), project.getOptions())));
        return this;
    }

    /**
     * Change the description of the given project
     *
     * @param projectId   Project identifier
     * @param projectDescription    New project description
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setProjectDescription(ProjectId projectId, Description projectDescription) {
        checkNotNull(projectId);
        checkNotNull(projectDescription);
        getProject(projectId).ifPresent(project -> setProject(projectId,
                factory.getProject(project.getId(), project.getName(), projectDescription, project.getFile(), project.getOwner(), project.getOptions())));
        return this;
    }

    /**
     * Change the owner of the specified project
     *
     * @param projectId   Project identifier
     * @param userId    New owner user identifier
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setProjectOwner(ProjectId projectId, UserId userId) {
        checkNotNull(projectId);
        checkNotNull(userId);
        getProject(projectId).ifPresent(project -> setProject(projectId,
                factory.getProject(project.getId(), project.getName(), project.getDescription(), project.getFile(), userId, project.getOptions())));
        return this;
    }

    /**
     * Change the file location of the specified project
     *
     * @param projectId Project identifier
     * @param file   Project file
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setProjectFile(ProjectId projectId, File file) {
        checkNotNull(projectId);
        checkNotNull(file);
        getProject(projectId).ifPresent(project -> setProject(projectId,
                factory.getProject(project.getId(), project.getName(), project.getDescription(), file, project.getOwner(), project.getOptions())));
        return this;
    }

    /**
     * Set the options for this project
     *
     * @param projectId Project identifier
     * @param projectOptions    Project options
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setProjectOptions(ProjectId projectId, ProjectOptions projectOptions) {
        checkNotNull(projectId);
        checkNotNull(projectOptions);
        getProject(projectId).ifPresent(project -> setProject(projectId,
                factory.getProject(projectId, project.getName(), project.getDescription(), project.getFile(), project.getOwner(), Optional.of(projectOptions))));
        return this;
    }

    private Optional<Project> getProject(ProjectId projectId) {
        for(Project p : projects) {
            if(p.getId().equals(projectId)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }


    /* roles */

    /**
     * Add the given role to the configuration
     *
     * @param role  New role
     * @throws IdAlreadyInUseException  Role identifier already in use
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder addRole(Role role) throws IdAlreadyInUseException {
        checkNotNull(role);
        if (contains(role.getId(), roles)) {
            throw new IdAlreadyInUseException("The specified role identifier is already used in another role");
        }
        roles.add(role);
        return this;
    }

    /**
     * Remove the given role from the configuration
     *
     * @param role  Role to be removed
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder removeRole(Role role) {
        checkNotNull(role);
        roles.remove(role);
        return this;
    }

    /**
     * Modify the role that has the specified role identifier with the given role
     *
     * @param roleId    Role identifier
     * @param role  Role
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setRole(RoleId roleId, Role role) {
        checkNotNull(roleId);
        checkNotNull(role);
        getRole(roleId).ifPresent(this::removeRole);
        try {
            addRole(role);
        } catch (IdAlreadyInUseException e) {
            logger.debug("Attempted to replace a role but the original role instance was not properly deleted beforehand");
        }
        return this;
    }

    /**
     * Change the name of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleName  New role name
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setRoleName(RoleId roleId, Name roleName) {
        checkNotNull(roleId);
        checkNotNull(roleName);
        getRole(roleId).ifPresent(role -> setRole(roleId,
                factory.getRole(role.getId(), roleName, role.getDescription(), role.getOperations())));
        return this;
    }

    /**
     * Change the description of the given role to a new one
     *
     * @param roleId  Role identifier
     * @param roleDescription   New role description
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setRoleDescription(RoleId roleId, Description roleDescription) {
        checkNotNull(roleId);
        checkNotNull(roleDescription);
        getRole(roleId).ifPresent(role -> setRole(roleId,
                factory.getRole(role.getId(), role.getName(), roleDescription, role.getOperations())));
        return this;
    }

    /**
     * Add one or more operations to the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds   Operation identifier(s)
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder addOperationToRole(RoleId roleId, OperationId... operationIds) {
        checkNotNull(roleId);
        checkNotNull(operationIds);
        getRole(roleId).ifPresent(role -> {
            Set<OperationId> operations = new HashSet<>(role.getOperations());
            Collections.addAll(operations, operationIds);
            setRole(roleId, factory.getRole(role.getId(), role.getName(), role.getDescription(), operations));
        });
        return this;
    }

    /**
     * Remove one or more operations from the permitted operations of the given role
     *
     * @param roleId    Role identifier
     * @param operationIds   Operation identifier(s)
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder removeOperationFromRole(RoleId roleId, OperationId... operationIds) {
        checkNotNull(roleId);
        checkNotNull(operationIds);
        getRole(roleId).ifPresent(role -> {
            Set<OperationId> operations = new HashSet<>(role.getOperations());
            for (OperationId operationId : operationIds) {
                operations.remove(checkNotNull(operationId));
            }
            setRole(roleId, factory.getRole(role.getId(), role.getName(), role.getDescription(), operations));
        });
        return this;
    }

    private Optional<Role> getRole(RoleId roleId) {
        for(Role r : roles) {
            if(r.getId().equals(roleId)) {
                return Optional.of(r);
            }
        }
        return Optional.empty();
    }


    /* operations */

    /**
     * Add the given operation to the configuration
     *
     * @param operation New operation
     * @throws IdAlreadyInUseException  Operation identifier already in use
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder addOperation(Operation operation) throws IdAlreadyInUseException {
        checkNotNull(operation);
        if (contains(operation.getId(), operations)) {
            throw new IdAlreadyInUseException("The specified operation identifier is already used by another operation");
        }
        operations.add(operation);
        return this;
    }

    /**
     * Remove the given operation from the configuration
     *
     * @param operation Operation
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder removeOperation(Operation operation) {
        checkNotNull(operation);
        operations.remove(operation);
        return this;
    }

    /**
     * Modify the operation that has the specified identifier with the given operation
     *
     * @param operationId   Operation identifier
     * @param operation Operation
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setOperation(OperationId operationId, Operation operation) {
        checkNotNull(operationId);
        checkNotNull(operation);
        getOperation(operationId).ifPresent(this::removeOperation);
        try {
            addOperation(operation);
        } catch (IdAlreadyInUseException e) {
            logger.debug("Attempted to replace an operation but the original operation instance was not properly deleted beforehand");
        }
        return this;
    }

    /**
     * Change the name of the given operation
     *
     * @param operationId Operation identifier
     * @param operationName New operation name
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setOperationName(OperationId operationId, Name operationName) {
        checkNotNull(operationId);
        checkNotNull(operationName);
        getOperation(operationId).ifPresent(operation -> {
            if (!operation.isSystemOperation()) {
                setOperation(operationId, factory.getCustomOperation(operation.getId(), operationName, operation.getDescription(), operation.getType(), operation.getScope()));
            } else {
                logger.error("Cannot modify name of a system operation");
            }
        });
        return this;
    }

    /**
     * Change the description of a given operation
     *
     * @param operationId Operation identifier
     * @param operationDescription  New operation description
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder setOperationDescription(OperationId operationId, Description operationDescription) {
        checkNotNull(operationId);
        checkNotNull(operationDescription);
        getOperation(operationId).ifPresent(operation -> {
            if (!operation.isSystemOperation()) {
                setOperation(operationId, factory.getCustomOperation(operation.getId(), operation.getName(), operationDescription, operation.getType(), operation.getScope()));
            } else {
                logger.error("Cannot modify description of a system operation");
            }
        });
        return this;
    }

    private Optional<Operation> getOperation(OperationId operationId) {
        for(Operation o : operations) {
            if(o.getId().equals(operationId)) {
                return Optional.of(o);
            }
        }
        return Optional.empty();
    }


    /* policy */

    /**
     * Add a user to the access control policy with the specified role(s) in the given project
     *
     * @param userId    User identifier
     * @param projectId    Project identifier
     * @param roleIds    Role identifier(s)
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder addPolicy(UserId userId, ProjectId projectId, RoleId... roleIds) {
        checkNotNull(userId);
        checkNotNull(projectId);
        if (policyMap.containsKey(userId)) {
            // getProject {project -> roles} assignments for this user
            Map<ProjectId, Set<RoleId>> projectRoleMap = policyMap.get(userId);
            if (projectRoleMap.containsKey(projectId)) {
                Set<RoleId> roles = projectRoleMap.get(projectId);
                Collections.addAll(roles, roleIds);
                projectRoleMap.put(projectId, roles);
            } else {
                Set<RoleId> roles = new HashSet<>();
                Collections.addAll(roles, roleIds);
                projectRoleMap.put(projectId, roles);
            }
            policyMap.put(userId, projectRoleMap);
        } else {
            Set<RoleId> roles = new HashSet<>();
            Collections.addAll(roles, roleIds);
            Map<ProjectId, Set<RoleId>> map = new HashMap<>();
            map.put(projectId, roles);
            policyMap.put(userId, map);
        }
        return this;
    }

    /**
     * Remove a role from the specified user and project in the access control policy
     *
     * @param userId    User identifier
     * @param projectId    Project identifier
     * @param roleId    Role identifier
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder removePolicy(UserId userId, ProjectId projectId, RoleId roleId) {
        Map<ProjectId, Set<RoleId>> map = policyMap.get(userId);
        Set<RoleId> roles = map.get(projectId);
        roles.remove(roleId);
        if (roles.isEmpty()) {
            map.remove(projectId);
            if(map.isEmpty()) {
                policyMap.remove(userId);
            }
        } else {
            map.put(projectId, roles);
        }
        policyMap.put(userId, map);
        return this;
    }

    /**
     * Remove all role assignments on the specified project by the given user
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder removePolicy(UserId userId, ProjectId projectId) {
        Map<ProjectId,Set<RoleId>> roleAssignments = policyMap.get(userId);
        roleAssignments.remove(projectId);
        policyMap.put(userId, roleAssignments);
        return this;
    }

    /**
     * Remove all policy entries for the user with the given identifier
     *
     * @param userId User identifier
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder removePolicy(UserId userId) {
        policyMap.remove(userId);
        return this;
    }

    /**
     * Remove all users' role assignments involving the role with the specified identifier
     *
     * @param roleId    Role identifier
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder removePolicy(RoleId roleId) {
        Map<UserId, Map<ProjectId,Set<RoleId>>> toUpdate = new HashMap<>();
        for(UserId userId : policyMap.keySet()) {
            Map<ProjectId,Set<RoleId>> roleAssignments = policyMap.get(userId);
            for(ProjectId projectId : roleAssignments.keySet()) {
                if(roleAssignments.get(projectId).contains(roleId)) {
                    Set<RoleId> roleIds = roleAssignments.get(projectId);
                    roleIds.remove(roleId);
                    roleAssignments.put(projectId, roleIds);
                }
            }
            toUpdate.put(userId, roleAssignments);
        }
        policyMap.putAll(toUpdate);
        return this;
    }

    /**
     * Remove all users' role assignments to the project with the given identifier
     *
     * @param projectId Project identifier
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder removePolicy(ProjectId projectId) {
        Map<UserId, Map<ProjectId,Set<RoleId>>> toUpdate = new HashMap<>();
        for(UserId userId : policyMap.keySet()) {
            Map<ProjectId, Set<RoleId>> roleAssignments = policyMap.get(userId);
            roleAssignments.remove(projectId);
            toUpdate.put(userId, roleAssignments);
        }
        policyMap.putAll(toUpdate);
        return this;
    }

    /**
     * Remove the given operation from all the roles in the policy
     *
     * @param operationId   Operation identifier
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder removePolicy(OperationId operationId) {
        Set<RoleId> roleIds = new HashSet<>();
        for(Role r : roles) {
            if(r.getOperations().contains(operationId)) {
                roleIds.add(r.getId());
            }
        }
        for(RoleId roleId : roleIds) {
            removeOperationFromRole(roleId, operationId);
        }
        return this;
    }


    /* authentication */

    /**
     * Register a user's authentication details in the authentication registry
     *
     * @param userId  User identifier
     * @param password  Password
     * @throws IdAlreadyInUseException   User is already registered
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder registerUser(UserId userId, SaltedPasswordDigest password) throws IdAlreadyInUseException {
        checkNotNull(userId);
        checkNotNull(password);
        if(isRegistered(userId)) {
            throw new IdAlreadyInUseException("The specified user is already registered with the authentication manager. Recover or change the password.");
        }
        authDetails.add(factory.getAuthenticationDetails(userId, password));
        return this;
    }

    /**
     * Remove user from the authentication registry (the user will not be able to login)
     *
     * @param userId  User identifier
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder unregisterUser(UserId userId) {
        checkNotNull(userId);
        getAuthenticationDetails(userId).ifPresent(details -> authDetails.remove(details));
        return this;
    }

    /**
     * Change password of a specified user to the given password
     *
     * @param userId  User identifier
     * @param password  New password
     * @return ServerConfigurationBuilder
     */
    public ConfigurationBuilder changePassword(UserId userId, SaltedPasswordDigest password) {
        checkNotNull(userId);
        checkNotNull(password);
        unregisterUser(userId);
        authDetails.add(factory.getAuthenticationDetails(userId, password));
        return this;
    }

    private boolean isRegistered(UserId userId) {
        for(AuthenticationDetails details : authDetails) {
            if(details.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    private Optional<AuthenticationDetails> getAuthenticationDetails(UserId userId) {
        checkNotNull(userId);
        for(AuthenticationDetails detail : authDetails) {
            if(detail.getUserId().equals(userId)) {
                return Optional.of(detail);
            }
        }
        return Optional.empty();
    }

    private <E extends PolicyObjectId, T extends PolicyObject> boolean contains(E id, Set<T> elements) {
        checkNotNull(id);
        checkNotNull(elements);
        for(T element : elements) {
            if(element.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create a server configuration instance
     *
     * @return Server configuration
     */
    public ServerConfiguration createServerConfiguration() {
        return new ServerConfigurationImpl(host, root, policyMap, users, projects, roles, operations, authDetails, properties);
    }
}
