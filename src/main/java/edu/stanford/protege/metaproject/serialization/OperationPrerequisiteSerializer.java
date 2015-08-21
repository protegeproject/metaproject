package edu.stanford.protege.metaproject.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import edu.stanford.protege.metaproject.api.OperationPrerequisite;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationPrerequisiteSerializer implements JsonSerializer<OperationPrerequisite> {

    @Override
    public JsonElement serialize(OperationPrerequisite prerequisite, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("prerequisite", prerequisite.getPrerequisite().toString());
        object.addProperty("modifier", prerequisite.getModifier().toString());
        return object;
    }
}
