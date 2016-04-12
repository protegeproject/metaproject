package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerOperationTest {
    private static final String
            operationIdStr = "testOperationId1",
            otherIdStr = "testOperationId2",
            operationNameStr = "test operation name",
            otherOperationNameStr = "test operation name 2",
            operationDescriptionStr = "test operation description",
            toStringHead = ServerOperation.class.getSimpleName();

    private static final OperationId operationId = Utils.getOperationId(operationIdStr), diffOperationId = Utils.getOperationId(otherIdStr);
    private static final Name operationName = Utils.getName(operationNameStr), diffOperationName = Utils.getName(otherOperationNameStr);
    private static final Description operationDescription = Utils.getDescription(operationDescriptionStr);
    private static final OperationType operationType = OperationType.WRITE;
    private Operation operation, otherOperation, diffOperation;

    @Before
    public void setUp() {
        operation = Utils.getServerOperation(operationId, operationName, operationDescription, operationType);
        otherOperation = Utils.getServerOperation(operationId, operationName, operationDescription, operationType);
        diffOperation = Utils.getServerOperation(diffOperationId, diffOperationName, operationDescription, operationType);
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
    public void testIsOntologyOperation() {
        assertThat(operation.isOntologyOperation(), is(false));
    }

    @Test
    public void testIsServerOperation() {
        assertThat(operation.isServerOperation(), is(true));
    }

    @Test
    public void testIsMetaprojectOperation() {
        assertThat(operation.isMetaprojectOperation(), is(false));
    }
}