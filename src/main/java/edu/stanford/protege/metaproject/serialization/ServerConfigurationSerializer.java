package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.impl.ProjectIdImpl;
import edu.stanford.protege.metaproject.impl.RoleIdImpl;
import edu.stanford.protege.metaproject.impl.ConfigurationBuilder;
import edu.stanford.protege.metaproject.impl.UserIdImpl;

import java.io.File;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ServerConfigurationSerializer implements JsonSerializer<ServerConfiguration>, JsonDeserializer<ServerConfiguration> {
    private final String HOST = "host", ROOT = "root", PROPERTIES = "properties", POLICY = "policy", USERS = "users", PROJECTS = "projects",
            ROLES = "roles", OPERATIONS = "operations", AUTHENTICATION = "authentication";

    @Override
    public JsonElement serialize(ServerConfiguration config, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add(HOST, context.serialize(config.getHost(), Host.class));
        obj.add(ROOT, context.serialize(config.getServerRoot().getPath()));
        obj.add(POLICY, context.serialize(config.getPolicyMap()));

        List<User> users = new ArrayList<>(config.getUsers());
        Collections.sort(users);
        obj.add(USERS, context.serialize(users));

        List<Project> projects = new ArrayList<>(config.getProjects());
        Collections.sort(projects);
        obj.add(PROJECTS, context.serialize(projects, new TypeToken<List<Project>>(){}.getType()));

        List<Role> roles = new ArrayList<>(config.getRoles());
        Collections.sort(roles);
        obj.add(ROLES, context.serialize(roles));

        List<Operation> operations = new ArrayList<>(config.getOperations());
        Collections.sort(operations);
        obj.add(OPERATIONS, context.serialize(operations));

        List<AuthenticationDetails> authDetails = new ArrayList<>(config.getAuthenticationDetails());
        Collections.sort(authDetails);
        obj.add(AUTHENTICATION, context.serialize(authDetails));
        obj.add(PROPERTIES, context.serialize(config.getProperties(), Map.class));
        return obj;
    }

    @Override
    public ServerConfiguration deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Host host = context.deserialize(obj.get(HOST), Host.class);
        File root = new File(obj.getAsJsonPrimitive(ROOT).getAsString());
        Map<UserId, Map<ProjectId, Set<RoleId>>> policy = context.deserialize(obj.getAsJsonObject(POLICY),
                new TypeToken<Map<UserIdImpl,Map<ProjectIdImpl,Set<RoleIdImpl>>>>() {}.getType());
        Set<User> users = context.deserialize(obj.getAsJsonArray(USERS), new TypeToken<Set<User>>(){}.getType());
        Set<Project> projects = context.deserialize(obj.getAsJsonArray(PROJECTS), new TypeToken<Set<Project>>(){}.getType());
        Set<Role> roles = context.deserialize(obj.getAsJsonArray(ROLES), new TypeToken<Set<Role>>(){}.getType());
        Set<Operation> operations = context.deserialize(obj.getAsJsonArray(OPERATIONS), new TypeToken<Set<Operation>>(){}.getType());
        Set<AuthenticationDetails> authDetails = context.deserialize(obj.getAsJsonArray(AUTHENTICATION),
                new TypeToken<Set<AuthenticationDetails>>(){}.getType());
        Map<String,String> map = context.deserialize(obj.get(PROPERTIES), Map.class);
        return new ConfigurationBuilder()
                .setHost(host)
                .setServerRoot(root)
                .setPolicyMap(policy)
                .setUsers(users)
                .setProjects(projects)
                .setRoles(roles)
                .setOperations(operations)
                .setAuthenticationDetails(authDetails)
                .setProperties(map)
                .createServerConfiguration();
    }
}
