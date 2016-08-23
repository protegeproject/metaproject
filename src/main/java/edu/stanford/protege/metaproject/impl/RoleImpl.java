package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableSet;
import edu.stanford.protege.metaproject.api.*;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.io.Serializable;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class RoleImpl implements Role, Serializable {
    private static final long serialVersionUID = -1651603573909818594L;
    @Nonnull private final RoleId id;
    @Nonnull private final Name name;
    @Nonnull private final Description description;
    @Nonnull private final ImmutableSet<OperationId> operations;

    /**
     * Constructor
     *
     * @param id    Role identifier
     * @param name  Role name
     * @param description   Role description
     * @param operations    Set of operations that can be performed on the given projects
     */
    public RoleImpl(@Nonnull RoleId id, @Nonnull Name name, @Nonnull Description description, @Nonnull Set<OperationId> operations) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.description = checkNotNull(description);
        this.operations = ImmutableSet.copyOf(checkNotNull(operations));
    }

    @Override
    @Nonnull
    public RoleId getId() {
        return id;
    }

    @Override
    @Nonnull
    public Name getName() {
        return name;
    }

    @Override
    @Nonnull
    public Description getDescription() {
        return description;
    }

    @Override
    @Nonnull
    public ImmutableSet<OperationId> getOperations() {
        return operations;
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
        return true;
    }

    @Override
    public boolean isOperation() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        Role that = (Role) o;
        return Objects.equal(id, that.getId()) &&
                Objects.equal(name, that.getName()) &&
                Objects.equal(description, that.getDescription()) &&
                Objects.equal(operations, that.getOperations());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, operations);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("operations", operations)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull Role that) {
        return ComparisonChain.start()
                .compare(this.name.get(), that.getName().get())
                .result();
    }
}
