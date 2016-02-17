package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.impl.*;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserSerializer implements JsonDeserializer<User> {

    @Override
    public User deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        UserId userId = new UserIdImpl(obj.getAsJsonPrimitive("id").getAsString());
        Name userName = new NameImpl(obj.getAsJsonPrimitive("name").getAsString());
        EmailAddress email = new EmailAddressImpl(obj.getAsJsonPrimitive("emailAddress").getAsString());
        return new UserImpl(userId, userName, email);
    }
}
