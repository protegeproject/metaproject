package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.AuthenticationDetails;
import edu.stanford.protege.metaproject.api.AuthenticationRegistry;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AuthenticationRegistrySerializer implements JsonSerializer<AuthenticationRegistry>, JsonDeserializer<AuthenticationRegistry> {

    @Override
    public JsonElement serialize(AuthenticationRegistry authenticationRegistry, Type type, JsonSerializationContext context) {
        List<AuthenticationDetails> list = new ArrayList<>(authenticationRegistry.getAuthenticationDetails());
        Collections.sort(list);
        return context.serialize(list);
    }

    @Override
    public AuthenticationRegistry deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Set<AuthenticationDetails> authenticationDetails =
                context.deserialize(element.getAsJsonArray(), new TypeToken<Set<AuthenticationDetails>>(){}.getType());
        return Manager.getFactory().getAuthenticationRegistry(authenticationDetails);
    }
}
