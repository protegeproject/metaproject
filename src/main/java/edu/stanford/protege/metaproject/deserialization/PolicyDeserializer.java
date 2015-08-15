package edu.stanford.protege.metaproject.deserialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.impl.AccessControlPolicy;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PolicyDeserializer implements JsonDeserializer<AccessControlPolicy> {

    @Override
    public AccessControlPolicy deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonArray items = element.getAsJsonArray();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type collectionItemType = parameterizedType.getActualTypeArguments()[0];
//        Set<E> tempSet = new HashSet<>();
//        for(JsonElement e : items){
//            tempSet.add(context.deserialize(e, collectionItemType));
//        }
//        return ImmutableSet.copyOf(tempSet);
    return  null;
    }
}
