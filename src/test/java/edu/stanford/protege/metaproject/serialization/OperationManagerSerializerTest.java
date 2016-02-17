package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.Operation;
import edu.stanford.protege.metaproject.api.OperationManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationManagerSerializerTest {
    private static final Set<Operation> operationSet = Utils.getOperationSet(5), diffOperationSet = Utils.getOperationSet(3);

    private String jsonOperationManager, jsonOtherOperationManager, jsonDiffOperationManager;
    private OperationManager operationManager, otherOperationManager, diffOperationManager;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultGsonSerializer().getDefaultSerializer();

        operationManager = Utils.getOperationManager(operationSet);
        otherOperationManager = Utils.getOperationManager(operationSet);
        diffOperationManager = Utils.getOperationManager(diffOperationSet);

        jsonOperationManager = gson.toJson(operationManager, OperationManager.class);
        jsonOtherOperationManager = gson.toJson(otherOperationManager, OperationManager.class);
        jsonDiffOperationManager = gson.toJson(diffOperationManager, OperationManager.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonOperationManager, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonOperationManager, OperationManager.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(operationManager, is(gson.fromJson(jsonOperationManager, OperationManager.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(operationManager, is(otherOperationManager));
        assertThat(jsonOperationManager, is(jsonOtherOperationManager));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(operationManager, is(not(diffOperationManager)));
        assertThat(jsonOperationManager, is(not(gson.toJson(diffOperationManager, OperationManager.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonOperationManager, OperationManager.class), is(gson.fromJson(jsonOtherOperationManager, OperationManager.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonOperationManager, OperationManager.class), is(not(gson.fromJson(jsonDiffOperationManager, OperationManager.class))));
    }

    @Test
    public void testGetOperationsNotNull() {
        assertThat(gson.fromJson(jsonOperationManager, OperationManager.class).getOperations(), is(not(equalTo(null))));
    }

    @Test
    public void testGetOperations() {
        assertThat(gson.fromJson(jsonOperationManager, OperationManager.class).getOperations(), is(operationSet));
    }
}