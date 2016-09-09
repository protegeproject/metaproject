package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.*;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.io.Serializable;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class ProjectImpl implements Project, Serializable {
    private static final long serialVersionUID = -2849746766544935723L;
    @Nonnull private final ProjectId id;
    @Nonnull private final Name name;
    @Nonnull private final Description description;
    @Nonnull private final UserId owner;
    @Nonnull private final Optional<String> filePath;
    @Nonnull private final Optional<ProjectOptions> options;

    /**
     * Constructor
     *
     * @param id Project identifier
     * @param name   Project name
     * @param description    Project description
     * @param filePath  Project file path
     * @param owner Owner of the project
     * @param options   Project options
     */
    public ProjectImpl(@Nonnull ProjectId id, @Nonnull Name name, @Nonnull Description description, @Nonnull UserId owner, @Nonnull Optional<String> filePath, @Nonnull Optional<ProjectOptions> options) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.description = checkNotNull(description);
        this.filePath = checkNotNull(filePath);
        this.owner = checkNotNull(owner);
        this.options = checkNotNull(options);
    }

    @Override
    @Nonnull
    public ProjectId getId() {
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
    public UserId getOwner() {
        return owner;
    }

    @Override
    @Nonnull
    public Optional<String> getFilePath() {
        return filePath;
    }

    @Override
    @Nonnull
    public Optional<ProjectOptions> getOptions() {
        return options;
    }

    @Override
    public boolean isUser() {
        return false;
    }

    @Override
    public boolean isProject() {
        return true;
    }

    @Override
    public boolean isRole() {
        return false;
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
        if (!(o instanceof Project)) {
            return false;
        }
        Project that = (Project) o;
        return Objects.equal(id, that.getId()) &&
                Objects.equal(name, that.getName()) &&
                Objects.equal(description, that.getDescription()) &&
                Objects.equal(filePath, that.getFilePath()) &&
                Objects.equal(owner, that.getOwner()) &&
                Objects.equal(options, that.getOptions());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, filePath, owner, options);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("filePath", filePath)
                .add("owner", owner)
                .add("options", options)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull Project that) {
        return ComparisonChain.start()
                .compare(this.name.get(), that.getName().get())
                .result();
    }
}
