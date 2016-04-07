package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.ProjectOptions;
import edu.stanford.protege.metaproject.impl.ProjectOptionsBuilder;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectOptionsSerializer implements JsonSerializer<ProjectOptions>, JsonDeserializer<ProjectOptions> {
    private final String
        COMPLEX_PROPERTIES = "complexProperties", IMMUTABLE_PROPERTIES = "immutableAnnotations", REQUIRED_ENTITIES = "requiredEntities",
        REQUIRED_ANNOTATIONS = "requiredAnnotations", OPTIONAL_ANNOTATIONS = "optionalAnnotations", CUSTOM_PROPERTIES = "customProperties";

    @Override
    public JsonElement serialize(ProjectOptions options, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add(COMPLEX_PROPERTIES, context.serialize(options.getComplexAnnotationProperties()));
        obj.add(IMMUTABLE_PROPERTIES, context.serialize(options.getImmutableAnnotationProperties()));
        obj.add(REQUIRED_ENTITIES, context.serialize(options.getRequiredEntities()));
        obj.add(REQUIRED_ANNOTATIONS, context.serialize(options.getRequiredAnnotationsForAnnotation()));
        obj.add(OPTIONAL_ANNOTATIONS, context.serialize(options.getOptionalAnnotationAnnotations()));
        obj.add(CUSTOM_PROPERTIES, context.serialize(options.getProperties()));
        return obj;
    }

    @Override
    public ProjectOptions deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Set<String> complexProperties = context.deserialize(obj.getAsJsonArray(COMPLEX_PROPERTIES), Set.class);
        Set<String> immutableProperties = context.deserialize(obj.getAsJsonArray(IMMUTABLE_PROPERTIES), Set.class);
        Map<String,Set<String>> requiredEntities = context.deserialize(obj.get(REQUIRED_ENTITIES), new TypeToken<Map<String,Set<String>>>(){}.getType());
        Map<String,Set<String>> requiredAnnotationsMap = context.deserialize(obj.get(REQUIRED_ANNOTATIONS), new TypeToken<Map<String,Set<String>>>(){}.getType());
        Map<String,Set<String>> optionalAnnotationsMap = context.deserialize(obj.get(OPTIONAL_ANNOTATIONS), new TypeToken<Map<String,Set<String>>>(){}.getType());
        Map<String,String> customProperties = context.deserialize(obj.get(CUSTOM_PROPERTIES), Map.class);
        return new ProjectOptionsBuilder()
                .setComplexAnnotations(complexProperties)
                .setImmutableAnnotations(immutableProperties)
                .setRequiredEntities(requiredEntities)
                .setRequiredAnnotationsMap(requiredAnnotationsMap)
                .setOptionalAnnotationsMap(optionalAnnotationsMap)
                .setCustomProperties(customProperties)
                .createProjectOptions();
    }
}
