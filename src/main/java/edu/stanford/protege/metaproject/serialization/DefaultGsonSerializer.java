package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.stanford.protege.metaproject.api.*;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class DefaultGsonSerializer {

    /**
     * Constructor
     */
    public DefaultGsonSerializer() { }

    /**
     * Get the Gson serializer instance
     *
     * @return Gson instance
     */
    public Gson getDefaultSerializer() {
        return new GsonBuilder()

                // access control objects
                .registerTypeAdapter(Operation.class, new OperationSerializer())
                .registerTypeAdapter(Project.class, new ProjectSerializer())
                .registerTypeAdapter(Role.class, new RoleSerializer())
                .registerTypeAdapter(User.class, new UserSerializer())

                // configurations
                .registerTypeAdapter(ServerConfiguration.class, new ServerConfigurationSerializer())
                .registerTypeAdapter(ClientConfiguration.class, new ClientConfigurationSerializer())
                .registerTypeAdapter(AccessControlPolicy.class, new AccessControlPolicySerializer())
                .registerTypeAdapter(Host.class, new HostSerializer())

                // access control object managers
                .registerTypeAdapter(PolicyManager.class, new PolicyManagerSerializer())
                .registerTypeAdapter(OperationManager.class, new OperationManagerSerializer())
                .registerTypeAdapter(ProjectManager.class, new ProjectManagerSerializer())
                .registerTypeAdapter(RoleManager.class, new RoleManagerSerializer())
                .registerTypeAdapter(UserManager.class, new UserManagerSerializer())
                .registerTypeAdapter(AuthenticationManager.class, new AuthenticationManagerSerializer())

                // other objects
                .registerTypeHierarchyAdapter(Property.class, new PropertySerializer())
                .registerTypeAdapter(OperationPrerequisite.class, new OperationPrerequisiteSerializer())
                .registerTypeAdapter(EntityIriStatus.class, new EntityIriStatusSerializer())
                .registerTypeHierarchyAdapter(AuthenticationDetails.class, new AuthenticationDetailsSerializer())
                .registerTypeAdapter(GuiRestriction.class, new GuiRestrictionSerializer())

                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .create();
    }
}