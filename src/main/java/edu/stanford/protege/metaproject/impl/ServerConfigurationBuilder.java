package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.AuthenticationRegistry;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.Metaproject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builder for server configuration instances
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerConfigurationBuilder {
    private Host host = MetaprojectUtils.getServerHost();
    private File root = MetaprojectUtils.getServerRoot();
    private Metaproject metaproject = new MetaprojectBuilder().createMetaproject();
    private AuthenticationRegistry authenticationRegistry = Manager.getFactory().getAuthenticationRegistry();
    private Map<String,String> properties = new HashMap<>();

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
     * Set the metaproject definition
     *
     * @param metaproject   Metaproject
     * @return ServerConfigurationBuilder
     */
    public ServerConfigurationBuilder setMetaproject(Metaproject metaproject) {
        this.metaproject = metaproject;
        return this;
    }

    /**
     * Set the authentication registry
     *
     * @param authenticationRegistry    Authentication registry
     * @return ServerConfigurationBuilder
     */
    public ServerConfigurationBuilder setAuthenticationRegistry(AuthenticationRegistry authenticationRegistry) {
        this.authenticationRegistry = checkNotNull(authenticationRegistry);
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
    public ServerConfigurationImpl createServerConfiguration() {
        return new ServerConfigurationImpl(host, root, metaproject, authenticationRegistry, properties);
    }
}
