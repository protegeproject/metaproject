package edu.stanford.protege.metaproject.serialization;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationSerializer implements JsonDeserializer<Operation> {
    private final String ID = "id", NAME = "name", DESCRIPTION = "description", TYPE = "type", RESTRICTIONS = "restrictions";

    @Override
    public Operation deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Factory factory = Manager.getFactory();
        JsonObject obj = element.getAsJsonObject();
        OperationId operationId = factory.createOperationId(obj.getAsJsonPrimitive(ID).getAsString());
        Name operationName = factory.createName(obj.getAsJsonPrimitive(NAME).getAsString());
        Description operationDescription = factory.createDescription(obj.getAsJsonPrimitive(DESCRIPTION).getAsString());
        OperationType operationType = OperationType.valueOf(obj.getAsJsonPrimitive(TYPE).getAsString());
        Set<OperationRestriction> restrictions = context.deserialize(obj.getAsJsonArray(RESTRICTIONS), new TypeToken<Set<OperationRestriction>>(){}.getType());
        return factory.createOperation(operationId, operationName, operationDescription, operationType, Optional.ofNullable(restrictions));
    }
}
