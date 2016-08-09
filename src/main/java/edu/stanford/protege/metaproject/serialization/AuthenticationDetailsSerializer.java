package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AuthenticationDetailsSerializer implements JsonSerializer<AuthenticationDetails>, JsonDeserializer<AuthenticationDetails> {
    private final String USER_ID = "userId", SALT = "salt", PASSWORD = "password";

    @Override
    public JsonElement serialize(AuthenticationDetails details, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add(USER_ID, context.serialize(details.getUserId()));
        obj.add(PASSWORD, context.serialize(details.getPassword().getPassword()));
        obj.add(SALT, context.serialize(details.getPassword().getSalt().getString()));
        return obj;
    }

    @Override
    public AuthenticationDetails deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        PolicyFactory factory = Manager.getFactory();
        JsonObject obj = element.getAsJsonObject();
        UserId userId = factory.getUserId(obj.getAsJsonPrimitive(USER_ID).getAsString());
        Salt salt = factory.getSalt(obj.getAsJsonPrimitive(SALT).getAsString());
        SaltedPasswordDigest password = factory.getSaltedPasswordDigest(obj.getAsJsonPrimitive(PASSWORD).getAsString(), salt);
        return factory.getAuthenticationDetails(userId, password);
    }
}
