package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.OperationNotFoundException;

import java.util.Optional;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface OperationManager extends Manager {

    void add(Operation... operation);

    void remove(Operation... operation) throws OperationNotFoundException;

    Set<Operation> getOperations();

    Operation getOperation(OperationId operationId) throws OperationNotFoundException;

    void changeOperationName(Operation operation, Name operationName) throws OperationNotFoundException;

    void changeOperationDescription(Operation operation, Description operationDescription) throws OperationNotFoundException;

    void addPrerequisite(Operation operation, OperationPrerequisite prerequisite) throws OperationNotFoundException;

    void addPrerequisites(Operation operation, Set<OperationPrerequisite> prerequisites) throws OperationNotFoundException;

    void removePrerequisite(Operation operation, OperationPrerequisite prerequisite) throws OperationNotFoundException;

    void removePrerequisites(Operation operation, Set<OperationPrerequisite> prerequisites) throws OperationNotFoundException;

}
