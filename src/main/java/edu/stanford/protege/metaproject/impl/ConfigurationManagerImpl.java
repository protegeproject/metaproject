package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ConfigurationManagerImpl implements ConfigurationManager, Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManagerImpl.class.getName());
    private static final long serialVersionUID = -8224499729431946518L;
    private final Serializer<?> serializer;
    private final PolicyFactory factory;
    private ServerConfiguration serverConfiguration;

    /**
     * Constructor
     *
     * @param factory   Policy factory
     * @param serializer    Configuration serializer
     */
    public ConfigurationManagerImpl(PolicyFactory factory, Serializer<?> serializer) {
        this.factory = checkNotNull(factory);
        this.serializer = checkNotNull(serializer);
    }

    @Override
    public synchronized ServerConfiguration loadConfiguration(File f) throws FileNotFoundException, ObjectConversionException {
        checkNotNull(f, "Input configuration file must not be null");
        serverConfiguration = new ServerConfigurationBuilder(checkNotNull(serializer.parse(f, ServerConfiguration.class)))
                .setConfigurationManager(this)
                .createServerConfiguration();
        return serverConfiguration;
    }

    @Override
    public synchronized ServerConfiguration loadConfiguration(Reader reader) throws ObjectConversionException {
        checkNotNull(reader, "Input reader must not be null");
        serverConfiguration = new ServerConfigurationBuilder(checkNotNull(serializer.parse(reader, ServerConfiguration.class)))
                .setConfigurationManager(this)
                .createServerConfiguration();
        return serverConfiguration;
    }

    @Override
    public synchronized ServerConfiguration getConfiguration() throws ServerConfigurationNotLoadedException {
        return checkConfigurationIsSet(serverConfiguration);
    }

    @Override
    public void setActiveConfiguration(ServerConfiguration configuration) {
        serverConfiguration = new ServerConfigurationBuilder(checkNotNull(configuration))
                .setConfigurationManager(this)
                .createServerConfiguration();
    }

    @Override
    public synchronized void saveConfiguration(File outputFile) throws IOException {
        checkNotNull(outputFile, "Output configuration file must not be null");
        checkNotNull(serverConfiguration, "Server configuration must not be null");
        FileWriter fw = new FileWriter(outputFile);
        String json = serializer.write(serverConfiguration, ServerConfiguration.class);
        fw.write(json);
        fw.close();
    }

    @Override
    public synchronized PolicyFactory getPolicyFactory() {
        return factory;
    }

    private synchronized <E extends PolicyObjectId, T extends PolicyObject> boolean contains(E id, Set<T> elements) {
        checkNotNull(id);
        for(T element : elements) {
            if(element.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private ServerConfiguration checkConfigurationIsSet(ServerConfiguration config) throws ServerConfigurationNotLoadedException {
        if(config == null) {
            throw new ServerConfigurationNotLoadedException("No server configuration has been loaded.");
        }
        return config;
    }



    /* server settings */

    @Override
    public synchronized void setHost(Host host) {
        checkNotNull(host);
        this.serverConfiguration = new ServerConfigurationBuilder(serverConfiguration).setHost(host).createServerConfiguration();
    }

    @Override
    public synchronized void setServerRoot(File root) {
        checkNotNull(root);
        this.serverConfiguration = new ServerConfigurationBuilder(serverConfiguration).setServerRoot(root).createServerConfiguration();
    }

    @Override
    public synchronized void setPropertyMap(Map<String,String> propertyMap) {
        checkNotNull(propertyMap);
        this.serverConfiguration = new ServerConfigurationBuilder(serverConfiguration).setPropertyMap(propertyMap).createServerConfiguration();
    }

    @Override
    public void addProperty(String key, String value) {
        checkNotNull(key);
        checkNotNull(value);
        Map<String,String> map = new HashMap<>(serverConfiguration.getProperties());
        map.put(key, value);
        setPropertyMap(map);
    }

    @Override
    public void removeProperty(String key) {
        checkNotNull(key);
        Map<String,String> map = new HashMap<>(serverConfiguration.getProperties());
        map.remove(key);
        setPropertyMap(map);
    }



    /* users */

    @Override
    public synchronized void addUser(User user) throws IdAlreadyInUseException {
        checkNotNull(user);
        Set<User> users = new HashSet<>(serverConfiguration.getUsers());
        if (contains(user.getId(), users)) {
            throw new IdAlreadyInUseException("The specified user identifier is already used by another user");
        }
        users.add(user);
        setUsers(users);
    }

    @Override
    public synchronized void removeUser(User user) {
        checkNotNull(user);
        Set<User> users = new HashSet<>(serverConfiguration.getUsers());
        users.remove(user);
        setUsers(users);
    }

    @Override
    public synchronized void setUser(UserId userId, User user) throws UnknownUserIdException {
        checkNotNull(userId);
        checkNotNull(user);
        removeUser(serverConfiguration.getUser(userId));
        try {
            addUser(user);
        } catch (IdAlreadyInUseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void setUsers(Set<User> users) {
        checkNotNull(users);
        this.serverConfiguration = new ServerConfigurationBuilder(serverConfiguration).setUsers(users).createServerConfiguration();
    }

    @Override
    public synchronized void setUserName(UserId userId, Name userName) throws UnknownUserIdException {
        checkNotNull(userId);
        checkNotNull(userName);
        User user = serverConfiguration.getUser(userId);
        User newUser = factory.getUser(userId, userName, user.getEmailAddress());
        setUser(userId, newUser);
    }

    @Override
    public synchronized void setUserEmailAddress(UserId userId, EmailAddress emailAddress) throws UnknownUserIdException {
        checkNotNull(emailAddress);
        User user = serverConfiguration.getUser(userId);
        User newUser = factory.getUser(userId, user.getName(), emailAddress);
        setUser(userId, newUser);
    }



    /* projects */

    @Override
    public synchronized void addProject(Project project) throws IdAlreadyInUseException {
        checkNotNull(project);
        Set<Project> projects = new HashSet<>(serverConfiguration.getProjects());
        if (contains(project.getId(), projects)) {
            throw new IdAlreadyInUseException("The specified project identifier is already used by another project");
        }
        projects.add(project);
        setProjects(projects);
    }

    @Override
    public synchronized void removeProject(Project project) {
        checkNotNull(project);
        Set<Project> projects = new HashSet<>(serverConfiguration.getProjects());
        projects.remove(project);
        setProjects(projects);
    }

    @Override
    public synchronized void setProject(ProjectId projectId, Project project) throws UnknownProjectIdException {
        removeProject(serverConfiguration.getProject(projectId));
        try {
            addProject(project);
        } catch (IdAlreadyInUseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void setProjects(Set<Project> projects) {
        checkNotNull(projects);
        this.serverConfiguration = new ServerConfigurationBuilder(serverConfiguration).setProjects(projects).createServerConfiguration();
    }

    @Override
    public synchronized void setProjectName(ProjectId projectId, Name projectName) throws UnknownProjectIdException {
        checkNotNull(projectId);
        checkNotNull(projectName);
        Project project = serverConfiguration.getProject(projectId);
        Project newProject = factory.getProject(project.getId(), projectName, project.getDescription(), project.getFile(),
                project.getOwner(), project.getOptions());
        setProject(projectId, newProject);
    }

    @Override
    public synchronized void setProjectDescription(ProjectId projectId, Description projectDescription) throws UnknownProjectIdException {
        checkNotNull(projectDescription);
        Project project = serverConfiguration.getProject(projectId);
        Project newProject = factory.getProject(project.getId(), project.getName(), projectDescription, project.getFile(),
                project.getOwner(), project.getOptions());
        setProject(projectId, newProject);
    }

    @Override
    public synchronized void setProjectOwner(ProjectId projectId, UserId userId) throws UnknownProjectIdException {
        checkNotNull(userId);
        Project project = serverConfiguration.getProject(projectId);
        Project newProject = factory.getProject(project.getId(), project.getName(), project.getDescription(), project.getFile(),
                userId, project.getOptions());
        setProject(projectId, newProject);
    }

    @Override
    public synchronized void setProjectFile(ProjectId projectId, File file) throws UnknownProjectIdException {
        checkNotNull(file);
        Project project = serverConfiguration.getProject(projectId);
        Project newProject = factory.getProject(project.getId(), project.getName(), project.getDescription(), file,
                project.getOwner(), project.getOptions());
        setProject(projectId, newProject);
    }

    @Override
    public synchronized void setProjectOptions(ProjectId projectId, ProjectOptions projectOptions) throws UnknownProjectIdException {
        checkNotNull(projectId);
        checkNotNull(projectOptions);
        Project project = serverConfiguration.getProject(projectId);
        Project newProject = factory.getProject(projectId, project.getName(), project.getDescription(), project.getFile(),
                project.getOwner(), Optional.of(projectOptions));
        setProject(projectId, newProject);
    }



    /* roles */

    @Override
    public synchronized void addRole(Role role) throws IdAlreadyInUseException {
        checkNotNull(role);
        Set<Role> roles = new HashSet<>(serverConfiguration.getRoles());
        if (contains(role.getId(), roles)) {
            throw new IdAlreadyInUseException("The specified role identifier is already used in another role");
        }
        roles.add(role);
        setRoles(roles);
    }

    @Override
    public synchronized void removeRole(Role role) {
        checkNotNull(role);
        Set<Role> roles = new HashSet<>(serverConfiguration.getRoles());
        roles.remove(role);
        setRoles(roles);
    }

    @Override
    public synchronized void setRole(RoleId roleId, Role role) throws UnknownRoleIdException {
        checkNotNull(roleId);
        checkNotNull(role);
        removeRole(serverConfiguration.getRole(roleId));
        try {
            addRole(role);
        } catch (IdAlreadyInUseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void setRoles(Set<Role> roles) {
        checkNotNull(roles);
        this.serverConfiguration = new ServerConfigurationBuilder(serverConfiguration).setRoles(roles).createServerConfiguration();
    }

    @Override
    public synchronized void setRoleName(RoleId roleId, Name roleName) throws UnknownRoleIdException {
        checkNotNull(roleName);
        Role role = serverConfiguration.getRole(roleId);
        Role newRole = factory.getRole(role.getId(), roleName, role.getDescription(), role.getOperations());
        setRole(roleId, newRole);
    }

    @Override
    public synchronized void setRoleDescription(RoleId roleId, Description roleDescription) throws UnknownRoleIdException {
        checkNotNull(roleDescription);
        Role role = serverConfiguration.getRole(roleId);
        Role newRole = factory.getRole(role.getId(), role.getName(), roleDescription, role.getOperations());
        setRole(roleId, newRole);
    }

    @Override
    public synchronized void addOperationToRole(RoleId roleId, OperationId... operationIds) throws UnknownRoleIdException {
        checkNotNull(operationIds);
        Role role = serverConfiguration.getRole(roleId);

        Set<OperationId> operations = new HashSet<>(role.getOperations());
        Collections.addAll(operations, operationIds);

        Role newRole = factory.getRole(role.getId(), role.getName(), role.getDescription(), operations);
        setRole(roleId, newRole);
    }

    @Override
    public synchronized void removeOperationFromRole(RoleId roleId, OperationId... operationIds) throws UnknownRoleIdException {
        checkNotNull(operationIds);
        Role role = serverConfiguration.getRole(roleId);

        Set<OperationId> operations = new HashSet<>(role.getOperations());
        for(OperationId operationId : operationIds) {
            operations.remove(checkNotNull(operationId));
        }

        Role newRole = factory.getRole(role.getId(), role.getName(), role.getDescription(), operations);
        setRole(roleId, newRole);
    }



    /* operations */

    @Override
    public synchronized void addOperation(Operation operation) throws IdAlreadyInUseException {
        checkNotNull(operation);
        Set<Operation> operations = new HashSet<>(serverConfiguration.getOperations());
        if (contains(operation.getId(), operations)) {
            throw new IdAlreadyInUseException("The specified operation identifier is already used by another operation");
        }
        operations.add(operation);
        setOperations(operations);
    }

    @Override
    public synchronized void removeOperation(Operation operation) {
        checkNotNull(operation);
        Set<Operation> operations = new HashSet<>(serverConfiguration.getOperations());
        operations.remove(operation);
        setOperations(operations);
    }

    @Override
    public synchronized void setOperation(OperationId operationId, Operation operation) throws UnknownOperationIdException {
        checkNotNull(operationId);
        checkNotNull(operation);
        removeOperation(serverConfiguration.getOperation(operationId));
        try {
            addOperation(operation);
        } catch (IdAlreadyInUseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void setOperations(Set<Operation> operations) {
        checkNotNull(operations);
        this.serverConfiguration = new ServerConfigurationBuilder(serverConfiguration).setOperations(operations).createServerConfiguration();
    }

    @Override
    public synchronized void setOperationName(OperationId operationId, Name operationName) throws UnknownOperationIdException {
        checkNotNull(operationId);
        checkNotNull(operationName);
        Operation operation = serverConfiguration.getOperation(operationId);
        if(!operation.isSystemOperation()) {
            Operation newOperation = factory.getCustomOperation(operation.getId(), operationName, operation.getDescription(), operation.getType(), operation.getScope());
            setOperation(operationId, newOperation);
        } else {
            logger.error("Cannot modify name of a system operation");
        }
    }

    @Override
    public synchronized void setOperationDescription(OperationId operationId, Description operationDescription) throws UnknownOperationIdException {
        checkNotNull(operationId);
        checkNotNull(operationDescription);
        Operation operation = serverConfiguration.getOperation(operationId);
        if(!operation.isSystemOperation()) {
            Operation newOperation = factory.getCustomOperation(operation.getId(), operation.getName(), operationDescription, operation.getType(), operation.getScope());
            setOperation(operationId, newOperation);
        } else {
            logger.error("Cannot modify description of a system operation");
        }
    }



    /* policy */

    @Override
    public synchronized void addPolicy(UserId userId, ProjectId projectId, RoleId... roleIds) {
        Map<UserId,Map<ProjectId,Set<RoleId>>> policyMap = new HashMap<>(serverConfiguration.getPolicyMap());
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
        setPolicy(policyMap);
    }

    @Override
    public synchronized void addPolicy(RoleId roleId, ProjectId projectId, UserId... userIds) {
        for (UserId userId : userIds) {
            addPolicy(userId, projectId, roleId);
        }
    }

    @Override
    public synchronized void removePolicy(UserId userId, ProjectId projectId, RoleId roleId) {
        Map<UserId,Map<ProjectId,Set<RoleId>>> policyMap = new HashMap<>(serverConfiguration.getPolicyMap());
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
        setPolicy(policyMap);
    }

    @Override
    public synchronized void removePolicy(UserId userId, ProjectId projectId) {
        Map<UserId,Map<ProjectId,Set<RoleId>>> policyMap = new HashMap<>(serverConfiguration.getPolicyMap());
        Map<ProjectId,Set<RoleId>> roleAssignments = policyMap.get(userId);
        roleAssignments.remove(projectId);
        policyMap.put(userId, roleAssignments);
        setPolicy(policyMap);
    }

    @Override
    public synchronized void removePolicy(UserId userId) {
        Map<UserId,Map<ProjectId,Set<RoleId>>> policyMap = new HashMap<>(serverConfiguration.getPolicyMap());
        policyMap.remove(userId);
        setPolicy(policyMap);
    }

    @Override
    public synchronized void removePolicy(RoleId roleId) {
        Map<UserId,Map<ProjectId,Set<RoleId>>> policyMap = new HashMap<>(serverConfiguration.getPolicyMap());
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
        setPolicy(policyMap);
    }

    @Override
    public void removePolicy(ProjectId projectId) {
        Map<UserId,Map<ProjectId,Set<RoleId>>> policyMap = new HashMap<>(serverConfiguration.getPolicyMap());
        Map<UserId, Map<ProjectId,Set<RoleId>>> toUpdate = new HashMap<>();
        for(UserId userId : policyMap.keySet()) {
            Map<ProjectId, Set<RoleId>> roleAssignments = policyMap.get(userId);
            roleAssignments.remove(projectId);
            toUpdate.put(userId, roleAssignments);
        }
        policyMap.putAll(toUpdate);
        setPolicy(policyMap);
    }

    @Override
    public synchronized void setPolicy(Map<UserId, Map<ProjectId, Set<RoleId>>> policyMap) {
        checkNotNull(policyMap);
        this.serverConfiguration = new ServerConfigurationBuilder(serverConfiguration).setPolicyMap(policyMap).createServerConfiguration();
    }



    /* authentication */

    @Override
    public void registerUser(UserId userId, SaltedPasswordDigest password) throws IdAlreadyInUseException {
        checkNotNull(userId);
        checkNotNull(password);
        if(serverConfiguration.containsUser(userId)) {
            throw new IdAlreadyInUseException("The specified user is already registered with the authentication manager. Recover or change the password.");
        }
        Set<AuthenticationDetails> authDetails = new HashSet<>(serverConfiguration.getAuthenticationDetails());
        authDetails.add(factory.getAuthenticationDetails(userId, password));
        setAuthenticationDetails(authDetails);
    }

    @Override
    public void unregisterUser(UserId userId) throws UserNotRegisteredException {
        AuthenticationDetails toDelete = serverConfiguration.getAuthenticationDetails(userId);
        Set<AuthenticationDetails> authDetails = new HashSet<>(serverConfiguration.getAuthenticationDetails());
        authDetails.remove(toDelete);
    }

    @Override
    public void changePassword(UserId userId, SaltedPasswordDigest password) throws UserNotRegisteredException {
        Set<AuthenticationDetails> authDetails = new HashSet<>(serverConfiguration.getAuthenticationDetails());
        authDetails.remove(serverConfiguration.getAuthenticationDetails(userId));
        AuthenticationDetails newUserDetails = factory.getAuthenticationDetails(userId, password);
        authDetails.add(newUserDetails);
        setAuthenticationDetails(authDetails);
    }

    @Override
    public synchronized void setAuthenticationDetails(Set<AuthenticationDetails> authDetails) {
        checkNotNull(authDetails);
        this.serverConfiguration = new ServerConfigurationBuilder(serverConfiguration).setAuthenticationDetails(authDetails).createServerConfiguration();
    }
}
