package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.User;
import edu.stanford.protege.metaproject.api.UserManager;
import edu.stanford.protege.metaproject.impl.UserManagerImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserManagerSerializer implements JsonSerializer<UserManager>, JsonDeserializer<UserManager> {

    @Override
    public JsonElement serialize(UserManager userManager, Type type, JsonSerializationContext context) {
        List<User> list = new ArrayList<>(userManager.getUsers());
        Collections.sort(list);
        return context.serialize(list);
    }

    @Override
    public UserManager deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Set<User> users = context.deserialize(element.getAsJsonArray(), new TypeToken<Set<User>>(){}.getType());
        return new UserManagerImpl(users);
    }
}