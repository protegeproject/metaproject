package edu.stanford.protege.metaproject.serialization;

import com.google.common.collect.ImmutableSet;
import com.google.gson.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ImmutableSetSerializer<E> implements JsonDeserializer<ImmutableSet<E>> {

    @Override
    public ImmutableSet<E> deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonArray items = element.getAsJsonArray();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type collectionItemType = parameterizedType.getActualTypeArguments()[0];
        Set<E> tempSet = new HashSet<>();
        for(JsonElement e : items){
            tempSet.add(context.deserialize(e, collectionItemType));
        }
        return ImmutableSet.copyOf(tempSet);
    }
}
