package edu.stanford.protege.metaproject.api;

/**
 * A representation of an operation consisting of a unique identifier and a natural language description
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Operation extends MetaprojectObject<OperationId>, HasDescription, Comparable<Operation> {

    /**
     * Get the type of operation: read, write, or execute
     *
     * @return Operation type
     */
    OperationType getType();

    /**
     * Check whether this is a metaproject operation
     *
     * @return true if this is a metaproject operation, false otherwise
     */
    boolean isMetaprojectOperation();

    /**
     * Check whether this is a server operation
     *
     * @return true if this is a server operation, false otherwise
     */
    boolean isServerOperation();

    /**
     * Check whether this is an ontology operation
     *
     * @return true if this is an ontology operation, false otherwise
     */
    boolean isOntologyOperation();

}
