package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.Project;
import edu.stanford.protege.metaproject.api.impl.ProjectManager;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectManagerSerializer implements JsonSerializer<ProjectManager>, JsonDeserializer<ProjectManager> {

    @Override
    public JsonElement serialize(ProjectManager projectManager, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("projects", context.serialize(projectManager.getProjects()));
        return object;
    }

    @Override
    public ProjectManager deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        Set<Project> projects = context.deserialize(obj.getAsJsonArray("projects"), Set.class);
        return ProjectManager.getInstance(projects);
    }
}
