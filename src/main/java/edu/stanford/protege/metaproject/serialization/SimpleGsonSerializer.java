package edu.stanford.protege.metaproject.serialization;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.impl.*;

import java.util.Collection;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class SimpleGsonSerializer {

    private final Gson gson = new GsonBuilder()
            .enableComplexMapKeySerialization()

            // collections
            .registerTypeAdapter(Collection.class, new CollectionSerializer())
            .registerTypeAdapter(ImmutableSet.class, new ImmutableSetSerializer())

            // access control objects
            .registerTypeAdapter(Operation.class, new OperationSerializer())
            .registerTypeAdapter(Project.class, new ProjectSerializer())
            .registerTypeAdapter(Role.class, new RoleSerializer())
            .registerTypeAdapter(User.class, new UserSerializer())

            // server configuration
            .registerTypeAdapter(ServerConfiguration.class, new ServerConfigurationSerializer())
            .registerTypeAdapter(Policy.class, new AccessControlPolicySerializer())
            .registerTypeAdapter(Host.class, new HostSerializer())

            // access control object managers
            .registerTypeAdapter(OperationManagerImpl.class, new OperationManagerSerializer())
            .registerTypeAdapter(ProjectManagerImpl.class, new ProjectManagerSerializer())
            .registerTypeAdapter(RoleManagerImpl.class, new RoleManagerSerializer())
            .registerTypeAdapter(UserManagerImpl.class, new UserManagerSerializer())
            .registerTypeAdapter(AuthenticationManagerImpl.class, new AuthenticationManagerSerializer())

            // properties and lower level objects
            .registerTypeHierarchyAdapter(StringProperty.class, new StringPropertySerializer())
            .registerTypeAdapter(OperationPrerequisite.class, new OperationPrerequisiteSerializer())
            .registerTypeAdapter(OntologyTermIdStatus.class, new OntologyTermIdStatusSerializer())

            .setPrettyPrinting()
            .create();

    /**
     * Constructor
     */
    public SimpleGsonSerializer() { }

    /**
     * Get the Gson instance
     *
     * @return Gson instance
     */
    public Gson get() {
        return gson;
    }
}
