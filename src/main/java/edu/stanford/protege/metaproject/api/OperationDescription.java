package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of a natural language description of an operation
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationDescription implements Serializable, HasDescription {
    private final String operationDescription;

    /**
     * Constructor
     *
     * @param operationDescription  Operation description
     */
    public OperationDescription(String operationDescription) {
        this.operationDescription = checkNotNull(operationDescription);
    }

    /**
     * Get the operation description
     *
     * @return Operation description
     */
    public String getDescription() {
        return operationDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationDescription that = (OperationDescription) o;
        return Objects.equal(operationDescription, that.operationDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operationDescription);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("operationDescription", operationDescription)
                .toString();
    }
}
