package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builder for server configuration instances
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerConfigurationBuilder {
    private Factory f = Manager.getFactory();
    private Host host = f.getHost(f.getUri("rmi-owl2-server://localhost:5100"), Optional.of(f.getPort(5200)));
    private File root = new File("target/server-distribution/server/root");
    private Metaproject metaproject = new MetaprojectBuilder().createMetaproject();
    private AuthenticationRegistry authenticationRegistry = Manager.getFactory().getAuthenticationRegistry();
    private Map<String,String> properties = new HashMap<>();
    private Map<UserId, Set<GuiRestriction>> userGuiRestrictions = new HashMap<>();

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
     * Set the map of users to their GUI restrictions
     *
     * @param userGuiRestrictions   Map of user identifiers to sets of GUI restrictions
     * @return ServerConfigurationBuilder
     */
    public ServerConfigurationBuilder setUserGuiRestrictions(Map<UserId, Set<GuiRestriction>> userGuiRestrictions) {
        this.userGuiRestrictions = checkNotNull(userGuiRestrictions);
        return this;
    }

    /**
     * Create a server configuration instance
     *
     * @return Server configuration
     */
    public ServerConfigurationImpl createServerConfiguration() {
        return new ServerConfigurationImpl(host, root, metaproject, authenticationRegistry, properties, userGuiRestrictions);
    }
}
