package edu.stanford.protege.metaproject.deserialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import edu.stanford.protege.metaproject.api.Property;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class StringPropertyDeserializer<E extends Property<String>> implements JsonDeserializer<E> {

    @Override
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
