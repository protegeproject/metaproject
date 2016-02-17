package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.Role;
import edu.stanford.protege.metaproject.api.RoleManager;
import edu.stanford.protege.metaproject.impl.RoleManagerImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleManagerSerializer implements JsonSerializer<RoleManager>, JsonDeserializer<RoleManager> {

    @Override
    public JsonElement serialize(RoleManager roleManager, Type type, JsonSerializationContext context) {
        List<Role> list = new ArrayList<>(roleManager.getRoles());
        Collections.sort(list);
        return context.serialize(list);
    }

    @Override
    public RoleManager deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Set<Role> roles = context.deserialize(element.getAsJsonArray(), new TypeToken<Set<Role>>(){}.getType());
        return new RoleManagerImpl(roles);
    }
}
