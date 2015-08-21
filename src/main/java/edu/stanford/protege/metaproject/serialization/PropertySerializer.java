package edu.stanford.protege.metaproject.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import edu.stanford.protege.metaproject.api.Property;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PropertySerializer<T> implements JsonSerializer<Property<T>> {

    @Override
    public JsonElement serialize(Property<T> property, Type type, JsonSerializationContext context) {
        return context.serialize(property.get());
    }
}
