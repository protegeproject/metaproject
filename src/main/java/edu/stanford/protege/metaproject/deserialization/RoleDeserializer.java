package edu.stanford.protege.metaproject.deserialization;

import com.google.common.collect.ImmutableSet;
import com.google.gson.*;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.impl.AccessControlObjectDescription;
import edu.stanford.protege.metaproject.api.impl.AccessControlObjectIdImpl;
import edu.stanford.protege.metaproject.api.impl.AccessControlObjectName;
import edu.stanford.protege.metaproject.api.impl.RoleImpl;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleDeserializer implements JsonDeserializer<Role> {

    @Override
    public Role deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        Id operationId = new AccessControlObjectIdImpl(obj.getAsJsonPrimitive("id").getAsString());
        Name operationName = new AccessControlObjectName(obj.getAsJsonPrimitive("name").getAsString());
        Description operationDescription = new AccessControlObjectDescription(obj.getAsJsonPrimitive("description").getAsString());
        ImmutableSet<Project> projects = context.deserialize(obj.getAsJsonArray("projects"), ImmutableSet.class);
        ImmutableSet<Operation> operations = context.deserialize(obj.getAsJsonArray("operations"), ImmutableSet.class);
        return new RoleImpl(operationId, operationName, operationDescription, projects, operations);
    }
}
