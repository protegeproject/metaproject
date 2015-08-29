package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.impl.AccessControlPolicy;
import edu.stanford.protege.metaproject.api.impl.RoleIdImpl;
import edu.stanford.protege.metaproject.api.impl.UserIdImpl;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PolicySerializer implements JsonSerializer<Policy>, JsonDeserializer<Policy> {

    @Override
    public JsonElement serialize(Policy policy, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("user_role_mappings", context.serialize(policy.getUserRoleMappings()));
        obj.add("users", context.serialize(policy.getUserManager(), UserManager.class));
        obj.add("projects", context.serialize(policy.getProjectManager(), ProjectManager.class));
        obj.add("roles", context.serialize(policy.getRoleManager(), RoleManager.class));
        obj.add("operations", context.serialize(policy.getOperationManager(), OperationManager.class));
        return obj;
    }

    @Override
    public Policy deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Map<UserId,Set<RoleId>> map = context.deserialize(obj.getAsJsonObject("user_role_mappings"), new TypeToken<Map<UserIdImpl, Set<RoleIdImpl>>>(){}.getType());
        UserManager userManager = context.deserialize(obj.getAsJsonArray("users"), UserManager.class);
        ProjectManager projectManager = context.deserialize(obj.getAsJsonArray("projects"), ProjectManager.class);
        OperationManager operationManager = context.deserialize(obj.getAsJsonArray("operations"), OperationManager.class);
        RoleManager roleManager = context.deserialize(obj.getAsJsonArray("roles"), RoleManager.class);
        return new AccessControlPolicy.Builder()
                .setUserRoleMap(map)
                .setUserManager(userManager)
                .setProjectManager(projectManager)
                .setOperationManager(operationManager)
                .setRoleManager(roleManager)
                .createAccessControlPolicy();
    }
}
