package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.AuthenticationRegistry;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.Metaproject;
import edu.stanford.protege.metaproject.api.ServerConfiguration;
import edu.stanford.protege.metaproject.impl.ServerConfigurationBuilder;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerConfigurationSerializer implements JsonSerializer<ServerConfiguration>, JsonDeserializer<ServerConfiguration> {
    private final String HOST = "host", ROOT = "root", METAPROJECT = "metaproject", AUTHENTICATION = "authentication", PROPERTIES = "properties";

    @Override
    public JsonElement serialize(ServerConfiguration config, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add(HOST, context.serialize(config.getHost(), Host.class));
        obj.add(ROOT, context.serialize(config.getServerRoot().getPath()));
        obj.add(METAPROJECT, context.serialize(config.getMetaproject(), Metaproject.class));
        obj.add(AUTHENTICATION, context.serialize(config.getAuthenticationRegistry(), AuthenticationRegistry.class));
        obj.add(PROPERTIES, context.serialize(config.getProperties(), Map.class));
        return obj;
    }

    @Override
    public ServerConfiguration deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Host host = context.deserialize(obj.get(HOST), Host.class);
        File root = new File(obj.getAsJsonPrimitive(ROOT).getAsString());
        Metaproject metaproject = context.deserialize(obj.getAsJsonObject(METAPROJECT), Metaproject.class);
        AuthenticationRegistry authenticationRegistry = context.deserialize(obj.get(AUTHENTICATION), AuthenticationRegistry.class);
        Map<String,String> map = context.deserialize(obj.get(PROPERTIES), Map.class);
        return new ServerConfigurationBuilder()
                .setHost(host)
                .setServerRoot(root)
                .setMetaproject(metaproject)
                .setAuthenticationRegistry(authenticationRegistry)
                .setPropertyMap(map)
                .createServerConfiguration();
    }
}
