package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.impl.AccessControlPolicyImpl;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AccessControlPolicySerializer implements JsonSerializer<AccessControlPolicy>, JsonDeserializer<AccessControlPolicy> {

    @Override
    public JsonElement serialize(AccessControlPolicy accessControlPolicy, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("policy", context.serialize(accessControlPolicy.getPolicyManager(), PolicyManager.class));
        obj.add("users", context.serialize(accessControlPolicy.getUserManager(), UserManager.class));
        obj.add("projects", context.serialize(accessControlPolicy.getProjectManager(), ProjectManager.class));
        obj.add("roles", context.serialize(accessControlPolicy.getRoleManager(), RoleManager.class));
        obj.add("operations", context.serialize(accessControlPolicy.getOperationManager(), OperationManager.class));
        return obj;
    }

    @Override
    public AccessControlPolicy deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        PolicyManager policyManager = context.deserialize(obj.getAsJsonObject("policy"), PolicyManager.class);
        UserManager userManager = context.deserialize(obj.getAsJsonArray("users"), UserManager.class);
        ProjectManager projectManager = context.deserialize(obj.getAsJsonArray("projects"), ProjectManager.class);
        OperationManager operationManager = context.deserialize(obj.getAsJsonArray("operations"), OperationManager.class);
        RoleManager roleManager = context.deserialize(obj.getAsJsonArray("roles"), RoleManager.class);
        return new AccessControlPolicyImpl.Builder()
                .setPolicyManager(policyManager)
                .setUserManager(userManager)
                .setProjectManager(projectManager)
                .setOperationManager(operationManager)
                .setRoleManager(roleManager)
                .createAccessControlPolicy();
    }
}
