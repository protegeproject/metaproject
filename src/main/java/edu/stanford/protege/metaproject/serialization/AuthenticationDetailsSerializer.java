package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.AuthenticationDetails;
import edu.stanford.protege.metaproject.api.Salt;
import edu.stanford.protege.metaproject.api.SaltedPasswordDigest;
import edu.stanford.protege.metaproject.api.UserId;
import edu.stanford.protege.metaproject.impl.AuthenticationDetailsImpl;
import edu.stanford.protege.metaproject.impl.SaltImpl;
import edu.stanford.protege.metaproject.impl.SaltedPasswordDigestImpl;
import edu.stanford.protege.metaproject.impl.UserIdImpl;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AuthenticationDetailsSerializer implements JsonSerializer<AuthenticationDetails>, JsonDeserializer<AuthenticationDetails> {
    private final String USER_ID = "userId", SALT = "salt", PASSWORD = "password";
    
    @Override
    public AuthenticationDetails deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        UserId userId = new UserIdImpl(obj.getAsJsonPrimitive(USER_ID).getAsString());
        Salt salt = new SaltImpl(obj.getAsJsonPrimitive(SALT).getAsString());
        SaltedPasswordDigest password = new SaltedPasswordDigestImpl(obj.getAsJsonPrimitive(PASSWORD).getAsString(), salt);
        return new AuthenticationDetailsImpl(userId, password);
    }

    @Override
    public JsonElement serialize(AuthenticationDetails details, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add(USER_ID, context.serialize(details.getUserId()));
        obj.add(PASSWORD, context.serialize(details.getPassword().getPassword()));
        obj.add(SALT, context.serialize(details.getPassword().getSalt().getString()));
        return obj;
    }
}
