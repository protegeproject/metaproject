package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;
import edu.stanford.protege.metaproject.api.*;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.io.Serializable;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class CustomOperation extends OperationAbst implements Serializable {
    private static final long serialVersionUID = -2476008890382106204L;

    @SerializedName("default")
    private final boolean system = false;

    /**
     * Constructor
     *
     * @param id   Operation identifier
     * @param name Operation name
     * @param description  Operation description
     * @param type Operation type
     * @param scope Operation scope
     */
    public CustomOperation(@Nonnull OperationId id, @Nonnull Name name, @Nonnull Description description, @Nonnull OperationType type, @Nonnull Scope scope) {
        super(id, name, description, type, scope);
    }

    @Override
    public boolean isSystemOperation() {
        return system;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Operation)) {
            return false;
        }
        Operation that = (Operation) o;
        return !that.isSystemOperation() &&
                Objects.equal(id, that.getId()) &&
                Objects.equal(name, that.getName()) &&
                Objects.equal(description, that.getDescription()) &&
                type == that.getType() &&
                scope == that.getScope();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, type, scope, system);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("type", type)
                .add("scope", scope)
                .add("default", system)
                .toString();
    }
}
