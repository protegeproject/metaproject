package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builder for server configuration instances
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ServerConfigurationBuilder {
    private ConfigurationManager configurationManager = Manager.getConfigurationManager();
    private Host host = ConfigurationUtils.getServerHost();
    private File root = ConfigurationUtils.getServerRoot();
    private Map<UserId, Map<ProjectId, Set<RoleId>>> policyMap = ConfigurationUtils.getDefaultPolicy();
    private Set<Role> roles = ConfigurationUtils.getDefaultRoles();
    private Set<Operation> operations = ConfigurationUtils.getDefaultOperations();
    private Set<User> users = ConfigurationUtils.getDefaultUsers();
    private Set<Project> projects = ConfigurationUtils.getDefaultProjects();
    private Set<AuthenticationDetails> authDetails = ConfigurationUtils.getDefaultAuthenticationDetails();
    private Map<String,String> properties = new HashMap<>();

    /**
     * No-arguments constructor
     */
    public ServerConfigurationBuilder() { }

    /**
     * Constructor that reuses the given server configuration
     *
     * @param config   Server configuration
     */
    public ServerConfigurationBuilder(ServerConfiguration config) {
        this.configurationManager = checkNotNull(config.getConfigurationManager());
        this.host = checkNotNull(config.getHost());
        this.root = checkNotNull(config.getServerRoot());
        this.policyMap = checkNotNull(config.getPolicyMap());
        this.roles = checkNotNull(config.getRoles());
        this.operations = checkNotNull(config.getOperations());
        this.users = checkNotNull(config.getUsers());
        this.projects = checkNotNull(config.getProjects());
        this.authDetails = checkNotNull(config.getAuthenticationDetails());
        this.properties = checkNotNull(config.getProperties());
    }

    /**
     * Set the server configuration manager
     *
     * @param configurationManager  Configuration manager
     * @return ServerConfigurationBuilder
     */
    public ServerConfigurationBuilder setConfigurationManager(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
        return this;
    }

    /**
     * Set the server host information
     *
     * @param host  Host
     * @return ServerConfigurationBuilder
     */
    public ServerConfigurationBuilder setHost(Host host) {
        this.host = host;
        return this;
    }

    /**
     * Set the root directory of the server
     *
     * @param root  Root directory of the server
     * @return ServerConfigurationBuilder
     */
    public ServerConfigurationBuilder setServerRoot(File root) {
        this.root = root;
        return this;
    }

    /**
     * Set the metaproject policy map, defining what users have what roles in which projects
     *
     * @param policyMap    Policy
     * @return ServerConfigurationBuilder
     */
    public ServerConfigurationBuilder setPolicyMap(Map<UserId, Map<ProjectId, Set<RoleId>>> policyMap) {
        this.policyMap = policyMap;
        return this;
    }

    /**
     * Set the collection of roles
     *
     * @param roles  Roles
     * @return ServerConfigurationBuilder
     */
    public ServerConfigurationBuilder setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    /**
     * Set the collection of operations
     *
     * @param operations Operations
     * @return ServerConfigurationBuilder
     */
    public ServerConfigurationBuilder setOperations(Set<Operation> operations) {
        this.operations = operations;
        return this;
    }

    /**
     * Set the collection of users
     *
     * @param users  Users
     * @return ServerConfigurationBuilder
     */
    public ServerConfigurationBuilder setUsers(Set<User> users) {
        this.users = users;
        return this;
    }

    /**
     * Set the collection of projects
     *
     * @param projects    Projects
     * @return ServerConfigurationBuilder
     */
    public ServerConfigurationBuilder setProjects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    /**
     * Set the authentication details
     *
     * @param authDetails   Set of authentication details
     * @return ServerConfigurationBuilder
     */
    public ServerConfigurationBuilder setAuthenticationDetails(Set<AuthenticationDetails> authDetails) {
        this.authDetails = authDetails;
        return this;
    }

    /**
     * Set the map of custom server configuration properties
     *
     * @param propertyMap   Custom properties map
     * @return ServerConfigurationBuilder
     */
    public ServerConfigurationBuilder setPropertyMap(Map<String,String> propertyMap) {
        this.properties = checkNotNull(propertyMap);
        return this;
    }

    /**
     * Create a server configuration instance
     *
     * @return Server configuration
     */
    public ServerConfiguration createServerConfiguration() {
        return new ServerConfigurationImpl(configurationManager, host, root, policyMap, users, projects, roles, operations, authDetails, properties);
    }
}
