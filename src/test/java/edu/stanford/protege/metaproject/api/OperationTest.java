package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
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
public class OperationTest {
    private static final String
            operationIdStr = "testOperationId1",
            otherIdStr = "testOperationId2",
            operationNameStr = "test operation name",
            operationDescriptionStr = "test operation description",
            toStringHead = "Operation";

    private static final OperationId operationId = Utils.getOperationId(operationIdStr), diffOperationId = Utils.getOperationId(otherIdStr);
    private static final Name operationName = Utils.getName(operationNameStr);
    private static final Description operationDescription = Utils.getDescription(operationDescriptionStr);
    private static final OperationType operationType = OperationType.POLICY;
    private static final OperationPrerequisite prerequisite = Utils.getOperationPrerequisite(OperationPrerequisite.Modifier.ABSENT);
    private static final Set<OperationPrerequisite> prerequisites = Utils.getOperationPrerequisiteSet(prerequisite);
    private Operation operation, otherOperation, diffOperation;

    @Before
    public void setUp() {
        operation = Utils.getOperation(operationId, operationName, operationDescription, operationType, Optional.of(prerequisites));
        otherOperation = Utils.getOperation(operationId, operationName, operationDescription, operationType, Optional.of(prerequisites));
        diffOperation = Utils.getOperation(diffOperationId, operationName, operationDescription, operationType, Optional.of(prerequisites));
    }

    @Test
    public void testNotNull() {
        assertThat(operation, is(not(equalTo(null))));
    }

    @Test
    public void testGetId() {
        assertThat(operation.getId().get(), is(operationIdStr));
    }

    @Test
    public void testGetName() {
        assertThat(operation.getName().get(), is(operationNameStr));
    }

    @Test
    public void testGetDescription() {
        assertThat(operation.getDescription().get(), is(operationDescriptionStr));
    }

    @Test
    public void testGetType() {
        assertThat(operation.getType(), is(operationType));
    }

    @Test
    public void testGetPrerequisites() {
        assertThat(operation.getPrerequisites().get(), is(prerequisites));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(operation, is(operation));
    }

    @Test
    public void testEquals() {
        assertThat(operation, is(otherOperation));
    }

    @Test
    public void testNotEquals() {
        assertThat(operation, is(not(diffOperation)));
    }

    @Test
    public void testHashCode() {
        assertThat(operation.hashCode(), is(otherOperation.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(operation.toString(), startsWith(toStringHead));
    }

    @Test
    public void testCompareToSelf() {
        assertThat(operation.compareTo(operation), is(0));
    }

    @Test
    public void testCompareTo() {
        assertThat(operation.compareTo(otherOperation), is(0));
    }

    @Test
    public void testCompareToAnother() {
        assertThat(operation.compareTo(diffOperation), is(-1));
    }

    @Test
    public void testCompareToAnotherReversed() {
        assertThat(diffOperation.compareTo(operation), is(1));
    }
}