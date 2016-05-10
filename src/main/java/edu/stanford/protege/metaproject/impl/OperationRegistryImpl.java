package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.IdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;
import edu.stanford.protege.metaproject.api.exception.UnknownOperationIdException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationRegistryImpl implements OperationRegistry, Serializable {
    private static final long serialVersionUID = 2327711655023800432L;
    private Set<Operation> operations = new HashSet<>();

    /**
     * Constructor
     *
     * @param operations    Set of operations
     */
    public OperationRegistryImpl(Set<Operation> operations) {
        this.operations = checkNotNull(operations);
    }

    @Override
    public void add(Operation operation) throws IdAlreadyInUseException {
        checkNotNull(operation);
        if (contains(operation.getId())) {
            throw new IdAlreadyInUseException("The specified operation identifier is already used by another operation");
        }
        operations.add(operation);
    }

    @Override
    public void remove(Operation operation) {
        checkNotNull(operation);
        operations.remove(operation);
    }

    @Override
    public Set<Operation> getEntries() {
        return operations;
    }

    @Override
    public <E extends MetaprojectObjectId> Operation get(E id) throws UnknownMetaprojectObjectIdException {
        if(!(id instanceof OperationId)) {
            throw new IllegalArgumentException("Programmer error: Expected an operation identifier");
        }
        Optional<Operation> operation = getOperationOptional((OperationId)id);
        if(operation.isPresent()) {
            return operation.get();
        }
        else {
            throw new UnknownOperationIdException("The specified operation identifier does not correspond to an existing operation");
        }
    }

    @Override
    public void setName(OperationId operationId, Name operationName) throws UnknownMetaprojectObjectIdException {
        checkNotNull(operationName);
        Operation operation = get(operationId);
        Operation newOperation = createOperation(operation.getId(), operationName, operation.getDescription(), operation.getType());
        update(operationId, newOperation);
    }

    @Override
    public void setDescription(OperationId operationId, Description operationDescription) throws UnknownMetaprojectObjectIdException {
        checkNotNull(operationDescription);
        Operation operation = get(operationId);
        Operation newOperation = createOperation(operation.getId(), operation.getName(), operationDescription, operation.getType());
        update(operationId, newOperation);
    }

    @Override
    public <E extends MetaprojectObjectId> void update(E id, Operation newObj) throws UnknownMetaprojectObjectIdException {
        remove(get(id));
        operations.add(newObj);
    }

    @Override
    public boolean contains(Operation obj) {
        checkNotNull(obj);
        return operations.contains(obj);
    }

    @Override
    public <E extends MetaprojectObjectId> boolean contains(E id) {
        checkNotNull(id);
        for(Operation operation : operations) {
            if(operation.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create an instance of an operation
     */
    private Operation createOperation(OperationId id, Name name, Description description, OperationType type) {
        return Manager.getFactory().getServerOperation(id, name, description, type);
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationRegistry)) {
            return false;
        }
        OperationRegistry that = (OperationRegistry) o;
        return Objects.equal(operations, that.getEntries());
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
