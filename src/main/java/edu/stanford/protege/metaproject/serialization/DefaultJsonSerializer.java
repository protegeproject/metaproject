package edu.stanford.protege.metaproject.serialization;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.ObjectConversionException;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public final class DefaultJsonSerializer implements Serializer {
    @Nonnull private final Gson gson;

    /**
     * No-args constructor
     */
    public DefaultJsonSerializer() {
        this.gson = getGson();
    }

    /**
     * Get the Gson instance
     *
     * @return Gson instance
     */
    @Nonnull
    public Gson getGson() {
        return new GsonBuilder()
                // access control objects
                .registerTypeAdapter(Operation.class, new OperationSerializer())
                .registerTypeAdapter(Project.class, new ProjectSerializer())
                .registerTypeAdapter(Role.class, new RoleSerializer())
                .registerTypeAdapter(User.class, new UserSerializer())

                // configurations
                .registerTypeAdapter(ServerConfiguration.class, new ServerConfigurationSerializer())
                .registerTypeAdapter(Host.class, new HostSerializer())

                // other objects
                .registerTypeHierarchyAdapter(TextProperty.class, new PropertySerializer())
                .registerTypeHierarchyAdapter(NumericProperty.class, new PropertySerializer())
                .registerTypeHierarchyAdapter(AuthenticationDetails.class, new AuthenticationDetailsSerializer())
                .registerTypeAdapter(ProjectOptions.class, new ProjectOptionsSerializer())

                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .create();
    }

    @Override
    @Nonnull
    public <T> T parse(@Nonnull Reader reader, @Nonnull Class<T> cls) throws ObjectConversionException {
        checkNotNull(reader);
        checkNotNull(cls);
        Gson gson = getGson();
        T obj;
        try {
            obj = gson.fromJson(reader, cls);
        } catch(JsonSyntaxException | JsonIOException e) {
            throw new ObjectConversionException("The given JSON file could not be parsed. This is likely to happen if the JSON object in " +
                    "the file does not match the Java object structure required for instantiating the object.", e.getCause());
        }
        return obj;
    }

    @Override
    @Nonnull
    public <T> T parse(@Nonnull File f, @Nonnull Class<T> cls) throws FileNotFoundException, ObjectConversionException {
        checkNotNull(f);
        checkNotNull(cls);
        return parse(new FileReader(f), cls);
    }

    @Override
    @Nonnull
    public String write(@Nonnull Object obj, @Nonnull Class cls) {
        checkNotNull(obj);
        checkNotNull(cls);
        return getGson().toJson(obj, cls);
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
