package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.OperationId;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of an operation identifier
 * 
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class OperationIdImpl implements OperationId, Serializable {
    private static final long serialVersionUID = -8157802769514788456L;
    private final String id;

    /**
     * Constructor
     *
     * @param id    Identifier
     */
    public OperationIdImpl(String id) {
        this.id = checkNotNull(id);
    }

    @Override
    public String get() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationIdImpl that = (OperationIdImpl) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
