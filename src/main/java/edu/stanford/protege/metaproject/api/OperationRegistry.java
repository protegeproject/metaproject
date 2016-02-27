package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UnknownOperationIdException;

import java.util.Set;

/**
 * A manager for accessing, adding, removing or editing existing operations
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OperationRegistry extends Registry {

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
     */
    void remove(Operation... operation);

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
     * @throws UnknownOperationIdException    Operation identifier is not recognized
     */
    Operation getOperation(OperationId operationId) throws UnknownOperationIdException;

    /**
     * Change the name of the given operation
     *
     * @param operationId Operation identifier
     * @param operationName New operation name
     * @throws UnknownOperationIdException    Operation identifier is not recognized
     */
    void changeName(OperationId operationId, Name operationName) throws UnknownOperationIdException;

    /**
     * Change the description of a given operation
     *
     * @param operationId Operation identifier
     * @param operationDescription  New operation description
     * @throws UnknownOperationIdException    Operation identifier is not recognized
     */
    void changeDescription(OperationId operationId, Description operationDescription) throws UnknownOperationIdException;

    /**
     * Add one or more operation restrictions to the specified operation
     *
     * @param operationId   Operation identifier
     * @param restrictions  Operation restriction(s)
     * @throws UnknownOperationIdException  Operation identifier is not recognized
     */
    void addRestriction(OperationId operationId, OperationRestriction... restrictions) throws UnknownOperationIdException;

    /**
     * Remove one or more operation restrictions from the specified operation
     *
     * @param operationId   Operation identifier
     * @param restrictions  Operation restriction(s)
     * @throws UnknownOperationIdException  Operation identifier is not recognized
     */
    void removeRestriction(OperationId operationId, OperationRestriction... restrictions) throws UnknownOperationIdException;

}
