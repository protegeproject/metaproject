package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.IdPrefix;
import edu.stanford.protege.metaproject.api.IdSuffix;
import edu.stanford.protege.metaproject.api.OntologyTermIdStatus;
import edu.stanford.protege.metaproject.api.impl.OntologyTermIdPrefix;
import edu.stanford.protege.metaproject.api.impl.OntologyTermIdStatusImpl;
import edu.stanford.protege.metaproject.api.impl.OntologyTermIdSuffix;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OntologyTermIdStatusSerializer implements JsonSerializer<OntologyTermIdStatus>, JsonDeserializer<OntologyTermIdStatus> {

    @Override
    public JsonElement serialize(OntologyTermIdStatus status, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        if(status.getClassIdPrefix().isPresent()) {
            obj.add("classIdPrefix", context.serialize(status.getClassIdPrefix().get()));
        }
        if(status.getObjectPropertyIdPrefix().isPresent()) {
            obj.add("objectPropertyIdPrefix", context.serialize(status.getObjectPropertyIdPrefix().get()));
        }
        if(status.getDataPropertyIdPrefix().isPresent()) {
            obj.add("dataPropertyIdPrefix", context.serialize(status.getDataPropertyIdPrefix().get()));
        }
        if(status.getAnnotationPropertyIdPrefix().isPresent()) {
            obj.add("annotationPropertyIdPrefix", context.serialize(status.getAnnotationPropertyIdPrefix().get()));
        }
        if(status.getIndividualIdPrefix().isPresent()) {
            obj.add("individualIdPrefix", context.serialize(status.getIndividualIdPrefix().get()));
        }
        if(status.getClassIdSuffix().isPresent()) {
            obj.add("classIdSuffix", context.serialize(status.getClassIdSuffix().get()));
        }
        if(status.getObjectPropertyIdSuffix().isPresent()) {
            obj.add("objectPropertyIdSuffix", context.serialize(status.getObjectPropertyIdSuffix().get()));
        }
        if(status.getDataPropertyIdSuffix().isPresent()) {
            obj.add("dataPropertyIdSuffix", context.serialize(status.getDataPropertyIdSuffix().get()));
        }
        if(status.getAnnotationPropertyIdSuffix().isPresent()) {
            obj.add("annotationPropertyIdSuffix", context.serialize(status.getAnnotationPropertyIdSuffix().get()));
        }
        if(status.getIndividualIdSuffix().isPresent()) {
            obj.add("individualIdSuffix", context.serialize(status.getIndividualIdSuffix().get()));
        }
        return obj;
    }

    @Override
    public OntologyTermIdStatus deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();

        // prefixes
        IdPrefix classIdPrefix = context.deserialize(obj.getAsJsonPrimitive("classIdPrefix"), OntologyTermIdPrefix.class);
        IdPrefix objectPropertyIdPrefix = context.deserialize(obj.getAsJsonPrimitive("objectPropertyIdPrefix"), OntologyTermIdPrefix.class);
        IdPrefix dataPropertyIdPrefix = context.deserialize(obj.getAsJsonPrimitive("dataPropertyIdPrefix"), OntologyTermIdPrefix.class);
        IdPrefix annotationPropertyIdPrefix = context.deserialize(obj.getAsJsonPrimitive("annotationPropertyIdPrefix"), OntologyTermIdPrefix.class);
        IdPrefix individualIdPrefix = context.deserialize(obj.getAsJsonPrimitive("individualIdPrefix"), OntologyTermIdPrefix.class);

        // suffixes
        IdSuffix classIdSuffix = context.deserialize(obj.getAsJsonPrimitive("classIdSuffix"), OntologyTermIdSuffix.class);
        IdSuffix objectPropertyIdSuffix = context.deserialize(obj.getAsJsonPrimitive("objectPropertyIdSuffix"), OntologyTermIdSuffix.class);
        IdSuffix dataPropertyIdSuffix = context.deserialize(obj.getAsJsonPrimitive("dataPropertyIdSuffix"), OntologyTermIdSuffix.class);
        IdSuffix annotationPropertyIdSuffix = context.deserialize(obj.getAsJsonPrimitive("annotationPropertyIdSuffix"), OntologyTermIdSuffix.class);
        IdSuffix individualIdSuffix = context.deserialize(obj.getAsJsonPrimitive("individualIdSuffix"), OntologyTermIdSuffix.class);

        return new OntologyTermIdStatusImpl.Builder()
                .setClassIdPrefix(classIdPrefix)
                .setObjectPropertyIdPrefix(objectPropertyIdPrefix)
                .setDataPropertyIdPrefix(dataPropertyIdPrefix)
                .setObjectPropertyIdPrefix(objectPropertyIdPrefix)
                .setAnnotationPropertyIdPrefix(annotationPropertyIdPrefix)
                .setIndividualIdPrefix(individualIdPrefix)

                .setClassIdSuffix(classIdSuffix)
                .setObjectPropertyIdSuffix(objectPropertyIdSuffix)
                .setDataPropertyIdSuffix(dataPropertyIdSuffix)
                .setAnnotationPropertyIdSuffix(annotationPropertyIdSuffix)
                .setIndividualIdSuffix(individualIdSuffix)

                .createOntologyTermIdStatus();
    }

}
