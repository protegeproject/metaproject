package edu.stanford.protege.metaproject.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import edu.stanford.protege.metaproject.api.Property;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class StringPropertySerializer implements JsonSerializer<Property<String>> {

    @Override
    public JsonElement serialize(Property<String> property, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(property.get());
    }
}
