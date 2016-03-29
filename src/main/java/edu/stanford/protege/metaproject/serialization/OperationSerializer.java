package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationSerializer implements JsonDeserializer<Operation> {
    private final String ID = "id", NAME = "name", DESCRIPTION = "description", TYPE = "type";

    @Override
    public Operation deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Factory factory = Manager.getFactory();
        JsonObject obj = element.getAsJsonObject();
        OperationId operationId = factory.getOperationId(obj.getAsJsonPrimitive(ID).getAsString());
        Name operationName = factory.getName(obj.getAsJsonPrimitive(NAME).getAsString());
        Description operationDescription = factory.getDescription(obj.getAsJsonPrimitive(DESCRIPTION).getAsString());
        OperationType operationType = OperationType.valueOf(obj.getAsJsonPrimitive(TYPE).getAsString());
        return factory.getServerOperation(operationId, operationName, operationDescription, operationType);
    }
}
