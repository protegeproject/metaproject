package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.ChangeModality;
import edu.stanford.protege.metaproject.api.OperationRestriction;
import edu.stanford.protege.metaproject.impl.AxiomChangeModality;
import org.semanticweb.owlapi.model.AxiomType;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationRestrictionSerializer implements JsonSerializer<OperationRestriction>, JsonDeserializer<OperationRestriction> {
    private final String AXIOM_TYPE = "axiomType", MODALITY = "modality";
    
    @Override
    public JsonElement serialize(OperationRestriction restriction, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty(AXIOM_TYPE, restriction.getRestriction().toString());
        object.addProperty(MODALITY, restriction.getModality().toString());
        return object;
    }

    @Override
    public OperationRestriction deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = element.getAsJsonObject();
        AxiomType t = AxiomType.getAxiomType(object.getAsJsonPrimitive(AXIOM_TYPE).getAsString());
        ChangeModality modality = AxiomChangeModality.valueOf(object.getAsJsonPrimitive(MODALITY).getAsString());
        return Manager.getFactory().createAxiomTypeOperationRestriction(t, modality);
    }
}
