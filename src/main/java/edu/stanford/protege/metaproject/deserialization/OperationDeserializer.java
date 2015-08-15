package edu.stanford.protege.metaproject.deserialization;

import com.google.common.collect.ImmutableSet;
import com.google.gson.*;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.impl.AccessControlObjectDescription;
import edu.stanford.protege.metaproject.api.impl.AccessControlObjectIdImpl;
import edu.stanford.protege.metaproject.api.impl.AccessControlObjectName;
import edu.stanford.protege.metaproject.api.impl.OperationImpl;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationDeserializer implements JsonDeserializer<Operation> {

    @Override
    public Operation deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Id operationId = new AccessControlObjectIdImpl(obj.getAsJsonPrimitive("id").getAsString());
        Name operationName = new AccessControlObjectName(obj.getAsJsonPrimitive("name").getAsString());
        Description operationDescription = new AccessControlObjectDescription(obj.getAsJsonPrimitive("description").getAsString());
        ImmutableSet<OperationPrerequisite> prerequisites = context.deserialize(obj.getAsJsonArray("prerequisites"), ImmutableSet.class);
        return new OperationImpl(operationId, operationName, operationDescription, prerequisites);
    }
}
