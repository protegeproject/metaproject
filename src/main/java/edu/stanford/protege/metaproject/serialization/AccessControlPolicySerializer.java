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
public class AccessControlPolicySerializer implements JsonSerializer<AccessControlPolicy>, JsonDeserializer<AccessControlPolicy> {

    @Override
    public JsonElement serialize(AccessControlPolicy policy, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("user_role_mappings", context.serialize(policy.getUserRoleMappings()));
        obj.add("users", context.serialize(policy.getUserManager()));
        obj.add("projects", context.serialize(policy.getProjectManager()));
        obj.add("roles", context.serialize(policy.getRoleManager()));
        obj.add("operations", context.serialize(policy.getOperationManager()));
        return obj;
    }

    @Override
    public AccessControlPolicy deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Map<UserId,Set<RoleId>> map = context.deserialize(obj.getAsJsonObject("user_role_mappings"), new TypeToken<Map<UserIdImpl, Set<RoleIdImpl>>>(){}.getType());
        Set<User> users = context.deserialize(obj.getAsJsonArray("users"), new TypeToken<Set<User>>(){}.getType());
        Set<Project> projects = context.deserialize(obj.getAsJsonArray("projects"), new TypeToken<Set<Project>>(){}.getType());
        Set<Operation> operations = context.deserialize(obj.getAsJsonArray("operations"), new TypeToken<Set<Operation>>(){}.getType());
        Set<Role> roles = context.deserialize(obj.getAsJsonArray("roles"), new TypeToken<Set<Role>>(){}.getType());

        return new AccessControlPolicy.Builder()
                .setUserRoleMap(map)
                .setUsers(users)
                .setProjects(projects)
                .setOperations(operations)
                .setRoles(roles)
                .createAccessControlPolicy();
    }
}
