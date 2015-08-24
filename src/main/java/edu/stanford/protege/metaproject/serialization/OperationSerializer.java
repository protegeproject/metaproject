package edu.stanford.protege.metaproject.serialization;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.impl.*;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationSerializer implements JsonDeserializer<Operation> {

    @Override
    public Operation deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        OperationId operationId = new OperationIdImpl(obj.getAsJsonPrimitive("id").getAsString());
        Name operationName = new NameImpl(obj.getAsJsonPrimitive("name").getAsString());
        Description operationDescription = new DescriptionImpl(obj.getAsJsonPrimitive("description").getAsString());
        Set<OperationPrerequisite> prerequisites = context.deserialize(obj.getAsJsonArray("prerequisites"), new TypeToken<Set<OperationPrerequisite>>(){}.getType());
        return new OperationImpl(operationId, operationName, operationDescription, prerequisites);
    }
}
