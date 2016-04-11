package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.IdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationRegistryTest {
    private static final Operation operation1 = Utils.getServerOperation(), operation2 = Utils.getServerOperation(), operation3 = Utils.getServerOperation(), operation4 = Utils.getServerOperation();
    private static final Set<Operation> operationSet = Utils.getOperationSet(operation1, operation2, operation3, operation4);
    private static final String toStringHead = OperationRegistry.class.getSimpleName();

    private OperationRegistry operationRegistry, otherOperationRegistry, diffOperationRegistry;

    @Before
    public void setUp() {
        operationRegistry = Utils.getOperationManager(operationSet);
        otherOperationRegistry = Utils.getOperationManager(operationSet);
        diffOperationRegistry = Utils.getOperationManager();
    }

    @Test
    public void testNotNull() {
        assertThat(operationRegistry, is(not(equalTo(null))));
    }

    @Test
    public void testGetOperations() {
        assertThat(operationRegistry.getEntries(), is(operationSet));
    }

    @Test
    public void testGetOperationById() throws UnknownMetaprojectObjectIdException {
        assertThat(operationRegistry.get(operation1.getId()), is(operation1));
    }

    @Test
    public void testRemoveOperation() {
        assertThat(operationRegistry.getEntries().contains(operation4), is(true));
        operationRegistry.remove(operation4);
        assertThat(operationRegistry.getEntries().contains(operation4), is(false));
    }

    @Test
    public void testAddOperation() throws UnknownMetaprojectObjectIdException, IdAlreadyInUseException {
        Operation operation5 = Utils.getServerOperation();
        assertThat(operationRegistry.getEntries().contains(operation5), is(false));

        operationRegistry.add(operation5);
        assertThat(operationRegistry.getEntries().contains(operation5), is(true));
        assertThat(operationRegistry.get(operation5.getId()), is(operation5));
    }

    @Test
    public void testChangeDescription() throws UnknownMetaprojectObjectIdException {
        Description newDescription = Utils.getDescription("new test description");
        operationRegistry.setDescription(operation2.getId(), newDescription);
        assertThat(operationRegistry.get(operation2.getId()).getDescription(), is(newDescription));
    }

    @Test
    public void testChangeName() throws UnknownMetaprojectObjectIdException {
        Name newName = Utils.getName("new test name");
        operationRegistry.setName(operation2.getId(), newName);
        assertThat(operationRegistry.get(operation2.getId()).getName(), is(newName));
    }

    @Test
    public void testExists() {
        OperationId operationId = Utils.getOperationId("newTestOperationId");
        assertThat(operationRegistry.contains(operation1.getId()), is(true));
        assertThat(operationRegistry.contains(operationId), is(false));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(operationRegistry, is(operationRegistry));
    }

    @Test
    public void testEquals() {
        assertThat(operationRegistry, is(otherOperationRegistry));
    }

    @Test
    public void testNotEquals() {
        assertThat(operationRegistry, is(not(diffOperationRegistry)));
    }

    @Test
    public void testHashCode() {
        assertThat(operationRegistry.hashCode(), is(otherOperationRegistry.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(operationRegistry.toString(), startsWith(toStringHead));
    }
}
