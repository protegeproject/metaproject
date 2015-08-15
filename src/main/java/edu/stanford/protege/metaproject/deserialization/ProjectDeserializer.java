package edu.stanford.protege.metaproject.deserialization;

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
public class ProjectDeserializer implements JsonDeserializer<Project> {

    @Override
    public Project deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Id projectId = new AccessControlObjectIdImpl(obj.getAsJsonPrimitive("id").getAsString());
        Name projectName = new AccessControlObjectName(obj.getAsJsonPrimitive("name").getAsString());
        Description projectDescription = new AccessControlObjectDescription(obj.getAsJsonPrimitive("description").getAsString());
        Address projectLocation = new AccessControlObjectAddress(obj.getAsJsonPrimitive("location").getAsString());
        User owner = context.deserialize(obj.get("owner"), User.class);

        Type listOfTestObject = new TypeToken<ImmutableSet<Id>>(){}.getType();
        ImmutableSet<User> administrators = context.deserialize(obj.get("administrators"), listOfTestObject);
        return new ProjectImpl(projectId, projectName, projectDescription, projectLocation, owner, administrators);
    }
}
