package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import edu.stanford.protege.metaproject.api.*;

import java.io.Serializable;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A role defines a group of allowed operations within some project
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class RoleImpl implements Role, Serializable {
    private static final long serialVersionUID = 352991724988559697L;
    private final Id id;
    private final Name name;
    private final Description description;
    private final ImmutableSet<Project> projects;
    private final ImmutableSet<Operation> operations;

    /**
     * Constructor
     *
     * @param id    Role identifier
     * @param name  Role name
     * @param description   Role description
     * @param projects  Set of projects within which the specified operations can be performed
     * @param operations    Set of operations that can be performed on the given projects
     */
    public RoleImpl(Id id, Name name, Description description, Set<Project> projects, Set<Operation> operations) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.description = checkNotNull(description);

        ImmutableSet<Project> projectsCopy = new ImmutableSet.Builder<Project>().addAll(checkNotNull(projects)).build();
        this.projects = checkNotNull(projectsCopy);

        ImmutableSet<Operation> operationsCopy = new ImmutableSet.Builder<Operation>().addAll(checkNotNull(operations)).build();
        this.operations = checkNotNull(operationsCopy);
    }

    /**
     * Get the role identifier
     *
     * @return Role identifier
     */
    @Override
    public Id getId() {
        return id;
    }

    /**
     * Get the role name
     * @return  Role name
     */
    @Override
    public Name getName() {
        return name;
    }

    /**
     * Get the role description
     *
     * @return Role description
     */
    @Override
    public Description getDescription() {
        return description;
    }

    /**
     * Get all projects associated with this role
     *
     * @return Set of project identifiers
     */
    @Override
    public Set<Project> getProjects() {
        return projects;
    }

    /**
     * Get the set of operations associated with this role
     *
     * @return Set of operations identifiers
     */
    @Override
    public Set<Operation> getOperations() {
        return operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleImpl role = (RoleImpl) o;
        return Objects.equal(id, role.id) &&
                Objects.equal(name, role.name) &&
                Objects.equal(description, role.description) &&
                Objects.equal(projects, role.projects) &&
                Objects.equal(operations, role.operations);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, projects, operations);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("projects", projects)
                .add("operations", operations)
                .toString();
    }
}
