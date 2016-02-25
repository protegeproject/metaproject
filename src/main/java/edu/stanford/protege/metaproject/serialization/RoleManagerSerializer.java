package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.Role;
import edu.stanford.protege.metaproject.api.RoleRegistry;
import edu.stanford.protege.metaproject.impl.RoleRegistryImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleManagerSerializer implements JsonSerializer<RoleRegistry>, JsonDeserializer<RoleRegistry> {

    @Override
    public JsonElement serialize(RoleRegistry roleRegistry, Type type, JsonSerializationContext context) {
        List<Role> list = new ArrayList<>(roleRegistry.getRoles());
        Collections.sort(list);
        return context.serialize(list);
    }

    @Override
    public RoleRegistry deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Set<Role> roles = context.deserialize(element.getAsJsonArray(), new TypeToken<Set<Role>>(){}.getType());
        return new RoleRegistryImpl(roles);
    }
}
