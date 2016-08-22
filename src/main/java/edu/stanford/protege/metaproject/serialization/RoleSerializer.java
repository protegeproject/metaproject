package edu.stanford.protege.metaproject.serialization;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import edu.stanford.protege.metaproject.ConfigurationManager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.impl.OperationIdImpl;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class RoleSerializer implements JsonDeserializer<Role> {

    @Override
    public Role deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        PolicyFactory factory = ConfigurationManager.getFactory();
        JsonObject obj = jsonElement.getAsJsonObject();
        RoleId operationId = factory.getRoleId(obj.getAsJsonPrimitive("id").getAsString());
        Name operationName = factory.getName(obj.getAsJsonPrimitive("name").getAsString());
        Description operationDescription = factory.getDescription(obj.getAsJsonPrimitive("description").getAsString());
        Set<OperationId> operations = context.deserialize(obj.getAsJsonArray("operations"), new TypeToken<Set<OperationIdImpl>>(){}.getType());
        return factory.getRole(operationId, operationName, operationDescription, operations);
    }
}
