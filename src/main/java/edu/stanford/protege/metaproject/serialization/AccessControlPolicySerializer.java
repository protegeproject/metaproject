package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.impl.*;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AccessControlPolicySerializer implements JsonSerializer<Policy>, JsonDeserializer<Policy> {

    @Override
    public JsonElement serialize(Policy policy, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("user_role_mappings", context.serialize(policy.getUserRoleMappings()));
        obj.add("users", context.serialize(policy.getUserManager()));
        obj.add("projects", context.serialize(policy.getProjectManager()));
        obj.add("roles", context.serialize(policy.getRoleManager()));
        obj.add("operations", context.serialize(policy.getOperationManager()));
        return obj;
    }

    @Override
    public Policy deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Map<UserId,Set<RoleId>> map = context.deserialize(obj.getAsJsonObject("user_role_mappings"), new TypeToken<Map<UserIdImpl, Set<RoleIdImpl>>>(){}.getType());
        UserManager userManager = context.deserialize(obj.getAsJsonArray("users"), UserManagerImpl.class);
        ProjectManager projectManager = context.deserialize(obj.getAsJsonArray("projects"), ProjectManagerImpl.class);
        OperationManager operationManager = context.deserialize(obj.getAsJsonArray("operations"), OperationManagerImpl.class);
        RoleManager roleManager = context.deserialize(obj.getAsJsonArray("roles"), RoleManagerImpl.class);
        return new AccessControlPolicy.Builder()
                .setUserRoleMap(map)
                .setUserManager(userManager)
                .setProjectManager(projectManager)
                .setOperationManager(operationManager)
                .setRoleManager(roleManager)
                .createAccessControlPolicy();
    }
}
