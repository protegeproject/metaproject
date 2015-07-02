package edu.stanford.protege.metaproject;

import com.google.common.base.Optional;

/**
 * A representation of an operation and its prerequisites
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Operation {

    /**
     * Get the operation identifier
     *
     * @return Operation identifier instance
     */
    OperationId getId();

    /**
     * Get the description of the operation
     *
     * @return Operation description instance
     */
    OperationDescription getDescription();

    /**
     * Get the prerequisitres for the operation
     *
     * @return Operation prerequisites instance
     */
    Optional<OperationPrerequisites> getPrerequisites();

}
