package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.*;

import javax.annotation.Nonnull;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
final class MetaprojectOperation implements Operation, Serializable {
    private static final long serialVersionUID = -6890409391570138362L;
    private final OperationId id;
    private final Name name;
    private final Description description;
    private final OperationType type;

    /**
     * Constructor
     *
     * @param id   Operation identifier
     * @param name Operation name
     * @param description  Operation description
     * @param type Operation type
     */
    MetaprojectOperation(OperationId id, Name name, Description description, OperationType type) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.description = checkNotNull(description);
        this.type = checkNotNull(type);
    }

    @Override
    public OperationId getId() {
        return id;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public OperationType getType() {
        return type;
    }

    @Override
    public boolean isMetaprojectOperation() {
        return true;
    }

    @Override
    public boolean isServerOperation() {
        return false;
    }

    @Override
    public boolean isOntologyOperation() {
        return false;
    }

    @Override
    public boolean isUser() {
        return false;
    }

    @Override
    public boolean isProject() {
        return false;
    }

    @Override
    public boolean isRole() {
        return false;
    }

    @Override
    public boolean isOperation() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetaprojectOperation operation = (MetaprojectOperation) o;
        return Objects.equal(id, operation.id) &&
                Objects.equal(name, operation.name) &&
                Objects.equal(description, operation.description) &&
                Objects.equal(type, operation.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, type);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("type", type)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull Operation that) {
        return ComparisonChain.start()
                .compare(this.name.get(), that.getName().get())
                .result();
    }
}
