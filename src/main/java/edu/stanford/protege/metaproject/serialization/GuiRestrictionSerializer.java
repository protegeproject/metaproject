package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.GuiRestriction;
import edu.stanford.protege.metaproject.api.impl.GuiRestrictionImpl;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class GuiRestrictionSerializer implements JsonSerializer<GuiRestriction>, JsonDeserializer<GuiRestriction> {

    @Override
    public GuiRestriction deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        String componentName = obj.get("component").getAsString();
        GuiRestriction.Visibility restrictionVisibility = GuiRestriction.Visibility.valueOf(obj.getAsJsonPrimitive("visibility").getAsString());
        return new GuiRestrictionImpl(componentName, restrictionVisibility);
    }

    @Override
    public JsonElement serialize(GuiRestriction restriction, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("component", context.serialize(restriction.getGuiComponentName()));
        obj.add("visibility", context.serialize(restriction.getVisibility()));
        return obj;
    }
}
