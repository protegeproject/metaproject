package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableSet;
import edu.stanford.protege.metaproject.api.*;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of an operation consisting of a unique identifier, a natural language description, and operation prerequisites
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class OperationImpl implements Operation, Serializable, Comparable<Operation> {
    private static final long serialVersionUID = 2430492438933347390L;
    private final OperationId id;
    private final Name name;
    private final Description description;
    private final OperationType type;
    private final ImmutableSet<OperationPrerequisite> prerequisites;

    /**
     * Constructor
     * @param id   Operation identifier
     * @param name Operation name
     * @param description  Operation description
     * @param type Operation type
     * @param prerequisites Operation prerequisites
     */
    public OperationImpl(OperationId id, Name name, Description description, OperationType type, Optional<Set<OperationPrerequisite>> prerequisites) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.description = checkNotNull(description);
        this.type = checkNotNull(type);
        this.prerequisites = (prerequisites.isPresent() ? new ImmutableSet.Builder<OperationPrerequisite>().addAll(checkNotNull(prerequisites.get())).build() : null);
    }

    /**
     * Get the operation identifier
     *
     * @return Operation identifier
     */
    @Override
    public OperationId getId() {
        return id;
    }

    /**
     * Get the operation name
     *
     * @return Operation name
     */
    @Override
    public Name getName() {
        return name;
    }

    /**
     * Get the description of the operation
     *
     * @return Operation description
     */
    @Override
    public Description getDescription() {
        return description;
    }

    /**
     * Get the type of operation
     *
     * @return Operation type
     */
    @Override
    public OperationType getType() {
        return type;
    }

    /**
     * Get the set of prerequisites for the operation
     *
     * @return Set of operation prerequisites
     */
    @Override
    public Optional<Set<OperationPrerequisite>> getPrerequisites() {
        return Optional.ofNullable(prerequisites);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationImpl operation = (OperationImpl) o;
        return Objects.equal(id, operation.id) &&
                Objects.equal(name, operation.name) &&
                Objects.equal(description, operation.description) &&
                Objects.equal(type, operation.type) &&
                Objects.equal(prerequisites, operation.prerequisites);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, type, prerequisites);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("type", type)
                .add("prerequisites", prerequisites)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull Operation that) {
        return ComparisonChain.start()
                .compare(this.id.get(), that.getId().get())
                .result();
    }
}
