package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class CollectionSerializer<E> implements JsonSerializer<Collection<E>>, JsonDeserializer<Collection<E>> {

    @Override
    public JsonElement serialize(Collection<E> collection, Type type, JsonSerializationContext context) {
        JsonArray result = new JsonArray();
        for(E item : collection){
            result.add(context.serialize(item));
        }
        return new JsonPrimitive(result.toString());
    }

    @Override
    public Collection<E> deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonArray items = (JsonArray) new JsonParser().parse(element.getAsString());
        ParameterizedType deserializationCollection = ((ParameterizedType) type);
        Type collectionItemType = deserializationCollection.getActualTypeArguments()[0];
        Collection<E> list;
        try {
            list = (Collection<E>)((Class<?>) deserializationCollection.getRawType()).newInstance();
            for(JsonElement e : items){
                list.add((E)context.deserialize(e, collectionItemType));
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JsonParseException(e);
        }
        return list;
    }
}
