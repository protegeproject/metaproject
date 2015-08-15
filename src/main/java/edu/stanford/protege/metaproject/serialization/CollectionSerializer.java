package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class CollectionSerializer<E> implements JsonSerializer<Collection<E>> {

    @Override
    public JsonElement serialize(Collection<E> collection, Type type, JsonSerializationContext context) {
        JsonArray result = new JsonArray();
        for(E item : collection){
            result.add(context.serialize(item));
        }
        return new JsonPrimitive(result.toString());
    }
}
