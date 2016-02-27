package edu.stanford.protege.metaproject.serialization;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.impl.UserIdImpl;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectSerializer implements JsonDeserializer<Project> {

    @Override
    public Project deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Factory factory = Manager.getFactory();
        JsonObject obj = element.getAsJsonObject();
        ProjectId projectId = factory.createProjectId(obj.getAsJsonPrimitive("id").getAsString());
        Name projectName = factory.createName(obj.getAsJsonPrimitive("name").getAsString());
        Description projectDescription = factory.createDescription(obj.getAsJsonPrimitive("description").getAsString());
        Address projectLocation = factory.createAddress(obj.getAsJsonPrimitive("address").getAsString());
        UserId owner = factory.createUserId(obj.getAsJsonPrimitive("owner").getAsString());
        Set<UserId> administrators = context.deserialize(obj.get("administrators"), new TypeToken<Set<UserIdImpl>>(){}.getType());
        return factory.createProject(projectId, projectName, projectDescription, projectLocation, owner, administrators);
    }
}
