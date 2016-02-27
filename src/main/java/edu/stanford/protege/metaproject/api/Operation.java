package edu.stanford.protege.metaproject.api;

import java.util.Optional;
import java.util.Set;

/**
 * A representation of an operation consisting of a unique identifier, a natural language description, and operation restrictions
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Operation extends AccessControlObject, HasName, HasDescription, Comparable<Operation> {

    /**
     * Get the operation identifier
     *
     * @return Operation identifier
     */
    OperationId getId();

    /**
     * Get the type of operation
     *
     * @return Operation type
     */
    OperationType getType();

    /**
     * Get the set of restrictions for the operation
     *
     * @return Set of operation restrictions
     */
    Optional<Set<OperationRestriction>> getRestrictions();

}
