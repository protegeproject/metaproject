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
    private final String ADDRESS = "address", PORT = "port", REGISTRY_PORT = "registryPort";

    @Override
    public JsonElement serialize(Host host, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add(ADDRESS, context.serialize(host.getAddress()));
        obj.add(PORT, context.serialize(host.getPort()));
        obj.add(REGISTRY_PORT, context.serialize(host.getRegistryPort()));
        return obj;
    }

    @Override
    public Host deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Factory factory = Manager.getFactory();
        JsonObject obj = element.getAsJsonObject();
        Address address = factory.getAddress(obj.getAsJsonPrimitive(ADDRESS).getAsString());
        Port port = factory.getPort(obj.getAsJsonPrimitive(PORT).getAsInt());
        RegistryPort registryPort = factory.getRegistryPort(obj.getAsJsonPrimitive(REGISTRY_PORT).getAsInt());
        return factory.getHost(address, port, registryPort);
    }
}
