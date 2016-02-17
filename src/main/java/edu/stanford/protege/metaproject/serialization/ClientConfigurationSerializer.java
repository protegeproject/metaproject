package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.ClientConfiguration;
import edu.stanford.protege.metaproject.api.GuiRestriction;
import edu.stanford.protege.metaproject.api.AccessControlPolicy;
import edu.stanford.protege.metaproject.impl.ClientConfigurationImpl;

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
        AccessControlPolicy accessControlPolicy = context.deserialize(obj.getAsJsonObject("accessControlPolicy"), AccessControlPolicy.class);
        int syncDelay = context.deserialize(obj.getAsJsonPrimitive("synchronisationDelay"), Integer.class);
        Set<GuiRestriction> set = context.deserialize(obj.getAsJsonArray("guiRestrictions"), new TypeToken<Set<GuiRestriction>>(){}.getType());
        Map<String,String> map = context.deserialize(obj.get("properties"), Map.class);
        return new ClientConfigurationImpl(accessControlPolicy, syncDelay, set , map);
    }

    @Override
    public JsonElement serialize(ClientConfiguration config, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("accessControlPolicy", context.serialize(config.getAccessControlPolicy(), AccessControlPolicy.class));
        obj.add("synchronisationDelay", context.serialize(config.getSynchronisationDelay()));
        obj.add("guiRestrictions", context.serialize(config.getGuiRestrictions()));
        obj.add("properties", context.serialize(config.getProperties()));
        return obj;
    }
}
