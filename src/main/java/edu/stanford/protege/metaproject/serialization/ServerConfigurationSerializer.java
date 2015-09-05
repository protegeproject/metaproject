package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.impl.ServerConfigurationImpl;

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
        OntologyTermIdStatus status = context.deserialize(obj.get("termIdentifiers"), OntologyTermIdStatus.class);
        return new ServerConfigurationImpl.Builder()
                .setHost(host)
                .setMetaproject(metaproject)
                .setAuthenticationManager(authenticationManager)
                .setPropertyMap(map)
                .setOntologyTermIdStatus(status)
                .createServerConfiguration();
    }

    @Override
    public JsonElement serialize(ServerConfiguration config, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("host", context.serialize(config.getHost(), Host.class));
        obj.add("metaproject", context.serialize(config.getMetaproject(), Metaproject.class));
        obj.add("authentication", context.serialize(config.getAuthenticationManager(), AuthenticationManager.class));
        obj.add("properties", context.serialize(config.getProperties().get(), Map.class));
        obj.add("termIdentifiers", context.serialize(config.getOntologyTermIdStatus().get(), OntologyTermIdStatus.class));
        return obj;
    }
}
