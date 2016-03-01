package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.impl.ServerConfigurationImpl;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerConfigurationSerializer implements JsonSerializer<ServerConfiguration>, JsonDeserializer<ServerConfiguration> {
    private final String HOST = "host", METAPROJECT = "metaproject", AUTHENTICATION = "authentication",
            PROPERTIES = "properties", ENTITY_IRI_STATUS = "entityIriStatus";

    @Override
    public ServerConfiguration deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Host host = context.deserialize(obj.get(HOST), Host.class);
        Metaproject metaproject = context.deserialize(obj.getAsJsonObject(METAPROJECT), Metaproject.class);
        AuthenticationManager authenticationManager = context.deserialize(obj.get(AUTHENTICATION), AuthenticationManager.class);
        Map<String,String> map = context.deserialize(obj.get(PROPERTIES), Map.class);
        EntityIriStatus status = context.deserialize(obj.get(ENTITY_IRI_STATUS), EntityIriStatus.class);
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
        obj.add(HOST, context.serialize(config.getHost(), Host.class));
        obj.add(METAPROJECT, context.serialize(config.getMetaproject(), Metaproject.class));
        obj.add(AUTHENTICATION, context.serialize(config.getAuthenticationManager(), AuthenticationManager.class));
        obj.add(PROPERTIES, context.serialize(config.getProperties().get(), Map.class));
        obj.add(ENTITY_IRI_STATUS, context.serialize(config.getOntologyTermIdStatus().get(), EntityIriStatus.class));
        return obj;
    }
}
