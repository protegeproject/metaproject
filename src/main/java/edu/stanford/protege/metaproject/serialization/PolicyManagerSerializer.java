package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.PolicyManager;
import edu.stanford.protege.metaproject.api.RoleId;
import edu.stanford.protege.metaproject.api.UserId;
import edu.stanford.protege.metaproject.api.impl.PolicyManagerImpl;
import edu.stanford.protege.metaproject.api.impl.RoleIdImpl;
import edu.stanford.protege.metaproject.api.impl.UserIdImpl;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PolicyManagerSerializer implements JsonSerializer<PolicyManager>, JsonDeserializer<PolicyManager> {

    @Override
    public PolicyManager deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Map<UserId, Set<RoleId>> map = context.deserialize(element, new TypeToken<Map<UserIdImpl,Set<RoleIdImpl>>>() {}.getType());
        return new PolicyManagerImpl(map);
    }

    @Override
    public JsonElement serialize(PolicyManager policyManager, Type type, JsonSerializationContext context) {
        return context.serialize(policyManager.getUserRoleMappings());
    }
}
