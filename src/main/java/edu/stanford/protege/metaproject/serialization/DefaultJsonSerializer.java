package edu.stanford.protege.metaproject.serialization;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.ObjectConversionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class DefaultJsonSerializer implements Serializer<Gson> {
    private Gson gson;

    /**
     * Constructor
     */
    public DefaultJsonSerializer() { }

    /**
     * Get the Gson serializer instance
     *
     * @return Gson instance
     */
    @Override
    public Gson getInstance() {
        if(gson == null) {
            gson = new GsonBuilder()

                    // access control objects
                    .registerTypeAdapter(Operation.class, new OperationSerializer())
                    .registerTypeAdapter(Project.class, new ProjectSerializer())
                    .registerTypeAdapter(Role.class, new RoleSerializer())
                    .registerTypeAdapter(User.class, new UserSerializer())

                    // configurations
                    .registerTypeAdapter(ServerConfiguration.class, new ServerConfigurationSerializer())
                    .registerTypeAdapter(ClientConfiguration.class, new ClientConfigurationSerializer())
                    .registerTypeAdapter(Metaproject.class, new MetaprojectSerializer())
                    .registerTypeAdapter(Host.class, new HostSerializer())

                    // access control object managers
                    .registerTypeAdapter(Policy.class, new PolicyManagerSerializer())
                    .registerTypeAdapter(OperationRegistry.class, new OperationManagerSerializer())
                    .registerTypeAdapter(ProjectRegistry.class, new ProjectManagerSerializer())
                    .registerTypeAdapter(RoleRegistry.class, new RoleManagerSerializer())
                    .registerTypeAdapter(UserRegistry.class, new UserManagerSerializer())
                    .registerTypeAdapter(AuthenticationManager.class, new AuthenticationManagerSerializer())

                    // other objects
                    .registerTypeHierarchyAdapter(TextProperty.class, new PropertySerializer())
                    .registerTypeHierarchyAdapter(NumericProperty.class, new PropertySerializer())
                    .registerTypeAdapter(OperationRestriction.class, new OperationRestrictionSerializer())
                    .registerTypeAdapter(EntityIriStatus.class, new EntityIriStatusSerializer())
                    .registerTypeHierarchyAdapter(AuthenticationDetails.class, new AuthenticationDetailsSerializer())
                    .registerTypeAdapter(GuiRestriction.class, new GuiRestrictionSerializer())

                    .enableComplexMapKeySerialization()
                    .setPrettyPrinting()
                    .create();
        }
        return gson;
    }

    @Override
    public <T> T parse(File f, Class<T> cls) throws FileNotFoundException, JsonSyntaxException, JsonIOException, ObjectConversionException {
        Gson gson = getInstance();
        T obj;
        try {
            obj = gson.fromJson(new FileReader(f), cls);
        } catch(NullPointerException e) {
            throw new ObjectConversionException("The given JSON file could not be parsed. This is likely to happen if the JSON object in " +
                    "the file does not match the Java object structure required for instantiating the object.");
        }
        return obj;
    }

    public ServerConfiguration parseServerConfiguration(File f) throws FileNotFoundException, ObjectConversionException {
        return parse(f, ServerConfiguration.class);
    }

    public ClientConfiguration parseClientConfiguration(File f) throws FileNotFoundException, ObjectConversionException {
        return parse(f, ClientConfiguration.class);
    }

    public Metaproject parseMetaproject(File f) throws FileNotFoundException, ObjectConversionException {
        return parse(f, Metaproject.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultJsonSerializer that = (DefaultJsonSerializer) o;
        return Objects.equal(gson, that.gson);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gson);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("gson", gson)
                .toString();
    }
}
