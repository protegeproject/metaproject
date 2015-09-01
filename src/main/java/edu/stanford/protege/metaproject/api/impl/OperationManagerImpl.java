package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.OperationNotFoundException;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationManagerImpl implements OperationManager, Serializable {
    private static final long serialVersionUID = 1859235816703554459L;
    private Set<Operation> operations = new HashSet<>();

    /**
     * Constructor
     *
     * @param operations    Set of operations
     */
    public OperationManagerImpl(Set<Operation> operations) {
        this.operations = checkNotNull(operations);
    }

    /**
     * No-arguments constructor
     */
    public OperationManagerImpl() { }

    @Override
    public void add(Operation... operations) {
        checkNotNull(operations);
        for(Operation o : operations) {
            this.operations.add(checkNotNull(o));
        }
    }

    @Override
    public void remove(Operation... operations) {
        checkNotNull(operations);
        for(Operation o : operations) {
            checkNotNull(o);
            this.operations.remove(o);
        }
    }

    @Override
    public Operation create(String name, String description, OperationType operationType, Optional<Set<OperationPrerequisite>> prerequisites) {
        AccessControlObjectUUIDGenerator gen = AccessControlObjectUUIDGenerator.getInstance();
        return new OperationImpl(gen.getOperationId(), new NameImpl(name), new DescriptionImpl(description), operationType, prerequisites);
    }

    @Override
    public Set<Operation> getOperations() {
        return operations;
    }

    @Override
    public Operation getOperation(OperationId operationId) throws OperationNotFoundException {
        Optional<Operation> operation = getOperationOptional(operationId);
        if(operation.isPresent()) {
            return operation.get();
        }
        else {
            throw new OperationNotFoundException("The specified operation identifier does not correspond to an existing operation");
        }
    }

    @Override
    public void changeName(OperationId operationId, Name operationName) throws OperationNotFoundException {
        checkNotNull(operationName);
        Operation operation = getOperation(operationId);
        remove(operation);
        Operation newOperation = new OperationImpl(operation.getId(), operationName, operation.getDescription(), operation.getType(), operation.getPrerequisites());
        add(newOperation);
    }

    @Override
    public void changeDescription(OperationId operationId, Description operationDescription) throws OperationNotFoundException {
        checkNotNull(operationDescription);
        Operation operation = getOperation(operationId);
        remove(operation);
        Operation newOperation = new OperationImpl(operation.getId(), operation.getName(), operationDescription, operation.getType(), operation.getPrerequisites());
        add(newOperation);
    }

    @Override
    public void addPrerequisite(OperationId operationId, OperationPrerequisite... prerequisites) throws OperationNotFoundException {
        checkNotNull(prerequisites);
        if(prerequisites.length > 0) {
            Operation operation = getOperation(operationId);
            Set<OperationPrerequisite> newPrerequisites = (operation.getPrerequisites().isPresent() ? new HashSet<>(operation.getPrerequisites().get()) : new HashSet<>());
            Collections.addAll(newPrerequisites, prerequisites);
            remove(operation);
            Operation newOperation = new OperationImpl(operation.getId(), operation.getName(), operation.getDescription(), operation.getType(), Optional.of(newPrerequisites));
            add(newOperation);
        }
    }

    @Override
    public void removePrerequisite(OperationId operationId, OperationPrerequisite... prerequisites) throws OperationNotFoundException {
        checkNotNull(prerequisites);
        if(prerequisites.length > 0) {
            Operation operation = getOperation(operationId);
            remove(operation);
            Set<OperationPrerequisite> prerequisiteSet = null;
            if(operation.getPrerequisites().isPresent()) {
                prerequisiteSet = new HashSet<>(operation.getPrerequisites().get());
                for(OperationPrerequisite op : prerequisites) {
                    prerequisiteSet.remove(checkNotNull(op));
                }
            }
            Operation newOperation = new OperationImpl(operation.getId(), operation.getName(), operation.getDescription(), operation.getType(), Optional.ofNullable(prerequisiteSet));
            add(newOperation);
        }
    }

    @Override
    public boolean contains(AccessControlObjectId operationId) {
        checkNotNull(operationId);
        for(Operation operation : operations) {
            if(operation.getId().equals(operationId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get an operation based on its identifier
     *
     * @param operationId   Operation identifier
     * @return Operation
     */
    private Optional<Operation> getOperationOptional(OperationId operationId) {
        checkNotNull(operationId);
        Operation operation = null;
        for(Operation o : operations) {
            if(o.getId().equals(operationId)) {
                operation = o; break;
            }
        }
        return Optional.ofNullable(operation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationManagerImpl that = (OperationManagerImpl) o;
        return Objects.equal(operations, that.operations);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operations);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("operations", operations)
                .toString();
    }
}
