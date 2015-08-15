package edu.stanford.protege.metaproject.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.semanticweb.owlapi.model.OWLEntity;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OWLEntitySerializer implements JsonSerializer<OWLEntity> {

    @Override
    public JsonElement serialize(OWLEntity entity, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(entity.getIRI().toString());
    }
}
