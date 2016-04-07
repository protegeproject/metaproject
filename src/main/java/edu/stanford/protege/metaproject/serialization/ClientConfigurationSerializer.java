package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.ClientConfiguration;
import edu.stanford.protege.metaproject.api.Metaproject;
import edu.stanford.protege.metaproject.impl.ClientConfigurationBuilder;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ClientConfigurationSerializer implements JsonSerializer<ClientConfiguration>, JsonDeserializer<ClientConfiguration> {
    private final String
            METAPROJECT = "metaproject", SYNC_DELAY = "synchronisationDelay", PROPERTIES = "properties";

    @Override
    public JsonElement serialize(ClientConfiguration config, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add(METAPROJECT, context.serialize(config.getMetaproject(), Metaproject.class));
        obj.add(SYNC_DELAY, context.serialize(config.getSynchronisationDelay()));
        obj.add(PROPERTIES, context.serialize(config.getProperties()));
        return obj;
    }

    @Override
    public ClientConfiguration deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Metaproject metaproject = context.deserialize(obj.getAsJsonObject(METAPROJECT), Metaproject.class);
        int syncDelay = context.deserialize(obj.getAsJsonPrimitive(SYNC_DELAY), Integer.class);
        Map<String,String> map = context.deserialize(obj.get(PROPERTIES), Map.class);
        return new ClientConfigurationBuilder().setMetaproject(metaproject).setSynchronisationDelay(syncDelay).setProperties(map).createClientConfiguration();
    }
}
