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
public class ProjectSerializer implements JsonDeserializer<Project> {

    @Override
    public Project deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        ProjectId projectId = new ProjectIdImpl(obj.getAsJsonPrimitive("id").getAsString());
        Name projectName = new NameImpl(obj.getAsJsonPrimitive("name").getAsString());
        Description projectDescription = new DescriptionImpl(obj.getAsJsonPrimitive("description").getAsString());
        Address projectLocation = new AddressImpl(obj.getAsJsonPrimitive("address").getAsString());
        UserId owner = new UserIdImpl(obj.getAsJsonPrimitive("owner").getAsString());
        ImmutableSet<UserId> administrators = context.deserialize(obj.get("administrators"), new TypeToken<ImmutableSet<UserIdImpl>>(){}.getType());
        return new ProjectImpl(projectId, projectName, projectDescription, projectLocation, owner, administrators);
    }
}
