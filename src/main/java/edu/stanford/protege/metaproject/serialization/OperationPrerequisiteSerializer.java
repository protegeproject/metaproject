package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.OperationPrerequisite;
import edu.stanford.protege.metaproject.api.impl.OWLEntityOperationPrerequisite;
import org.semanticweb.owlapi.model.IRI;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationPrerequisiteSerializer implements JsonSerializer<OperationPrerequisite>, JsonDeserializer<OperationPrerequisite> {

    @Override
    public JsonElement serialize(OperationPrerequisite prerequisite, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("prerequisite", prerequisite.getPrerequisite().toString());
        object.addProperty("modifier", prerequisite.getModifier().toString());
        return object;
    }

    @Override
    public OperationPrerequisite deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = element.getAsJsonObject();
        IRI iri = IRI.create(object.getAsJsonPrimitive("prerequisite").getAsString());
        OperationPrerequisite.Modifier modifier = OperationPrerequisite.Modifier.valueOf(object.getAsJsonPrimitive("modifier").getAsString());
        return new OWLEntityOperationPrerequisite(iri, modifier);
    }
}
