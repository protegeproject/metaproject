package edu.stanford.protege.metaproject.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import edu.stanford.protege.metaproject.api.impl.*;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AccessControlPolicySerializer implements JsonSerializer<AccessControlPolicy> {

    @Override
    public JsonElement serialize(AccessControlPolicy policy, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("users", context.serialize(UserManager.getInstance().getUsers()));
        object.add("projects", context.serialize(ProjectManager.getInstance().getProjects()));
        object.add("operations", context.serialize(OperationManager.getInstance().getOperations()));
        object.add("roles", context.serialize(RoleManager.getInstance().getRoles()));
        return object;
    }
}
