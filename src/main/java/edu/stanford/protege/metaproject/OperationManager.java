package edu.stanford.protege.metaproject;

import java.util.Set;

/**
 * A manager for operations
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OperationManager {

    /**
     * Add an operation
     *
     * @param operation Operation
     */
    void addOperation(Operation operation);

    /**
     * Remove the specified operation
     *
     * @param operation Operation
     */
    void removeOperation(Operation operation);

    /**
     * Get all known operations
     *
     * @return Set of operations
     */
    Set<Operation> getOperations();

    /**
     * Get an operation based on its identifier
     *
     * @param operationId   Operation identifier
     * @return Operation
     */
    Operation getOperation(OperationId operationId);

    /**
     * Change the name of the given operation
     *
     * @param operation Operation instance
     * @param operationName New operation name
     */
    void changeOperationName(Operation operation, OperationName operationName);

    /**
     * Change the description of a given operation
     *
     * @param operation Operation instance
     * @param operationDescription  New operation description
     */
    void changeOperationDescription(Operation operation, OperationDescription operationDescription);

}
