package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;

/**
 * A manager for accessing, adding, removing or editing existing operations
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OperationRegistry extends Registry<Operation> {

    /**
     * Change the name of the given operation
     *
     * @param operationId Operation identifier
     * @param operationName New operation name
     * @throws UnknownMetaprojectObjectIdException    Operation identifier is not recognized
     */
    void setName(OperationId operationId, Name operationName) throws UnknownMetaprojectObjectIdException;

    /**
     * Change the description of a given operation
     *
     * @param operationId Operation identifier
     * @param operationDescription  New operation description
     * @throws UnknownMetaprojectObjectIdException    Operation identifier is not recognized
     */
    void setDescription(OperationId operationId, Description operationDescription) throws UnknownMetaprojectObjectIdException;

}
