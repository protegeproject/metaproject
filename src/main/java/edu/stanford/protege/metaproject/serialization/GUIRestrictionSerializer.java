package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.GUIRestriction;
import edu.stanford.protege.metaproject.api.impl.GUIRestrictionImpl;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class GUIRestrictionSerializer implements JsonSerializer<GUIRestriction>, JsonDeserializer<GUIRestriction> {

    @Override
    public GUIRestriction deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        String componentName = obj.get("component").getAsString();
        GUIRestriction.Visibility restrictionVisibility = GUIRestriction.Visibility.valueOf(obj.getAsJsonPrimitive("visibility").getAsString());
        return new GUIRestrictionImpl(componentName, restrictionVisibility);
    }

    @Override
    public JsonElement serialize(GUIRestriction restriction, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("component", context.serialize(restriction.getGUIComponentName()));
        obj.add("visibility", context.serialize(restriction.getVisibility()));
        return obj;
    }
}
