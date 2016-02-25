package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.Address;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.Port;
import edu.stanford.protege.metaproject.api.RegistryPort;
import edu.stanford.protege.metaproject.impl.AddressImpl;
import edu.stanford.protege.metaproject.impl.HostImpl;
import edu.stanford.protege.metaproject.impl.PortImpl;
import edu.stanford.protege.metaproject.impl.RegistryPortImpl;

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
        Port port = new PortImpl(obj.getAsJsonPrimitive("port").getAsInt());
        RegistryPort registryPort = new RegistryPortImpl(obj.getAsJsonPrimitive("registryPort").getAsInt());
        return new HostImpl(address, port, registryPort);
    }

    @Override
    public JsonElement serialize(Host host, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("address", context.serialize(host.getAddress()));
        obj.add("port", context.serialize(host.getPort()));
        obj.add("registryPort", context.serialize(host.getRegistryPort()));
        return obj;
    }
}
