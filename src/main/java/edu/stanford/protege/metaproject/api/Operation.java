package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

import java.io.Serializable;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of an operation consisting of a unique identifier, a natural language description, and operation prerequisites
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class Operation implements Serializable, HasDetails, HasDescription {
    private static final long serialVersionUID = -4469938653912135406L;
    private final OperationId operationId;
    private final OperationName operationName;
    private final OperationDescription operationDescription;
    private final ImmutableSet<OperationPrerequisite> prerequisites;

    /**
     * Constructor
     * @param operationId   Operation identifier
     * @param operationName Operation name
     * @param operationDescription  Operation description
     * @param prerequisites Operation prerequisites
     */
    public Operation(OperationId operationId, OperationName operationName, OperationDescription operationDescription, Set<OperationPrerequisite> prerequisites) {
        this.operationId = checkNotNull(operationId);
        this.operationName = checkNotNull(operationName);
        this.operationDescription = checkNotNull(operationDescription);
        ImmutableSet<OperationPrerequisite> prerequisitesCopy = new ImmutableSet.Builder<OperationPrerequisite>().addAll(checkNotNull(prerequisites)).build();
        this.prerequisites = checkNotNull(prerequisitesCopy);
    }

    /**
     * Get the operation identifier
     *
     * @return Operation identifier
     */
    public OperationId getId() {
        return operationId;
    }

    /**
     * Get the operation name
     *
     * @return Operation name
     */
    public OperationName getName() {
        return operationName;
    }

    /**
     * Get the description of the operation
     *
     * @return Operation description
     */
    public OperationDescription getDescription() {
        return operationDescription;
    }

    /**
     * Get the set of prerequisites for the operation
     *
     * @return Set of operation prerequisites
     */
    public Set<OperationPrerequisite> getPrerequisites() {
        return prerequisites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equal(operationId, operation.operationId) &&
                Objects.equal(operationName, operation.operationName) &&
                Objects.equal(operationDescription, operation.operationDescription) &&
                Objects.equal(prerequisites, operation.prerequisites);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operationId, operationName, operationDescription, prerequisites);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("operationId", operationId)
                .add("operationName", operationName)
                .add("operationDescription", operationDescription)
                .add("prerequisites", prerequisites)
                .toString();
    }
}
