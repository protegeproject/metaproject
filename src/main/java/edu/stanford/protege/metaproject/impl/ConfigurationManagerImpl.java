package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
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
        Factory f = Manager.getFactory();
        Metaproject metaproject = serverConfiguration.getMetaproject();

        // Get policy for user
        Map<UserId,Map<ProjectId, Set<RoleId>>> userPolicyMap = new HashMap<>();
        userPolicyMap.put(userId, metaproject.getPolicy().getUserRoleMap(userId));
        Policy userPolicy = f.getPolicy(userPolicyMap);

        ProjectRegistry projectRegistry = getProjectRegistry(metaproject, userId, f);

        Metaproject userMetaproject = new MetaprojectBuilder()
                .setOperationRegistry(metaproject.getOperationRegistry())
                .setProjectRegistry(projectRegistry)
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

    private ProjectRegistry getProjectRegistry(Metaproject metaproject, UserId userId, Factory f) throws UserNotInPolicyException {
        Set<ProjectId> projects = metaproject.getPolicy().getProjects(userId);
        Set<Project> projectSet = new HashSet<>();
        for(ProjectId p : projects) {
            try {
                projectSet.add(metaproject.getProjectRegistry().getProject(p));
            } catch (UnknownProjectIdException e) { /* no-op */ }
        }
        return f.getProjectRegistry(projectSet);
    }

    private ServerConfiguration checkConfigurationIsSet(ServerConfiguration config) throws ServerConfigurationNotLoadedException {
        if(config == null) {
            throw new ServerConfigurationNotLoadedException("No server configuration has been loaded.");
        }
        return config;
    }
}
