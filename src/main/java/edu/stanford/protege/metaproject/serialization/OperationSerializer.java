package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.ConfigurationManager;
import edu.stanford.protege.metaproject.api.*;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class OperationSerializer implements JsonDeserializer<Operation> {
    private final String ID = "id", NAME = "name", DESCRIPTION = "description", TYPE = "type", SCOPE = "scope", SYSTEM = "default";

    @Override
    public Operation deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        PolicyFactory factory = ConfigurationManager.getFactory();
        JsonObject obj = element.getAsJsonObject();
        OperationId operationId = factory.getOperationId(obj.getAsJsonPrimitive(ID).getAsString());
        Name operationName = factory.getName(obj.getAsJsonPrimitive(NAME).getAsString());
        Description operationDescription = factory.getDescription(obj.getAsJsonPrimitive(DESCRIPTION).getAsString());
        OperationType operationType = OperationType.valueOf(obj.getAsJsonPrimitive(TYPE).getAsString());
        Operation.Scope scope = Operation.Scope.valueOf(obj.getAsJsonPrimitive(SCOPE).getAsString());
        boolean system = obj.getAsJsonPrimitive(SYSTEM).getAsBoolean();
        if(system) {
            return factory.getSystemOperation(operationId, operationName, operationDescription, operationType, scope);
        } else {
            return factory.getCustomOperation(operationId, operationName, operationDescription, operationType, scope);
        }
    }
}
