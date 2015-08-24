package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.Address;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.impl.AddressImpl;
import edu.stanford.protege.metaproject.api.impl.HostImpl;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class HostSerializer implements JsonSerializer<Host>, JsonDeserializer<Host> {

    @Override
    public Host deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        Address address = new AddressImpl(obj.getAsJsonPrimitive("address").getAsString());
        int port = obj.getAsJsonPrimitive("port").getAsInt();
        return new HostImpl(address, port);
    }

    @Override
    public JsonElement serialize(Host host, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("address", context.serialize(host.getAddress()));
        obj.add("port", context.serialize(host.getPort()));
        return obj;
    }
}
