package edu.stanford.protege.metaproject.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.stanford.protege.metaproject.api.Operation;
import edu.stanford.protege.metaproject.api.OperationManager;
import edu.stanford.protege.metaproject.impl.OperationManagerImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationManagerSerializer implements JsonSerializer<OperationManager>, JsonDeserializer<OperationManager> {

    @Override
    public JsonElement serialize(OperationManager operationManager, Type type, JsonSerializationContext context) {
        List<Operation> list = new ArrayList<>(operationManager.getOperations());
        Collections.sort(list);
        return context.serialize(list);
    }

    @Override
    public OperationManager deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Set<Operation> operations = context.deserialize(element.getAsJsonArray(), new TypeToken<Set<Operation>>(){}.getType());
        return new OperationManagerImpl(operations);
    }
}
