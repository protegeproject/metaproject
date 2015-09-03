package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.ClientConfiguration;
import edu.stanford.protege.metaproject.api.GUIRestriction;
import edu.stanford.protege.metaproject.api.Policy;
import edu.stanford.protege.metaproject.api.impl.ClientConfigurationImpl;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ClientConfigurationSerializer implements JsonSerializer<ClientConfiguration>, JsonDeserializer<ClientConfiguration> {

    @Override
    public ClientConfiguration deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Policy policy = context.deserialize(obj.getAsJsonObject("policy"), Policy.class);
        int syncDelay = context.deserialize(obj.getAsJsonPrimitive("synchronisationDelay"), Integer.class);
        Set<GUIRestriction> set = context.deserialize(obj.getAsJsonArray("guiRestrictions"), new TypeToken<Set<GUIRestriction>>(){}.getType());
        Map<String,String> map = context.deserialize(obj.get("properties"), Map.class);
        return new ClientConfigurationImpl(policy, syncDelay, set , map);
    }

    @Override
    public JsonElement serialize(ClientConfiguration config, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("policy", context.serialize(config.getPolicy(), Policy.class));
        obj.add("synchronisationDelay", context.serialize(config.getSynchronisationDelay()));
        obj.add("guiRestrictions", context.serialize(config.getGUIRestrictions()));
        obj.add("properties", context.serialize(config.getProperties()));
        return obj;
    }
}
