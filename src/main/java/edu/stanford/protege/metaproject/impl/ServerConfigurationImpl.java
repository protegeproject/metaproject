package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.AuthenticationRegistry;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.Metaproject;
import edu.stanford.protege.metaproject.api.ServerConfiguration;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerConfigurationImpl implements ServerConfiguration, Serializable {
    private static final long serialVersionUID = -790337608616699925L;
    private final Metaproject metaproject;
    private final AuthenticationRegistry authenticationRegistry;
    private Map<String,String> properties;
    private Host host;
    private File root;

    /**
     * Package-private constructor; use {@link ServerConfigurationBuilder}
     *
     * @param host    Host
     * @param root  Root directory of the server
     * @param metaproject    Metaproject
     * @param authenticationRegistry Authentication manager
     * @param properties   Map of custom configuration properties
     */
    ServerConfigurationImpl(Host host, File root, Metaproject metaproject, AuthenticationRegistry authenticationRegistry, Map<String,String> properties) {
        this.host = checkNotNull(host);
        this.root = checkNotNull(root);
        this.metaproject = checkNotNull(metaproject);
        this.authenticationRegistry = checkNotNull(authenticationRegistry);
        this.properties = checkNotNull(properties);
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
    public Metaproject getMetaproject() {
        return metaproject;
    }

    @Override
    public AuthenticationRegistry getAuthenticationRegistry() {
        return authenticationRegistry;
    }

    @Override
    public void setHost(Host host) {
        this.host = checkNotNull(host);
    }

    @Override
    public void setServerRoot(File root) {
        this.root = checkNotNull(root);
    }

    @Override
    public Map<String,String> getProperties() {
        return properties;
    }

    @Override
    public void addProperty(String key, String value) {
        properties.put(checkNotNull(key), checkNotNull(value));
    }

    @Override
    public void removeProperty(String key) {
        properties.remove(checkNotNull(key));
    }

    @Override
    public String getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServerConfigurationImpl)) {
            return false;
        }
        ServerConfigurationImpl that = (ServerConfigurationImpl) o;
        return Objects.equal(metaproject, that.metaproject) &&
                Objects.equal(authenticationRegistry, that.authenticationRegistry) &&
                Objects.equal(properties, that.properties) &&
                Objects.equal(host, that.host) &&
                Objects.equal(root, that.root);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(metaproject, authenticationRegistry, properties, host, root);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("host", host)
                .add("root", root)
                .add("metaproject", metaproject)
                .add("authenticationRegistry", authenticationRegistry)
                .add("properties", properties)
                .toString();
    }
}