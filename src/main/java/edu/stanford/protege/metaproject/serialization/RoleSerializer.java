package edu.stanford.protege.metaproject.serialization;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.impl.*;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleSerializer implements JsonDeserializer<Role> {

    @Override
    public Role deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        RoleId operationId = new RoleIdImpl(obj.getAsJsonPrimitive("id").getAsString());
        Name operationName = new NameImpl(obj.getAsJsonPrimitive("name").getAsString());
        Description operationDescription = new DescriptionImpl(obj.getAsJsonPrimitive("description").getAsString());
        ImmutableSet<ProjectId> projects = context.deserialize(obj.getAsJsonArray("projects"), new TypeToken<ImmutableSet<ProjectIdImpl>>(){}.getType());
        ImmutableSet<OperationId> operations = context.deserialize(obj.getAsJsonArray("operations"), new TypeToken<ImmutableSet<OperationIdImpl>>(){}.getType());
        return new RoleImpl(operationId, operationName, operationDescription, projects, operations);
    }
}
