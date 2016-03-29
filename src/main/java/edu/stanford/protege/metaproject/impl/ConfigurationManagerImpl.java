package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.MetaprojectNotLoadedException;
import edu.stanford.protege.metaproject.api.exception.ObjectConversionException;
import edu.stanford.protege.metaproject.api.exception.ServerConfigurationNotLoadedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ConfigurationManagerImpl implements ConfigurationManager {
    private final Serializer serializer;
    private ServerConfiguration serverConfiguration;

    /**
     * Constructor
     *
     * @param serializer    Configuration serializer
     */
    public ConfigurationManagerImpl(Serializer serializer) {
        this.serializer = checkNotNull(serializer);
    }

    @Override
    public ServerConfiguration loadServerConfiguration(File f) throws FileNotFoundException, ObjectConversionException {
        checkNotNull(f, "Configuration file must not be null");
        serverConfiguration = checkNotNull((ServerConfiguration) serializer.parse(f, ServerConfiguration.class));
        return serverConfiguration;
    }

    @Override
    public ClientConfiguration loadClientConfiguration(File f) throws FileNotFoundException, ObjectConversionException {
        checkNotNull(f, "Configuration file must not be null");
        return (ClientConfiguration) serializer.parse(f, ClientConfiguration.class);
    }

    @Override
    public ServerConfiguration getServerConfiguration() throws ServerConfigurationNotLoadedException {
        if(serverConfiguration == null) {
            throw new ServerConfigurationNotLoadedException("No server configuration has been loaded.");
        }
        return serverConfiguration;
    }

    @Override
    public ClientConfiguration getClientConfiguration(UserId userId) throws MetaprojectNotLoadedException, ServerConfigurationNotLoadedException {
        Factory f = Manager.getFactory();
        Metaproject metaproject = serverConfiguration.getMetaproject();

        // Get policy for user
        Map<UserId,Map<ProjectId, Set<RoleId>>> userPolicyMap = new HashMap<>();
        userPolicyMap.put(userId, metaproject.getPolicy().getUserRoleMap(userId));
        Policy userPolicy = f.getPolicy(userPolicyMap);

        Metaproject userMetaproject = new MetaprojectBuilder()
                .setOperationRegistry(metaproject.getOperationRegistry())
                .setProjectRegistry(metaproject.getProjectRegistry())
                .setRoleRegistry(metaproject.getRoleRegistry())
                .setUserRegistry(metaproject.getUserRegistry())
                .setPolicy(userPolicy)
                .createMetaproject();

        return new ClientConfigurationBuilder()
                .setMetaproject(userMetaproject)
                .setGuiRestrictions(serverConfiguration.getUserGuiRestrictions().get(userId))
                .setProperties(serverConfiguration.getProperties())
                .createClientConfiguration();
    }
}
