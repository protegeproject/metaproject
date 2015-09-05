package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.impl.MetaprojectImpl;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class MetaprojectSerializer implements JsonSerializer<Metaproject>, JsonDeserializer<Metaproject> {

    @Override
    public JsonElement serialize(Metaproject metaproject, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("policy", context.serialize(metaproject.getPolicyManager(), PolicyManager.class));
        obj.add("users", context.serialize(metaproject.getUserManager(), UserManager.class));
        obj.add("projects", context.serialize(metaproject.getProjectManager(), ProjectManager.class));
        obj.add("roles", context.serialize(metaproject.getRoleManager(), RoleManager.class));
        obj.add("operations", context.serialize(metaproject.getOperationManager(), OperationManager.class));
        return obj;
    }

    @Override
    public Metaproject deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        PolicyManager policyManager = context.deserialize(obj.getAsJsonObject("policy"), PolicyManager.class);
        UserManager userManager = context.deserialize(obj.getAsJsonArray("users"), UserManager.class);
        ProjectManager projectManager = context.deserialize(obj.getAsJsonArray("projects"), ProjectManager.class);
        OperationManager operationManager = context.deserialize(obj.getAsJsonArray("operations"), OperationManager.class);
        RoleManager roleManager = context.deserialize(obj.getAsJsonArray("roles"), RoleManager.class);
        return new MetaprojectImpl.Builder()
                .setPolicyManager(policyManager)
                .setUserManager(userManager)
                .setProjectManager(projectManager)
                .setOperationManager(operationManager)
                .setRoleManager(roleManager)
                .createAccessControlPolicy();
    }
}
