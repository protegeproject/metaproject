package edu.stanford.protege.metaproject.api;

import com.google.common.base.Optional;

import java.util.Set;

/**
 * A representation of an operation consisting of a unique identifier, a natural language description, and operation prerequisites
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Operation {

    /**
     * Get the operation identifier
     *
     * @return Operation identifier
     */
    OperationId getId();

    /**
     * Get the operation name
     *
     * @return Operation name
     */
    OperationName getName();

    /**
     * Get the description of the operation
     *
     * @return Operation description
     */
    OperationDescription getDescription();

    /**
     * Get the set of prerequisites for the operation
     *
     * @return Set of operation prerequisites
     */
    Optional<Set<OperationPrerequisite>> getPrerequisites();

}
