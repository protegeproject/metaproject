package edu.stanford.protege.metaproject.deserialization;

import com.google.gson.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class CollectionDeserializer<E> implements JsonDeserializer<Collection<E>> {

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
        } catch (InstantiationException e) {
            throw new JsonParseException(e);
        } catch (IllegalAccessException e) {
            throw new JsonParseException(e);
        }

        return list;
    }
}
