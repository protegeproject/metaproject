package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.IdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerConfigurationImpl implements ServerConfiguration, Serializable {
    private static final long serialVersionUID = -1381462891599550914L;
    private final Host host;
    private final File root;
    private final Metaproject metaproject;
    private final AuthenticationRegistry authenticationRegistry;
    private Map<String,String> properties;

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

    public AuthenticationRegistry getAuthenticationRegistry() {
        return authenticationRegistry;
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
    public void enableGuestUser(boolean enableGuestUser) throws IdAlreadyInUseException {
        UserRegistry userRegistry = metaproject.getUserRegistry();
        User guestUser = userRegistry.getGuestUser();
        if(enableGuestUser && !authenticationRegistry.contains(guestUser.getId())) {
            final String guestPassword = "guest";
            final MetaprojectFactory f = Manager.getFactory();
            PasswordHasher hasher = f.getPasswordHasher();
            SaltGenerator saltGenerator = f.getSaltGenerator();
            SaltedPasswordDigest passwordDigest = hasher.hash(f.getPlainPassword(guestPassword), saltGenerator.generate());
            authenticationRegistry.add(guestUser.getId(), passwordDigest);
            userRegistry.add(guestUser);
        }
        else if(!enableGuestUser) {
            userRegistry.remove(guestUser);
            try {
                authenticationRegistry.remove(guestUser.getId());
            } catch (UserNotRegisteredException e) { /* no-op */ }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerConfigurationImpl that = (ServerConfigurationImpl) o;
        return Objects.equal(host, that.host) &&
                Objects.equal(root, that.root) &&
                Objects.equal(metaproject, that.metaproject) &&
                Objects.equal(authenticationRegistry, that.authenticationRegistry) &&
                Objects.equal(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(host, root, metaproject, authenticationRegistry, properties);
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