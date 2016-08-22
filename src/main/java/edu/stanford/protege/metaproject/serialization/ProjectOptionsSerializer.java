package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.ConfigurationManager;
import edu.stanford.protege.metaproject.api.ProjectOptions;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ProjectOptionsSerializer implements JsonSerializer<ProjectOptions>, JsonDeserializer<ProjectOptions> {

    @Override
    public JsonElement serialize(ProjectOptions options, Type type, JsonSerializationContext context) {
        return context.serialize(options.getOptions());
    }

    @Override
    public ProjectOptions deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Map<String,Set<String>> options = context.deserialize(element, new TypeToken<Map<String,Set<String>>>(){}.getType());
        return ConfigurationManager.getFactory().getProjectOptions(options);
    }
}
