package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.OperationNotFoundException;

import java.util.Optional;
import java.util.Set;

/**
 * A manager for accessing, adding, removing or editing existing operations
 *
 * The operation manager follows the singleton pattern; only one instance of this manager may exist.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OperationManager extends Manager {

    /**
     * Add operation(s)
     *
     * @param operation One or more operations
     */
    void add(Operation... operation);

    /**
     * Remove the specified operation(s)
     *
     * @param operation One or more operations
     * @throws OperationNotFoundException   Operation not found
     */
    void remove(Operation... operation) throws OperationNotFoundException;

    /**
     * Get all known operations
     *
     * @return Set of operations
     */
    Set<Operation> getOperations();

    /**
     * A convenience method to fetch an operation or die trying (with an exception)
     *
     * @param operationId    Operation identifier
     * @return Operation instance
     * @throws OperationNotFoundException    Operation not found
     */
    Operation getOperation(OperationId operationId) throws OperationNotFoundException;

    /**
     * Change the name of the given operation
     *
     * @param operation Operation
     * @param operationName New operation name
     * @throws OperationNotFoundException    Operation not found
     */
    void changeOperationName(Operation operation, Name operationName) throws OperationNotFoundException;

    /**
     * Change the description of a given operation
     *
     * @param operation Operation
     * @param operationDescription  New operation description
     * @throws OperationNotFoundException    Operation not found
     */
    void changeOperationDescription(Operation operation, Description operationDescription) throws OperationNotFoundException;

    /**
     * Add an operation prerequisite to the specified operation
     *
     * @param operation   Operation
     * @param prerequisite  Operation prerequisite
     * @throws OperationNotFoundException   Operation not found
     */
    void addPrerequisite(Operation operation, OperationPrerequisite prerequisite) throws OperationNotFoundException;

    /**
     * Add a set of operation prerequisites to the specified operation
     *
     * @param operation   Operation
     * @param prerequisites  Set of operation prerequisites
     * @throws OperationNotFoundException   Operation not found
     */
    void addPrerequisites(Operation operation, Set<OperationPrerequisite> prerequisites) throws OperationNotFoundException;

    /**
     * Remove an operation prerequisite from the specified operation
     *
     * @param operation   Operation
     * @param prerequisite  Operation prerequisite
     * @throws OperationNotFoundException   Operation not found
     */
    void removePrerequisite(Operation operation, OperationPrerequisite prerequisite) throws OperationNotFoundException;

    /**
     * Remove a set of operation prerequisites from the specified operation
     *
     * @param operation   Operation
     * @param prerequisites  Set of operation prerequisites
     * @throws OperationNotFoundException   Operation not found
     */
    void removePrerequisites(Operation operation, Set<OperationPrerequisite> prerequisites) throws OperationNotFoundException;

}
