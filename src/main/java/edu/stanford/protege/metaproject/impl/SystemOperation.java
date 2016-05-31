package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;
import edu.stanford.protege.metaproject.api.*;

import java.io.Serializable;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
final class SystemOperation extends OperationAbst implements Serializable {
    private static final long serialVersionUID = 624027255772710818L;

    @SerializedName("default")
    private final boolean system = true;

    /**
     * Constructor
     *
     * @param id   Operation identifier
     * @param name Operation name
     * @param description  Operation description
     * @param type Operation type
     * @param scope Operation scope
     */
    SystemOperation(OperationId id, Name name, Description description, OperationType type, Scope scope) {
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
        return system == that.isSystemOperation() &&
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
