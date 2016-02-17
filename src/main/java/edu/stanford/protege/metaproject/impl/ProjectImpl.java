package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableSet;
import edu.stanford.protege.metaproject.api.*;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of a project, consisting of a unique (internal) identifier, a (display) name, and a
 * description. A project is owned by a single user, and can have multiple administrators.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ProjectImpl implements Project, Serializable, Comparable<Project> {
    private static final long serialVersionUID = 8619786837725236237L;
    private final ProjectId id;
    private final Name name;
    private final Description description;
    private final Address address;
    private final UserId owner;
    private final ImmutableSet<UserId> administrators;

    /**
     * Constructor
     *
     * @param id Project identifier
     * @param name   Project name
     * @param description    Project description
     * @param address  Project address
     * @param owner Owner of the project
     * @param administrators    Administrators of the project
     */
    public ProjectImpl(ProjectId id, Name name, Description description, Address address, UserId owner, Set<UserId> administrators) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.description = checkNotNull(description);
        this.address = checkNotNull(address);
        this.owner = checkNotNull(owner);

        ImmutableSet<UserId> administratorsCopy = new ImmutableSet.Builder<UserId>().addAll(checkNotNull(administrators)).build();
        this.administrators = checkNotNull(administratorsCopy);
    }

    /**
     * Get the identifier of the project
     *
     * @return Project identifier
     */
    @Override
    public ProjectId getId() {
        return id;
    }

    /**
     * Get the name of the project
     *
     * @return Project name
     */
    @Override
    public Name getName() {
        return name;
    }

    /**
     * Get the description of the project
     *
     * @return Project description
     */
    @Override
    public Description getDescription() {
        return description;
    }

    /**
     * Get the location of the project
     *
     * @return Project location
     */
    @Override
    public Address getAddress() {
        return address;
    }

    /**
     * Get the owner user of the project
     *
     * @return Project owner user
     */
    @Override
    public UserId getOwner() {
        return owner;
    }

    /**
     * Get the administrators of the project
     *
     * @return Set of users that administrate the project
     */
    @Override
    public Set<UserId> getAdministrators() {
        return administrators;
    }

    /**
     * Check whether the specified user is an administrator of the project
     *
     * @param userId  User identifier
     * @return true if user is an administrator of this project, false otherwise
     */
    @Override
    public boolean hasAdministrator(UserId userId) {
        return administrators.contains(userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectImpl that = (ProjectImpl) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(description, that.description) &&
                Objects.equal(address, that.address) &&
                Objects.equal(owner, that.owner) &&
                Objects.equal(administrators, that.administrators);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, address, owner, administrators);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("address", address)
                .add("owner", owner)
                .add("administrators", administrators)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull Project that) {
        return ComparisonChain.start()
                .compare(this.id.get(), that.getId().get())
                .result();
    }
}
