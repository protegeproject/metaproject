package edu.stanford.protege.metaproject.serialization;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.impl.DescriptionImpl;
import edu.stanford.protege.metaproject.impl.NameImpl;
import edu.stanford.protege.metaproject.impl.OperationIdImpl;
import edu.stanford.protege.metaproject.impl.OperationImpl;

import java.lang.reflect.Type;
import java.util.Optional;
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
        OperationType operationType = OperationType.valueOf(obj.getAsJsonPrimitive("type").getAsString());
        Set<OperationPrerequisite> prerequisites = context.deserialize(obj.getAsJsonArray("prerequisites"), new TypeToken<Set<OperationPrerequisite>>(){}.getType());
        return new OperationImpl(operationId, operationName, operationDescription, operationType, Optional.ofNullable(prerequisites));
    }
}
