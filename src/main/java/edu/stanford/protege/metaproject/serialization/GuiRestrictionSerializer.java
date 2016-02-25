package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.GuiRestriction;
import edu.stanford.protege.metaproject.impl.GuiRestrictionImpl;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class GuiRestrictionSerializer implements JsonSerializer<GuiRestriction>, JsonDeserializer<GuiRestriction> {
    private final String COMPONENT = "component", VISIBILITY = "visibility";
    
    @Override
    public GuiRestriction deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        String componentName = obj.get(COMPONENT).getAsString();
        GuiRestriction.Visibility restrictionVisibility = GuiRestriction.Visibility.valueOf(obj.getAsJsonPrimitive(VISIBILITY).getAsString());
        return new GuiRestrictionImpl(componentName, restrictionVisibility);
    }

    @Override
    public JsonElement serialize(GuiRestriction restriction, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add(COMPONENT, context.serialize(restriction.getGuiComponentName()));
        obj.add(VISIBILITY, context.serialize(restriction.getVisibility()));
        return obj;
    }
}
