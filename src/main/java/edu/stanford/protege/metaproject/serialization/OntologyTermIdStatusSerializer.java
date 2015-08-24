package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.OntologyTermIdStatus;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OntologyTermIdStatusSerializer implements JsonSerializer<OntologyTermIdStatus>, JsonDeserializer<OntologyTermIdStatus> {

    @Override
    public OntologyTermIdStatus deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(OntologyTermIdStatus idStatus, Type type, JsonSerializationContext context) {
        return null;
    }
}
