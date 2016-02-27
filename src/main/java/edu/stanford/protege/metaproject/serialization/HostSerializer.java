package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class HostSerializer implements JsonSerializer<Host>, JsonDeserializer<Host> {

    @Override
    public Host deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Factory factory = Manager.getFactory();
        JsonObject obj = element.getAsJsonObject();
        Address address = factory.createAddress(obj.getAsJsonPrimitive("address").getAsString());
        Port port = factory.createPort(obj.getAsJsonPrimitive("port").getAsInt());
        RegistryPort registryPort = factory.createRegistryPort(obj.getAsJsonPrimitive("registryPort").getAsInt());
        return factory.createHost(address, port, registryPort);
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
