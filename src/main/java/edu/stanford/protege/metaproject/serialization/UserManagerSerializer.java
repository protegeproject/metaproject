package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.impl.UserManager;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserManagerSerializer implements JsonSerializer<UserManager> {

    @Override
    public JsonElement serialize(UserManager userManager, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("users", context.serialize(userManager.getUsers()));
        return object;
    }
}
