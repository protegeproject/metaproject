package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * A representation of an operation consisting of a unique identifier and a natural language description
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface Operation extends PolicyObject<OperationId>, HasDescription, Comparable<Operation> {

    /**
     * Get the type of operation: read, write, or execute
     *
     * @return Operation type
     */
    @Nonnull
    OperationType getType();

    /**
     * Get the scope of this operation, i.e., whether it applies to the ontology, metaproject, or server
     *
     * @return Scope of operation
     */
    @Nonnull
    Scope getScope();

    /**
     * Check whether this is a system operation, i.e., a default operation defined by the API
     *
     * @return true if this is a system operation, false otherwise
     */
    boolean isSystemOperation();

    enum Scope {
        POLICY, SERVER, ONTOLOGY, GUI
    }
}
