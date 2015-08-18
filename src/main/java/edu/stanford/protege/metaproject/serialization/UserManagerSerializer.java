package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.User;
import edu.stanford.protege.metaproject.api.impl.UserManager;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserManagerSerializer implements JsonSerializer<UserManager>, JsonDeserializer<UserManager> {

    @Override
    public JsonElement serialize(UserManager userManager, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("users", context.serialize(userManager.getUsers()));
        return object;
    }

    @Override
    public UserManager deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        Set<User> users = context.deserialize(obj.getAsJsonArray("users"), Set.class);
        return UserManager.getInstance(users);
    }
}
