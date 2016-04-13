package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.MetaprojectFactory;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.Port;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class HostSerializer implements JsonSerializer<Host>, JsonDeserializer<Host> {
    private final String SERVER_URI = "uri", SECONDARY_PORT = "secondaryPort";

    @Override
    public JsonElement serialize(Host host, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add(SERVER_URI, context.serialize(host.getUri()));
        if(host.getSecondaryPort().isPresent()) {
            obj.add(SECONDARY_PORT, context.serialize(host.getSecondaryPort().get()));
        }
        return obj;
    }

    @Override
    public Host deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        MetaprojectFactory factory = Manager.getFactory();
        JsonObject obj = element.getAsJsonObject();
        URI address = null;
        try {
            address = factory.getUri(obj.getAsJsonPrimitive(SERVER_URI).getAsString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Optional<Port> optionalPort = (obj.getAsJsonPrimitive(SECONDARY_PORT) != null ?
                Optional.ofNullable(factory.getPort(obj.getAsJsonPrimitive(SECONDARY_PORT).getAsInt())) :
                Optional.empty());
        return factory.getHost(address, optionalPort);
    }
}
