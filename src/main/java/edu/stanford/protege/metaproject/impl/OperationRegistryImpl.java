package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.OperationForChangeNotFoundException;
import edu.stanford.protege.metaproject.api.exception.UnknownOperationIdException;
import org.semanticweb.owlapi.model.*;

import java.io.Serializable;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationRegistryImpl implements OperationRegistry, Serializable {
    private static final long serialVersionUID = 7864971444673547999L;
    private Set<Operation> operations = new HashSet<>();

    /**
     * Constructor
     *
     * @param operations    Set of operations
     */
    public OperationRegistryImpl(Set<Operation> operations) {
        this.operations = checkNotNull(operations);
        initOperations();
    }

    /**
     * No-arguments constructor
     */
    public OperationRegistryImpl() {
        initOperations();
    }

    private void initOperations() {
        List<Operation> defaultOperations = Operations.getDefaultOperations();
        if(!operations.containsAll(defaultOperations)) {
            this.operations.addAll(defaultOperations);
        }
    }

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
    public Set<Operation> getOperations() {
        return operations;
    }

    @Override
    public Operation getOperation(OperationId operationId) throws UnknownOperationIdException {
        Optional<Operation> operation = getOperationOptional(operationId);
        if(operation.isPresent()) {
            return operation.get();
        }
        else {
            throw new UnknownOperationIdException("The specified operation identifier does not correspond to an existing operation");
        }
    }

    @Override
    public Operation getOperationForChange(OWLOntologyChange change) throws OperationForChangeNotFoundException {
        Operation operation = null;
        if (change.isAxiomChange()) {
            ChangeModality modality = null;
            if (change.isAddAxiom()) {
                modality = AxiomChangeModality.ADDITION;
            } else if (change.isRemoveAxiom()) {
                modality = AxiomChangeModality.REMOVAL;
            }
            operation = getOperationForChange(change.getAxiom().getAxiomType(), modality);
        } else if (change.isImportChange()) {
            if (change instanceof AddImport) {
                operation = Operations.ADD_IMPORT;
            } else if (change instanceof RemoveImport) {
                operation = Operations.REMOVE_IMPORT;
            }
        } else if (change instanceof AnnotationChange) {
            if (change instanceof AddOntologyAnnotation) {
                operation = Operations.ADD_ONTOLOGY_ANNOTATION;
            } else if (change instanceof RemoveOntologyAnnotation) {
                operation = Operations.REMOVE_ONTOLOGY_ANNOTATION;
            }
        } else if (change instanceof SetOntologyID) {
            operation = Operations.MODIFY_ONTOLOGY_IRI;
        }
        if(operation==null) {
            throw new OperationForChangeNotFoundException("There is no operation defined for the given ontology change");
        }
        return operation;
    }

    /**
     * Given the axiom type and the modality of a change, get the operation
     *
     * @param axiomType Axiom type
     * @param modality  Change modality (add or remove)
     * @return Operation for the given change type
     * @throws OperationForChangeNotFoundException  There is no operation involving the given axiom type and modality
     */
    private Operation getOperationForChange(AxiomType axiomType, ChangeModality modality) throws OperationForChangeNotFoundException {
        for(Operation op : operations) {
            if(op.getRestrictions().isPresent()) {
                for(OperationRestriction r : op.getRestrictions().get()) {
                    if(r.isAxiomRestriction() && r.getRestriction().equals(axiomType) && r.getModality().equals(modality)) {
                        return op;
                    }
                }
            }
        }
        throw new OperationForChangeNotFoundException("There is no operation involving axioms of the specified type ("
                + axiomType.getName() + ") and with the given modality (" + modality.get() + ")");
    }

    @Override
    public void changeName(OperationId operationId, Name operationName) throws UnknownOperationIdException {
        checkNotNull(operationName);
        Operation operation = getOperation(operationId);
        remove(operation);
        Operation newOperation = getOperation(operation.getId(), operationName, operation.getDescription(), operation.getType(), operation.getRestrictions());
        add(newOperation);
    }

    @Override
    public void changeDescription(OperationId operationId, Description operationDescription) throws UnknownOperationIdException {
        checkNotNull(operationDescription);
        Operation operation = getOperation(operationId);
        remove(operation);
        Operation newOperation = getOperation(operation.getId(), operation.getName(), operationDescription, operation.getType(), operation.getRestrictions());
        add(newOperation);
    }

    @Override
    public void addRestriction(OperationId operationId, OperationRestriction... restrictions) throws UnknownOperationIdException {
        checkNotNull(restrictions);
        if(restrictions.length > 0) {
            Operation operation = getOperation(operationId);
            Set<OperationRestriction> newRestrictions = (operation.getRestrictions().isPresent() ? new HashSet<>(operation.getRestrictions().get()) : new HashSet<>());
            Collections.addAll(newRestrictions, restrictions);
            remove(operation);
            Operation newOperation = getOperation(operation.getId(), operation.getName(), operation.getDescription(), operation.getType(), Optional.of(newRestrictions));
            add(newOperation);
        }
    }

    @Override
    public void removeRestriction(OperationId operationId, OperationRestriction... restrictions) throws UnknownOperationIdException {
        checkNotNull(restrictions);
        if(restrictions.length > 0) {
            Operation operation = getOperation(operationId);
            remove(operation);
            Set<OperationRestriction> restrictionSet = null;
            if(operation.getRestrictions().isPresent()) {
                restrictionSet = new HashSet<>(operation.getRestrictions().get());
                for(OperationRestriction op : restrictions) {
                    restrictionSet.remove(checkNotNull(op));
                }
            }
            Operation newOperation = getOperation(operation.getId(), operation.getName(), operation.getDescription(), operation.getType(), Optional.ofNullable(restrictionSet));
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

    private Operation getOperation(OperationId id, Name name, Description description, OperationType type, Optional<Set<OperationRestriction>> restrictions) {
        return Manager.getFactory().createOperation(id, name, description, type, restrictions);
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
        OperationRegistryImpl that = (OperationRegistryImpl) o;
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
