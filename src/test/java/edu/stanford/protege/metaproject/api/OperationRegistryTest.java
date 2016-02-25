package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.exception.UnknownAccessControlObjectIdException;
import edu.stanford.protege.metaproject.api.exception.UnknownOperationIdException;
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
    private static final Operation operation1 = Utils.getOperation(), operation2 = Utils.getOperation(), operation3 = Utils.getOperation(), operation4 = Utils.getOperation();
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
        assertThat(operationRegistry.getOperations(), is(operationSet));
    }

    @Test
    public void testGetOperationById() throws UnknownOperationIdException {
        assertThat(operationRegistry.getOperation(operation1.getId()), is(operation1));
    }

    @Test
    public void testRemoveOperation() throws UnknownAccessControlObjectIdException {
        assertThat(operationRegistry.getOperations().contains(operation4), is(true));
        operationRegistry.remove(operation4);
        assertThat(operationRegistry.getOperations().contains(operation4), is(false));
    }

    @Test
    public void testAddOperation() throws UnknownOperationIdException {
        Operation operation5 = Utils.getOperation();
        assertThat(operationRegistry.getOperations().contains(operation5), is(false));

        operationRegistry.add(operation5);
        assertThat(operationRegistry.getOperations().contains(operation5), is(true));
        assertThat(operationRegistry.getOperation(operation5.getId()), is(operation5));
    }

    @Test
    public void testChangeDescription() throws UnknownOperationIdException {
        Description newDescription = Utils.getDescription("new test description");
        operationRegistry.changeDescription(operation2.getId(), newDescription);
        assertThat(operationRegistry.getOperation(operation2.getId()).getDescription(), is(newDescription));
    }

    @Test
    public void testChangeName() throws UnknownOperationIdException {
        Name newName = Utils.getName("new test name");
        operationRegistry.changeName(operation2.getId(), newName);
        assertThat(operationRegistry.getOperation(operation2.getId()).getName(), is(newName));
    }

    @Test
    public void testExists() {
        OperationId operationId = Utils.getOperationId("newTestOperationId");
        assertThat(operationRegistry.contains(operation1.getId()), is(true));
        assertThat(operationRegistry.contains(operationId), is(false));
    }

    @Test
    public void testAddPrerequisite() throws UnknownOperationIdException {
        OperationPrerequisite prerequisite = Utils.getOperationPrerequisite();
        operationRegistry.addPrerequisite(operation2.getId(), prerequisite);
        assertThat(operationRegistry.getOperation(operation2.getId()).getPrerequisites().get().contains(prerequisite), is(true));
    }

    @Test
    public void testRemovePrerequisite() throws UnknownOperationIdException {
        OperationPrerequisite prerequisite = operation2.getPrerequisites().get().iterator().next();
        operationRegistry.removePrerequisite(operation2.getId(), prerequisite);

        Operation op = operationRegistry.getOperation(operation2.getId());
        assertThat(op.getPrerequisites().get().contains(prerequisite), is(false));
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
