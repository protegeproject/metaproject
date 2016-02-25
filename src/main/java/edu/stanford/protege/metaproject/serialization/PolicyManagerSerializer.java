package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.Policy;
import edu.stanford.protege.metaproject.api.ProjectId;
import edu.stanford.protege.metaproject.api.RoleId;
import edu.stanford.protege.metaproject.api.UserId;
import edu.stanford.protege.metaproject.impl.PolicyImpl;
import edu.stanford.protege.metaproject.impl.ProjectIdImpl;
import edu.stanford.protege.metaproject.impl.RoleIdImpl;
import edu.stanford.protege.metaproject.impl.UserIdImpl;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PolicyManagerSerializer implements JsonSerializer<Policy>, JsonDeserializer<Policy> {

    @Override
    public Policy deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Map<UserId, Map<ProjectId, Set<RoleId>>> map = context.deserialize(element, new TypeToken<Map<UserIdImpl,Map<ProjectIdImpl,Set<RoleIdImpl>>>>() {}.getType());
        return new PolicyImpl(map);
    }

    @Override
    public JsonElement serialize(Policy policy, Type type, JsonSerializationContext context) {
        return context.serialize(policy.getPolicyMappings());
    }
}
