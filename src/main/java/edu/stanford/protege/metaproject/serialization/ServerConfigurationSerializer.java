package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.Policy;
import edu.stanford.protege.metaproject.api.ServerConfiguration;
import edu.stanford.protege.metaproject.api.impl.AccessControlServerConfiguration;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerConfigurationSerializer implements JsonSerializer<ServerConfiguration>, JsonDeserializer<ServerConfiguration> {

    @Override
    public ServerConfiguration deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Host host = context.deserialize(obj.get("host"), HostSerializer.class);
        Policy policy = context.deserialize(obj.get("policy"), AccessControlPolicySerializer.class);
        return new AccessControlServerConfiguration.Builder()
                .setHost(host)
                .setPolicy(policy)
                .createAccessControlServerConfiguration();
    }

    @Override
    public JsonElement serialize(ServerConfiguration config, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("host", context.serialize(config.getHost()));
        obj.add("policy", context.serialize(config.getPolicy(), AccessControlPolicySerializer.class));
        return obj;
    }
}
