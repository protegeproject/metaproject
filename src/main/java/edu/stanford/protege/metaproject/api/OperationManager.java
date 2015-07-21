package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.OperationNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A manager for operations
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class OperationManager {
    private static OperationManager instance = null;
    private Set<Operation> operations = new HashSet<>();

    private OperationManager() { }

    public static OperationManager getInstance() {
        if(instance == null) {
            instance = new OperationManager();
        }
        return instance;
    }

    /**
     * Add an operation
     *
     * @param operation Operation
     */
    public void addOperation(Operation operation) {
        operations.add(checkNotNull(operation));
    }

    /**
     * Add a set of operations
     *
     * @param operations    Set of operations
     */
    public void addOperations(Set<Operation> operations) {
        operations.forEach(this::addOperation);
    }

    /**
     * Remove the specified operation
     *
     * @param operation Operation
     * @throws OperationNotFoundException   Operation not found
     */
    public void removeOperation(Operation operation) throws OperationNotFoundException {
        if(!operations.contains(operation)) {
            throw new OperationNotFoundException("The specified operation does not exist");
        }
        operations.remove(operation);
    }

    /**
     * Remove the given set of operations
     *
     * @param operations Set of operations
     * @throws OperationNotFoundException   Operation not found
     */
    public void removeOperations(Set<Operation> operations) throws OperationNotFoundException {
        for(Operation operation : operations) {
            removeOperation(operation);
        }
    }

    /**
     * Get all known operations
     *
     * @return Set of operations
     */
    public Set<Operation> getOperations() {
        return operations;
    }

    /**
     * Get an operation based on its identifier
     *
     * @param operationId   Operation identifier
     * @return Operation
     */
    public Optional<Operation> getOperation(OperationId operationId) {
        Operation operation = null;
        for(Operation o : operations) {
            if(o.getId().equals(operationId)) {
                operation = o; break;
            }
        }
        return Optional.ofNullable(operation);
    }

    /**
     * A convenience method to fetch an operation or die trying (with an exception)
     *
     * @param operationId    Operation identifier
     * @return Operation instance
     * @throws OperationNotFoundException    Operation not found
     */
    private Operation getOperationOrFail(OperationId operationId) throws OperationNotFoundException {
        Optional<Operation> operation = getOperation(operationId);
        if(operation.isPresent()) {
            return operation.get();
        }
        else {
            throw new OperationNotFoundException("The specified operation does not exist");
        }
    }

    /**
     * Change the name of the given operation
     *
     * @param operationId Operation identifier
     * @param operationName New operation name
     * @throws OperationNotFoundException    Operation not found
     */
    public void changeOperationName(OperationId operationId, OperationName operationName) throws OperationNotFoundException {
        Operation operation = getOperationOrFail(operationId);
        removeOperation(operation);

        Operation newOperation = new Operation(operationId, operationName, operation.getDescription(), operation.getPrerequisites());
        addOperation(newOperation);
    }

    /**
     * Change the description of a given operation
     *
     * @param operationId Operation identifier
     * @param operationDescription  New operation description
     * @throws OperationNotFoundException    Operation not found
     */
    public void changeOperationDescription(OperationId operationId, OperationDescription operationDescription) throws OperationNotFoundException {
        Operation operation = getOperationOrFail(operationId);
        removeOperation(operation);

        Operation newOperation = new Operation(operationId, operation.getName(), operationDescription, operation.getPrerequisites());
        addOperation(newOperation);
    }

    /**
     * Add an operation prerequisite to the specified operation
     *
     * @param operationId   Operation identifier
     * @param prerequisite  Operation prerequisite
     * @throws OperationNotFoundException   Operation not found
     */
    public void addPrerequisite(OperationId operationId, OperationPrerequisite prerequisite) throws OperationNotFoundException {
        Set<OperationPrerequisite> prerequisites = new HashSet<>();
        prerequisites.add(prerequisite);
        addPrerequisites(operationId, prerequisites);
    }

    /**
     * Add a set of operation prerequisites to the specified operation
     *
     * @param operationId   Operation identifier
     * @param prerequisites  Set of operation prerequisites
     * @throws OperationNotFoundException   Operation not found
     */
    public void addPrerequisites(OperationId operationId, Set<OperationPrerequisite> prerequisites) throws OperationNotFoundException {
        Operation operation = getOperationOrFail(operationId);
        removeOperation(operation);

        Set<OperationPrerequisite> prerequisiteSet = operation.getPrerequisites();
        prerequisiteSet.addAll(prerequisites);

        Operation newOperation = new Operation(operation.getId(), operation.getName(), operation.getDescription(), prerequisiteSet);
        addOperation(newOperation);
    }

    /**
     * Remove an operation prerequisite from the specified operation
     *
     * @param operationId   Operation identifier
     * @param prerequisite  Operation prerequisite
     * @throws OperationNotFoundException   Operation not found
     */
    public void removePrerequisite(OperationId operationId, OperationPrerequisite prerequisite) throws OperationNotFoundException {
        Set<OperationPrerequisite> prerequisites = new HashSet<>();
        prerequisites.remove(prerequisite);
        removePrerequisites(operationId, prerequisites);
    }

    /**
     * Remove a set of operation prerequisites from the specified operation
     *
     * @param operationId   Operation identifier
     * @param prerequisites  Set of operation prerequisites
     * @throws OperationNotFoundException   Operation not found
     */
    public void removePrerequisites(OperationId operationId, Set<OperationPrerequisite> prerequisites) throws OperationNotFoundException {
        Operation operation = getOperationOrFail(operationId);
        removeOperation(operation);

        Set<OperationPrerequisite> prerequisiteSet = operation.getPrerequisites();
        prerequisiteSet.removeAll(prerequisites);

        Operation newOperation = new Operation(operation.getId(), operation.getName(), operation.getDescription(), prerequisiteSet);
        addOperation(newOperation);
    }
}
