package edu.stanford.protege.metaproject.api;

/**
 * A representation of an operation consisting of a unique identifier and a natural language description
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Operation extends PolicyObject<OperationId>, HasDescription, Comparable<Operation> {

    /**
     * Get the type of operation: read, write, or execute
     *
     * @return Operation type
     */
    OperationType getType();

    /**
     * Get the scope of this operation, i.e., whether it applies to the ontology, metaproject, or server
     *
     * @return Scope of operation
     */
    Scope getScope();

    /**
     * Check whether this is a system operation, i.e., a default operation defined by the API
     *
     * @return true if this is a system operation, false otherwise
     */
    boolean isSystemOperation();

    enum Scope {
        METAPROJECT, SERVER, ONTOLOGY, GUI
    }
}
