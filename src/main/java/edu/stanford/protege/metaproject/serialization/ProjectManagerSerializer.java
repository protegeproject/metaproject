package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
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
        return context.serialize(projectManager.getProjects());
    }

    @Override
    public ProjectManager deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Set<Project> projects = context.deserialize(element.getAsJsonArray(), new TypeToken<Set<Project>>(){}.getType());
        return new ProjectManager(projects);
    }
}
