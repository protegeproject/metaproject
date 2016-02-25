package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.Property;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PropertySerializer<E extends Property> implements JsonSerializer<Property>, JsonDeserializer<E> {

    @Override
    public JsonElement serialize(Property property, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(property.get().toString());
    }

    @Override
    @SuppressWarnings("unchecked")
    public E deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Object object = null;
        try {
            Class<?> c = Class.forName(type.getTypeName());
            Constructor<?> cons = c.getConstructor(String.class);
            object = cons.newInstance(element.getAsJsonPrimitive().getAsString());
        } catch(ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return (E) object;
    }
}
