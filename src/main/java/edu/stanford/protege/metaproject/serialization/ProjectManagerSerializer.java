package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.Project;
import edu.stanford.protege.metaproject.api.ProjectRegistry;
import edu.stanford.protege.metaproject.impl.ProjectRegistryImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectManagerSerializer implements JsonSerializer<ProjectRegistry>, JsonDeserializer<ProjectRegistry> {

    @Override
    public JsonElement serialize(ProjectRegistry projectRegistry, Type type, JsonSerializationContext context) {
        List<Project> list = new ArrayList<>(projectRegistry.getProjects());
        Collections.sort(list);
        return context.serialize(list);
    }

    @Override
    public ProjectRegistry deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Set<Project> projects = context.deserialize(element.getAsJsonArray(), new TypeToken<Set<Project>>(){}.getType());
        return new ProjectRegistryImpl(projects);
    }
}