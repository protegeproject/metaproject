package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.impl.MetaprojectImpl;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.Metaproject;
import edu.stanford.protege.metaproject.impl.ServerConfigurationImpl;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerConfigurationSerializer implements JsonSerializer<ServerConfiguration>, JsonDeserializer<ServerConfiguration> {

    @Override
    public ServerConfiguration deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Host host = context.deserialize(obj.get("host"), Host.class);
        Metaproject metaproject = context.deserialize(obj.getAsJsonObject("metaproject"), Metaproject.class);
        AuthenticationManager authenticationManager = context.deserialize(obj.get("authentication"), AuthenticationManager.class);
        Map<String,String> map = context.deserialize(obj.get("properties"), Map.class);
        EntityIriStatus status = context.deserialize(obj.get("entityIriStatus"), EntityIriStatus.class);
        return new ServerConfigurationImpl.Builder()
                .setHost(host)
                .setMetaproject(metaproject)
                .setAuthenticationManager(authenticationManager)
                .setPropertyMap(map)
                .setEntityIriStatus(status)
                .createServerConfiguration();
    }

    @Override
    public JsonElement serialize(ServerConfiguration config, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("host", context.serialize(config.getHost(), Host.class));
        obj.add("metaproject", context.serialize(config.getMetaproject(), Metaproject.class));
        obj.add("authentication", context.serialize(config.getAuthenticationManager(), AuthenticationManager.class));
        obj.add("properties", context.serialize(config.getProperties().get(), Map.class));
        obj.add("entityIriStatus", context.serialize(config.getOntologyTermIdStatus().get(), EntityIriStatus.class));
        return obj;
    }
}
