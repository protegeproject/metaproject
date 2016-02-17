package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import edu.stanford.protege.metaproject.api.EntityIriPrefix;
import edu.stanford.protege.metaproject.api.EntityIriStatus;
import edu.stanford.protege.metaproject.api.EntityNamePrefix;
import edu.stanford.protege.metaproject.api.EntityNameSuffix;
import edu.stanford.protege.metaproject.impl.EntityIriPrefixImpl;
import edu.stanford.protege.metaproject.impl.EntityIriStatusImpl;
import edu.stanford.protege.metaproject.impl.EntityNamePrefixImpl;
import edu.stanford.protege.metaproject.impl.EntityNameSuffixImpl;

import java.lang.reflect.Type;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class EntityIriStatusSerializer implements JsonSerializer<EntityIriStatus>, JsonDeserializer<EntityIriStatus> {

    @Override
    public JsonElement serialize(EntityIriStatus status, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("iriPrefix", context.serialize(status.getEntityIriPrefix().get()));
        if(status.getClassNamePrefix().isPresent()) {
            obj.add("classNamePrefix", context.serialize(status.getClassNamePrefix().get()));
        }
        if(status.getObjectPropertyNamePrefix().isPresent()) {
            obj.add("objectPropertyNamePrefix", context.serialize(status.getObjectPropertyNamePrefix().get()));
        }
        if(status.getDataPropertyNamePrefix().isPresent()) {
            obj.add("dataPropertyNamePrefix", context.serialize(status.getDataPropertyNamePrefix().get()));
        }
        if(status.getAnnotationPropertyNamePrefix().isPresent()) {
            obj.add("annotationPropertyNamePrefix", context.serialize(status.getAnnotationPropertyNamePrefix().get()));
        }
        if(status.getIndividualNamePrefix().isPresent()) {
            obj.add("individualNamePrefix", context.serialize(status.getIndividualNamePrefix().get()));
        }
        if(status.getClassNameSuffix().isPresent()) {
            obj.add("classNameSuffix", context.serialize(status.getClassNameSuffix().get()));
        }
        if(status.getObjectPropertyNameSuffix().isPresent()) {
            obj.add("objectPropertyNameSuffix", context.serialize(status.getObjectPropertyNameSuffix().get()));
        }
        if(status.getDataPropertyNameSuffix().isPresent()) {
            obj.add("dataPropertyNameSuffix", context.serialize(status.getDataPropertyNameSuffix().get()));
        }
        if(status.getAnnotationPropertyNameSuffix().isPresent()) {
            obj.add("annotationPropertyNameSuffix", context.serialize(status.getAnnotationPropertyNameSuffix().get()));
        }
        if(status.getIndividualNameSuffix().isPresent()) {
            obj.add("individualNameSuffix", context.serialize(status.getIndividualNameSuffix().get()));
        }
        return obj;
    }

    @Override
    public EntityIriStatus deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        EntityIriPrefix iriPrefix = context.deserialize(obj.getAsJsonPrimitive("iriPrefix"), EntityIriPrefixImpl.class);

        // prefixes
        EntityNamePrefix classNamePrefix = context.deserialize(obj.getAsJsonPrimitive("classNamePrefix"), EntityNamePrefixImpl.class);
        EntityNamePrefix objectPropertyNamePrefix = context.deserialize(obj.getAsJsonPrimitive("objectPropertyNamePrefix"), EntityNamePrefixImpl.class);
        EntityNamePrefix dataPropertyNamePrefix = context.deserialize(obj.getAsJsonPrimitive("dataPropertyNamePrefix"), EntityNamePrefixImpl.class);
        EntityNamePrefix annotationPropertyNamePrefix = context.deserialize(obj.getAsJsonPrimitive("annotationPropertyNamePrefix"), EntityNamePrefixImpl.class);
        EntityNamePrefix individualNamePrefix = context.deserialize(obj.getAsJsonPrimitive("individualNamePrefix"), EntityNamePrefixImpl.class);

        // suffixes
        EntityNameSuffix classNameSuffix = context.deserialize(obj.getAsJsonPrimitive("classNameSuffix"), EntityNameSuffixImpl.class);
        EntityNameSuffix objectPropertyNameSuffix = context.deserialize(obj.getAsJsonPrimitive("objectPropertyNameSuffix"), EntityNameSuffixImpl.class);
        EntityNameSuffix dataPropertyNameSuffix = context.deserialize(obj.getAsJsonPrimitive("dataPropertyNameSuffix"), EntityNameSuffixImpl.class);
        EntityNameSuffix annotationPropertyNameSuffix = context.deserialize(obj.getAsJsonPrimitive("annotationPropertyNameSuffix"), EntityNameSuffixImpl.class);
        EntityNameSuffix individualNameSuffix = context.deserialize(obj.getAsJsonPrimitive("individualNameSuffix"), EntityNameSuffixImpl.class);

        return new EntityIriStatusImpl.Builder()
                .setEntityIriPrefix(iriPrefix)
                .setClassNamePrefix(classNamePrefix)
                .setObjectPropertyNamePrefix(objectPropertyNamePrefix)
                .setDataPropertyNamePrefix(dataPropertyNamePrefix)
                .setObjectPropertyNamePrefix(objectPropertyNamePrefix)
                .setAnnotationPropertyNamePrefix(annotationPropertyNamePrefix)
                .setIndividualNamePrefix(individualNamePrefix)

                .setClassNameSuffix(classNameSuffix)
                .setObjectPropertyNameSuffix(objectPropertyNameSuffix)
                .setDataPropertyNameSuffix(dataPropertyNameSuffix)
                .setAnnotationPropertyNameSuffix(annotationPropertyNameSuffix)
                .setIndividualNameSuffix(individualNameSuffix)

                .createEntityIriStatus();
    }

}
