package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
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
        return context.serialize(userManager.getUsers());
    }

    @Override
    public UserManager deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Set<User> users = context.deserialize(element.getAsJsonArray(), new TypeToken<Set<User>>(){}.getType());
        return new UserManager(users);
    }
}
