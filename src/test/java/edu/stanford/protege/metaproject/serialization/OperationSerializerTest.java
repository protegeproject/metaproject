package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationSerializerTest {
    private static final String operationIdStr = "testOperationId1", diffIdStr = "testOperationId2";
    private static final OperationId operationId = Utils.getOperationId(operationIdStr), diffOperationId = Utils.getOperationId(diffIdStr);
    private static final Name operationName = Utils.getName();
    private static final Description operationDescription = Utils.getDescription();
    private static final OperationType type = OperationType.READ;
    private static final Set<OperationPrerequisite> prerequisites = Utils.getOperationPrerequisiteSet(Utils.getOperationPrerequisite());

    private String jsonOperation, jsonOtherOperation, jsonDiffOperation;
    private Operation operation, otherOperation, diffOperation;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        operation = Utils.getOperation(operationId, operationName, operationDescription, type, Optional.of(prerequisites));
        otherOperation = Utils.getOperation(operationId, operationName, operationDescription, type, Optional.of(prerequisites));
        diffOperation = Utils.getOperation(diffOperationId, operationName, operationDescription, type, Optional.of(prerequisites));

        jsonOperation = gson.toJson(operation);
        jsonOtherOperation = gson.toJson(otherOperation);
        jsonDiffOperation = gson.toJson(diffOperation);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonOperation, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonOperation, Operation.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(operation, is(gson.fromJson(jsonOperation, Operation.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(operation, is(otherOperation));
        assertThat(jsonOperation, is(jsonOtherOperation));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(operation, is(not(diffOperation)));
        assertThat(jsonOperation, is(not(gson.toJson(diffOperation))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonOperation, Operation.class), is(gson.fromJson(jsonOtherOperation, Operation.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonOperation, Operation.class), is(not(gson.fromJson(jsonDiffOperation, Operation.class))));
    }

    @Test
    public void testGetId() {
        assertThat(gson.fromJson(jsonOperation, Operation.class).getId(), is(operationId));
    }

    @Test
    public void testGetName() {
        assertThat(gson.fromJson(jsonOperation, Operation.class).getName(), is(operationName));
    }

    @Test
    public void testGetDescription() {
        assertThat(gson.fromJson(jsonOperation, Operation.class).getDescription(), is(operationDescription));
    }

    @Test
    public void testGetOperationType() {
        assertThat(gson.fromJson(jsonOperation, Operation.class).getType(), is(type));
    }

    @Test
    public void testGetPrerequisites() {
        assertThat(gson.fromJson(jsonOperation, Operation.class).getPrerequisites().get(), is(prerequisites));
    }
}