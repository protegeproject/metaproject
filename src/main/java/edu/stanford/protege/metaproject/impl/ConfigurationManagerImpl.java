package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.MetaprojectNotLoadedException;
import edu.stanford.protege.metaproject.api.exception.ObjectConversionException;
import edu.stanford.protege.metaproject.api.exception.ServerConfigurationNotLoadedException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

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
        checkNotNull(f, "Input configuration file must not be null");
        serverConfiguration = checkNotNull((ServerConfiguration) serializer.parse(f, ServerConfiguration.class));
        return serverConfiguration;
    }

    @Override
    public ServerConfiguration getServerConfiguration() throws ServerConfigurationNotLoadedException {
        return checkConfigurationIsSet(serverConfiguration);
    }

    @Override
    public void setServerConfiguration(ServerConfiguration serverConfiguration) {
        this.serverConfiguration = checkNotNull(serverConfiguration);
    }

    @Override
    public void saveServerConfiguration(File outputFile) throws IOException, ServerConfigurationNotLoadedException {
        checkNotNull(outputFile, "Output configuration file must not be null");
        checkConfigurationIsSet(serverConfiguration);
        FileWriter fw = new FileWriter(outputFile);
        String json = serializer.write(serverConfiguration, ServerConfiguration.class);
        fw.write(json);
        fw.close();
    }

    @Override
    public ClientConfiguration getClientConfiguration(UserId userId) throws MetaprojectNotLoadedException, ServerConfigurationNotLoadedException, UserNotInPolicyException {
        Metaproject metaproject = serverConfiguration.getMetaproject();
        Metaproject userMetaproject = new MetaprojectBuilder()
                .setOperationRegistry(metaproject.getOperationRegistry())
                .setProjectRegistry(metaproject.getProjectRegistry())
                .setRoleRegistry(metaproject.getRoleRegistry())
                .setUserRegistry(metaproject.getUserRegistry())
                .setPolicy(metaproject.getPolicy())
                .createMetaproject();

        return new ClientConfigurationBuilder()
                .setMetaproject(userMetaproject)
                .setProperties(serverConfiguration.getProperties())
                .createClientConfiguration();
    }

    private ServerConfiguration checkConfigurationIsSet(ServerConfiguration config) throws ServerConfigurationNotLoadedException {
        if(config == null) {
            throw new ServerConfigurationNotLoadedException("No server configuration has been loaded.");
        }
        return config;
    }
}
