package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.exception.AccessControlObjectNotFoundException;
import edu.stanford.protege.metaproject.api.exception.OperationNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationManagerTest {
    private static final Operation operation1 = Utils.getOperation(), operation2 = Utils.getOperation(), operation3 = Utils.getOperation(), operation4 = Utils.getOperation();
    private static final Set<Operation> operationSet = Utils.getOperationSet(operation1, operation2, operation3, operation4);
    private static final String toStringHead = "OperationManager";

    private OperationManager operationManager, otherOperationManager, diffOperationManager;

    @Before
    public void setUp() {
        operationManager = Utils.getOperationManager(operationSet);
        otherOperationManager = Utils.getOperationManager(operationSet);
        diffOperationManager = Utils.getOperationManager();
    }

    @Test
    public void testNotNull() {
        assertThat(operationManager, is(not(equalTo(null))));
    }

    @Test
    public void testGetOperations() {
        assertThat(operationManager.getOperations(), is(operationSet));
    }

    @Test
    public void testGetOperationById() throws OperationNotFoundException {
        assertThat(operationManager.getOperation(operation1.getId()), is(operation1));
    }

    @Test
    public void testRemoveOperation() throws AccessControlObjectNotFoundException {
        assertThat(operationManager.getOperations().contains(operation4), is(true));
        operationManager.remove(operation4);
        assertThat(operationManager.getOperations().contains(operation4), is(false));
    }

    @Test
    public void testAddOperation() throws OperationNotFoundException {
        Operation operation5 = Utils.getOperation();
        assertThat(operationManager.getOperations().contains(operation5), is(false));

        operationManager.add(operation5);
        assertThat(operationManager.getOperations().contains(operation5), is(true));
        assertThat(operationManager.getOperation(operation5.getId()), is(operation5));
    }

    @Test
    public void testChangeDescription() throws OperationNotFoundException {
        Description newDescription = Utils.getDescription("new test description");
        operationManager.changeDescription(operation2.getId(), newDescription);
        assertThat(operationManager.getOperation(operation2.getId()).getDescription(), is(newDescription));
    }

    @Test
    public void testChangeName() throws OperationNotFoundException {
        Name newName = Utils.getName("new test name");
        operationManager.changeName(operation2.getId(), newName);
        assertThat(operationManager.getOperation(operation2.getId()).getName(), is(newName));
    }

    @Test
    public void testExists() {
        OperationId operationId = Utils.getOperationId("newTestOperationId");
        assertThat(operationManager.contains(operation1.getId()), is(true));
        assertThat(operationManager.contains(operationId), is(false));
    }

    @Test
    public void testAddPrerequisite() throws OperationNotFoundException {
        OperationPrerequisite prerequisite = Utils.getOperationPrerequisite();
        operationManager.addPrerequisite(operation2.getId(), prerequisite);
        assertThat(operationManager.getOperation(operation2.getId()).getPrerequisites().get().contains(prerequisite), is(true));
    }

    @Test
    public void testRemovePrerequisite() throws OperationNotFoundException {
        OperationPrerequisite prerequisite = operation2.getPrerequisites().get().iterator().next();
        operationManager.removePrerequisite(operation2.getId(), prerequisite);

        Operation op = operationManager.getOperation(operation2.getId());
        assertThat(op.getPrerequisites().get().contains(prerequisite), is(false));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(operationManager, is(operationManager));
    }

    @Test
    public void testEquals() {
        assertThat(operationManager, is(otherOperationManager));
    }

    @Test
    public void testNotEquals() {
        assertThat(operationManager, is(not(diffOperationManager)));
    }

    @Test
    public void testHashCode() {
        assertThat(operationManager.hashCode(), is(otherOperationManager.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(operationManager.toString(), startsWith(toStringHead));
    }
}
