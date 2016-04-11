package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.Operation;
import edu.stanford.protege.metaproject.api.OperationRegistry;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationRegistrySerializerTest {
    private static final Set<Operation> operationSet = Utils.getOperationSet(5), diffOperationSet = Utils.getOperationSet(3);

    private String jsonOperationManager, jsonOtherOperationManager, jsonDiffOperationManager;
    private OperationRegistry operationRegistry, otherOperationRegistry, diffOperationRegistry;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        operationRegistry = Utils.getOperationManager(operationSet);
        otherOperationRegistry = Utils.getOperationManager(operationSet);
        diffOperationRegistry = Utils.getOperationManager(diffOperationSet);

        jsonOperationManager = gson.toJson(operationRegistry, OperationRegistry.class);
        jsonOtherOperationManager = gson.toJson(otherOperationRegistry, OperationRegistry.class);
        jsonDiffOperationManager = gson.toJson(diffOperationRegistry, OperationRegistry.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonOperationManager, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonOperationManager, OperationRegistry.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(operationRegistry, is(gson.fromJson(jsonOperationManager, OperationRegistry.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(operationRegistry, is(otherOperationRegistry));
        assertThat(jsonOperationManager, is(jsonOtherOperationManager));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(operationRegistry, is(not(diffOperationRegistry)));
        assertThat(jsonOperationManager, is(not(gson.toJson(diffOperationRegistry, OperationRegistry.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonOperationManager, OperationRegistry.class), is(gson.fromJson(jsonOtherOperationManager, OperationRegistry.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonOperationManager, OperationRegistry.class), is(not(gson.fromJson(jsonDiffOperationManager, OperationRegistry.class))));
    }

    @Test
    public void testGetOperationsNotNull() {
        assertThat(gson.fromJson(jsonOperationManager, OperationRegistry.class).getEntries(), is(not(equalTo(null))));
    }

    @Test
    public void testGetOperations() {
        assertThat(gson.fromJson(jsonOperationManager, OperationRegistry.class).getEntries(), is(operationSet));
    }
}