package edu.stanford.protege.metaproject.impl;

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
public class SystemOperationTest {
    private static final String
            operationIdStr = "testOperationId1",
            otherIdStr = "testOperationId2",
            operationNameStr = "test operation name",
            otherOperationNameStr = "test operation name 2",
            operationDescriptionStr = "test operation description",
            toStringHead = SystemOperation.class.getSimpleName();

    private static final OperationId operationId = TestUtils.getOperationId(operationIdStr), diffOperationId = TestUtils.getOperationId(otherIdStr);
    private static final Name operationName = TestUtils.getName(operationNameStr), diffOperationName = TestUtils.getName(otherOperationNameStr);
    private static final Description operationDescription = TestUtils.getDescription(operationDescriptionStr);
    private static final OperationType operationType = OperationType.WRITE;
    private final static Operation.Scope scope = Operation.Scope.POLICY;
    private Operation operation, otherOperation, diffOperation;

    @Before
    public void setUp() {
        operation = TestUtils.getSystemOperation(operationId, operationName, operationDescription, operationType, scope);
        otherOperation = TestUtils.getSystemOperation(operationId, operationName, operationDescription, operationType, scope);
        diffOperation = TestUtils.getSystemOperation(diffOperationId, diffOperationName, operationDescription, operationType, scope);
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

    @Test
    public void testIsMetaprojectOperation() {
        assertThat(operation.isSystemOperation(), is(true));
    }
}