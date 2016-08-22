package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class OperationSerializerTest {
    private static final String operationIdStr = "testOperationId1", diffIdStr = "testOperationId2";
    private static final OperationId operationId = TestUtils.getOperationId(operationIdStr), diffOperationId = TestUtils.getOperationId(diffIdStr);
    private static final Name operationName = TestUtils.getName();
    private static final Description operationDescription = TestUtils.getDescription();
    private static final OperationType type = OperationType.READ;
    private final static Operation.Scope scope = Operation.Scope.POLICY;

    private String jsonOperation, jsonOtherOperation, jsonDiffOperation;
    private Operation operation, otherOperation, diffOperation;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getGson();

        operation = TestUtils.getSystemOperation(operationId, operationName, operationDescription, type, scope);
        otherOperation = TestUtils.getSystemOperation(operationId, operationName, operationDescription, type, scope);
        diffOperation = TestUtils.getSystemOperation(diffOperationId, operationName, operationDescription, type, scope);

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
}