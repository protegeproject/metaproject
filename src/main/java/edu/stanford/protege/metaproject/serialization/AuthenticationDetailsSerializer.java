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

    @Override
    public AuthenticationDetails deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        UserId userId = new UserIdImpl(obj.getAsJsonPrimitive("userId").getAsString());
        Salt salt = new SaltImpl(obj.getAsJsonPrimitive("salt").getAsString());
        SaltedPasswordDigest password = new SaltedPasswordDigestImpl(obj.getAsJsonPrimitive("password").getAsString(), salt);
        return new AuthenticationDetailsImpl(userId, password);
    }

    @Override
    public JsonElement serialize(AuthenticationDetails details, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("userId", context.serialize(details.getUserId()));
        obj.add("password", context.serialize(details.getPassword().getPassword()));
        obj.add("salt", context.serialize(details.getPassword().getSalt().getString()));
        return obj;
    }
}
