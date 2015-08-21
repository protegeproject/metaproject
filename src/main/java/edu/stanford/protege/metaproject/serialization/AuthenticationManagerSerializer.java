package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.UserAuthenticationDetails;
import edu.stanford.protege.metaproject.api.impl.AuthenticationManager;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AuthenticationManagerSerializer implements JsonSerializer<AuthenticationManager>, JsonDeserializer<AuthenticationManager> {

    @Override
    public JsonElement serialize(AuthenticationManager authenticationManager, Type type, JsonSerializationContext context) {
        return context.serialize(authenticationManager.getUserAuthenticationDetails());
    }

    @Override
    public AuthenticationManager deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Set<UserAuthenticationDetails> userAuthenticationDetails =
                context.deserialize(element.getAsJsonArray(), new TypeToken<Set<UserAuthenticationDetails>>() {}.getType());
        return new AuthenticationManager(userAuthenticationDetails);
    }
}
