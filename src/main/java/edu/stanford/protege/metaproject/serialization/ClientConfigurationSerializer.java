package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.ClientConfiguration;
import edu.stanford.protege.metaproject.api.GuiRestriction;
import edu.stanford.protege.metaproject.api.Metaproject;
import edu.stanford.protege.metaproject.impl.ClientConfigurationImpl;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ClientConfigurationSerializer implements JsonSerializer<ClientConfiguration>, JsonDeserializer<ClientConfiguration> {
    private final String
            METAPROJECT = "metaproject", SYNC_DELAY = "synchronisationDelay",
            GUI_RESTRICTIONS = "guiRestrictions", PROPERTIES = "properties";
    
    @Override
    public ClientConfiguration deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Metaproject metaproject = context.deserialize(obj.getAsJsonObject(METAPROJECT), Metaproject.class);
        int syncDelay = context.deserialize(obj.getAsJsonPrimitive(SYNC_DELAY), Integer.class);
        Set<GuiRestriction> set = context.deserialize(obj.getAsJsonArray(GUI_RESTRICTIONS), new TypeToken<Set<GuiRestriction>>(){}.getType());
        Map<String,String> map = context.deserialize(obj.get(PROPERTIES), Map.class);
        return new ClientConfigurationImpl(metaproject, syncDelay, set , map);
    }

    @Override
    public JsonElement serialize(ClientConfiguration config, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add(METAPROJECT, context.serialize(config.getMetaproject(), Metaproject.class));
        obj.add(SYNC_DELAY, context.serialize(config.getSynchronisationDelay()));
        obj.add(GUI_RESTRICTIONS, context.serialize(config.getGuiRestrictions()));
        obj.add(PROPERTIES, context.serialize(config.getProperties()));
        return obj;
    }
}
