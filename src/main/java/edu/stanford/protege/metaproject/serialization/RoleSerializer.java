package edu.stanford.protege.metaproject.serialization;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.impl.OperationIdImpl;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleSerializer implements JsonDeserializer<Role> {

    @Override
    public Role deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        Factory factory = Manager.getFactory();
        JsonObject obj = jsonElement.getAsJsonObject();
        RoleId operationId = factory.createRoleId(obj.getAsJsonPrimitive("id").getAsString());
        Name operationName = factory.createName(obj.getAsJsonPrimitive("name").getAsString());
        Description operationDescription = factory.createDescription(obj.getAsJsonPrimitive("description").getAsString());
        Set<OperationId> operations = context.deserialize(obj.getAsJsonArray("operations"), new TypeToken<Set<OperationIdImpl>>(){}.getType());
        return factory.createRole(operationId, operationName, operationDescription, operations);
    }
}
