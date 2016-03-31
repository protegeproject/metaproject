package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectSerializer implements JsonDeserializer<Project>, JsonSerializer<Project> {
    private final String ID = "id", NAME = "name", DESCRIPTION = "description", FILE = "file", OWNER = "owner",
            OPTIONS = "options";

    @Override
    public JsonElement serialize(Project project, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add(ID, context.serialize(project.getId()));
        obj.add(NAME, context.serialize(project.getName()));
        obj.add(DESCRIPTION, context.serialize(project.getDescription()));
        obj.add(FILE, context.serialize(project.getFile().getPath()));
        obj.add(OWNER, context.serialize(project.getOwner()));
        if(project.getOptions().isPresent()) {
            obj.add(OPTIONS, context.serialize(project.getOptions().get(), ProjectOptions.class));
        }
        return obj;
    }

    @Override
    public Project deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Factory factory = Manager.getFactory();
        JsonObject obj = element.getAsJsonObject();
        ProjectId projectId = factory.getProjectId(obj.getAsJsonPrimitive(ID).getAsString());
        Name projectName = factory.getName(obj.getAsJsonPrimitive(NAME).getAsString());
        Description projectDescription = factory.getDescription(obj.getAsJsonPrimitive(DESCRIPTION).getAsString());
        File projectFile = new File(obj.getAsJsonPrimitive(FILE).getAsString());
        UserId owner = factory.getUserId(obj.getAsJsonPrimitive(OWNER).getAsString());
        ProjectOptions projectOptions = null;
        if(obj.getAsJsonObject(OPTIONS) != null) {
            projectOptions = context.deserialize(obj.getAsJsonObject(OPTIONS), ProjectOptions.class);
        }
        return factory.getProject(projectId, projectName, projectDescription, projectFile, owner, Optional.ofNullable(projectOptions));
    }
}
