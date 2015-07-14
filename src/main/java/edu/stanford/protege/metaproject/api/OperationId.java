package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple representation of an operation identifier
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationId implements Serializable, HasId {
    private final String operationId;

    /**
     * Constructor
     *
     * @param operationId   Operation identifier
     */
    public OperationId(String operationId) {
        this.operationId = checkNotNull(operationId);
    }

    /**
     * Get the operation identifier
     *
     * @return Operation identifier
     */
    public String getId() {
        return operationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationId that = (OperationId) o;
        return Objects.equal(operationId, that.operationId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operationId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("operationId", operationId)
                .toString();
    }
}
