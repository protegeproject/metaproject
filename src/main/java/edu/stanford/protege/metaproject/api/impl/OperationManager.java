package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.OperationNotFoundException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A manager for accessing, adding, removing or editing existing operations
 *
 * The operation manager follows the singleton pattern; only one instance of this manager may exist.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationManager implements Manager, Serializable {
    private static final long serialVersionUID = 6445984833759487695L;
    private Set<Operation> operations = new HashSet<>();

    /**
     * Constructor
     *
     * @param operations    Set of operations
     */
    public OperationManager(Set<Operation> operations) {
        this.operations = checkNotNull(operations);
    }

    /**
     * No-arguments constructor
     */
    public OperationManager() { }

    /**
     * Add operation(s)
     *
     * @param operation One or more operations
     */
    public void addOperation(Operation... operation) {
        for(Operation o : operation) {
            operations.add(checkNotNull(o));
        }
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
     * Remove the specified operation(s)
     *
     * @param operation One or more operations
     * @throws OperationNotFoundException   Operation not found
     */
    public void removeOperation(Operation... operation) throws OperationNotFoundException {
        for(Operation op : operation) {
            if (!operations.contains(op)) {
                throw new OperationNotFoundException("The specified operation does not exist");
            }
            operations.remove(op);
        }
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
    public Optional<Operation> getOperationOptional(Id operationId) {
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
    public Operation getOperation(Id operationId) throws OperationNotFoundException {
        Optional<Operation> operation = getOperationOptional(operationId);
        if(operation.isPresent()) {
            return operation.get();
        }
        else {
            throw new OperationNotFoundException("The specified operation identifier does not correspond to an existing operation");
        }
    }

    /**
     * Change the name of the given operation
     *
     * @param operation Operation
     * @param operationName New operation name
     * @throws OperationNotFoundException    Operation not found
     */
    public void changeOperationName(Operation operation, Name operationName) throws OperationNotFoundException {
        removeOperation(operation);
        Operation newOperation = new OperationImpl(operation.getId(), operationName, operation.getDescription(), operation.getPrerequisites());
        addOperation(newOperation);
    }

    /**
     * Change the description of a given operation
     *
     * @param operation Operation
     * @param operationDescription  New operation description
     * @throws OperationNotFoundException    Operation not found
     */
    public void changeOperationDescription(Operation operation, Description operationDescription) throws OperationNotFoundException {
        removeOperation(operation);
        Operation newOperation = new OperationImpl(operation.getId(), operation.getName(), operationDescription, operation.getPrerequisites());
        addOperation(newOperation);
    }

    /**
     * Add an operation prerequisite to the specified operation
     *
     * @param operation   Operation
     * @param prerequisite  Operation prerequisite
     * @throws OperationNotFoundException   Operation not found
     */
    public void addPrerequisite(Operation operation, OperationPrerequisite prerequisite) throws OperationNotFoundException {
        Set<OperationPrerequisite> prerequisites = new HashSet<>();
        prerequisites.add(prerequisite);
        addPrerequisites(operation, prerequisites);
    }

    /**
     * Add a set of operation prerequisites to the specified operation
     *
     * @param operation   Operation
     * @param prerequisites  Set of operation prerequisites
     * @throws OperationNotFoundException   Operation not found
     */
    public void addPrerequisites(Operation operation, Set<OperationPrerequisite> prerequisites) throws OperationNotFoundException {
        removeOperation(operation);

        Set<OperationPrerequisite> prerequisiteSet = operation.getPrerequisites();
        prerequisiteSet.addAll(prerequisites);

        Operation newOperation = new OperationImpl(operation.getId(), operation.getName(), operation.getDescription(), prerequisiteSet);
        addOperation(newOperation);
    }

    /**
     * Remove an operation prerequisite from the specified operation
     *
     * @param operation   Operation
     * @param prerequisite  Operation prerequisite
     * @throws OperationNotFoundException   Operation not found
     */
    public void removePrerequisite(Operation operation, OperationPrerequisite prerequisite) throws OperationNotFoundException {
        Set<OperationPrerequisite> prerequisites = new HashSet<>();
        prerequisites.remove(prerequisite);
        removePrerequisites(operation, prerequisites);
    }

    /**
     * Remove a set of operation prerequisites from the specified operation
     *
     * @param operation   Operation
     * @param prerequisites  Set of operation prerequisites
     * @throws OperationNotFoundException   Operation not found
     */
    public void removePrerequisites(Operation operation, Set<OperationPrerequisite> prerequisites) throws OperationNotFoundException {
        removeOperation(operation);

        Set<OperationPrerequisite> prerequisiteSet = operation.getPrerequisites();
        prerequisiteSet.removeAll(prerequisites);

        Operation newOperation = new OperationImpl(operation.getId(), operation.getName(), operation.getDescription(), prerequisiteSet);
        addOperation(newOperation);
    }

    /**
     * Verify whether a given operation identifier corresponds to a registered operation
     *
     * @param operationId Operation identifier
     * @return true if operation with the given operation identifier exists, false otherwise
     */
    public boolean exists(Id operationId) {
        for(Operation operation : operations) {
            if(operation.getId().equals(operationId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationManager that = (OperationManager) o;
        return Objects.equal(operations, that.operations);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operations);
    }
}
