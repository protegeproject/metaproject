package edu.stanford.protege.metaproject.deserialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.impl.AccessControlObjectAddress;
import edu.stanford.protege.metaproject.api.impl.AccessControlObjectIdImpl;
import edu.stanford.protege.metaproject.api.impl.AccessControlObjectName;
import edu.stanford.protege.metaproject.api.impl.UserImpl;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserDeserializer implements JsonDeserializer<User> {

    @Override
    public User deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Id userId = new AccessControlObjectIdImpl(obj.getAsJsonPrimitive("id").getAsString());
        Name userName = new AccessControlObjectName(obj.getAsJsonPrimitive("name").getAsString());
        Address email = new AccessControlObjectAddress(obj.getAsJsonPrimitive("emailAddress").getAsString());
        return new UserImpl(userId, userName, email);
    }
}
