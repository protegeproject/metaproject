package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import edu.stanford.protege.metaproject.api.*;

import java.io.Serializable;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of an operation consisting of a unique identifier, a natural language description, and operation prerequisites
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class OperationImpl<T> implements Serializable, Operation<T> {
    private static final long serialVersionUID = -3660230416713349013L;
    private final Id id;
    private final Name name;
    private final Description description;
    private final ImmutableSet<OperationPrerequisite<T>> prerequisites;

    /**
     * Constructor
     * @param id   Operation identifier
     * @param operationName Operation name
     * @param description  Operation description
     * @param prerequisites Operation prerequisites
     */
    public OperationImpl(Id id, Name operationName, Description description, Set<OperationPrerequisite<T>> prerequisites) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(operationName);
        this.description = checkNotNull(description);
        ImmutableSet<OperationPrerequisite<T>> prerequisitesCopy = new ImmutableSet.Builder<OperationPrerequisite<T>>().addAll(checkNotNull(prerequisites)).build();
        this.prerequisites = checkNotNull(prerequisitesCopy);
    }

    /**
     * Get the operation identifier
     *
     * @return Operation identifier
     */
    @Override
    public Id getId() {
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
     * Get the set of prerequisites for the operation
     *
     * @return Set of operation prerequisites
     */
    @Override
    public Set<OperationPrerequisite<T>> getPrerequisites() {
        return prerequisites;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("prerequisites", prerequisites)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationImpl that = (OperationImpl) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(description, that.description) &&
                Objects.equal(prerequisites, that.prerequisites);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, prerequisites);
    }
}
