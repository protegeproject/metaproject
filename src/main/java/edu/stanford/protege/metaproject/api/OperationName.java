package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of the name of an operation
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationName implements Serializable, HasName {
    private final String operationName;

    /**
     * Constructor
     *
     * @param operationName Operation name
     */
    public OperationName(String operationName) {
        this.operationName = checkNotNull(operationName);
    }

    /**
     * Get the operation name
     *
     * @return Operation name
     */
    public String getName() {
        return operationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationName that = (OperationName) o;
        return Objects.equal(operationName, that.operationName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operationName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("operationName", operationName)
                .toString();
    }
}
