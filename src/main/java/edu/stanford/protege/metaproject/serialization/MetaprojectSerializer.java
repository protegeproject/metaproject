package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.impl.MetaprojectImpl;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.Metaproject;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class MetaprojectSerializer implements JsonSerializer<Metaproject>, JsonDeserializer<Metaproject> {
    private final String POLICY = "policy", USERS = "users", PROJECTS = "projects", ROLES = "roles", OPERATIONS = "operations";
            
    @Override
    public JsonElement serialize(Metaproject metaproject, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add(POLICY, context.serialize(metaproject.getPolicy(), Policy.class));
        obj.add(USERS, context.serialize(metaproject.getUserRegistry(), UserRegistry.class));
        obj.add(PROJECTS, context.serialize(metaproject.getProjectRegistry(), ProjectRegistry.class));
        obj.add(ROLES, context.serialize(metaproject.getRoleRegistry(), RoleRegistry.class));
        obj.add(OPERATIONS, context.serialize(metaproject.getOperationRegistry(), OperationRegistry.class));
        return obj;
    }

    @Override
    public Metaproject deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Policy policy = context.deserialize(obj.getAsJsonObject(POLICY), Policy.class);
        UserRegistry userRegistry = context.deserialize(obj.getAsJsonArray(USERS), UserRegistry.class);
        ProjectRegistry projectRegistry = context.deserialize(obj.getAsJsonArray(PROJECTS), ProjectRegistry.class);
        OperationRegistry operationRegistry = context.deserialize(obj.getAsJsonArray(OPERATIONS), OperationRegistry.class);
        RoleRegistry roleRegistry = context.deserialize(obj.getAsJsonArray(ROLES), RoleRegistry.class);
        return new MetaprojectImpl.Builder()
                .setPolicy(policy)
                .setUserRegistry(userRegistry)
                .setProjectRegistry(projectRegistry)
                .setOperationRegistry(operationRegistry)
                .setRoleRegistry(roleRegistry)
                .createMetaproject();
    }
}
