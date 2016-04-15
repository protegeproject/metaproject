package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.*;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.Serializable;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ProjectImpl implements Project, Serializable {
    private static final long serialVersionUID = 4575272908702931683L;
    private final ProjectId id;
    private final Name name;
    private final Description description;
    private final File file;
    private final UserId owner;
    private final ProjectOptions options;

    /**
     * Constructor
     *
     * @param id Project identifier
     * @param name   Project name
     * @param description    Project description
     * @param file  Project file
     * @param owner Owner of the project
     * @param options   Project options
     */
    public ProjectImpl(ProjectId id, Name name, Description description, File file, UserId owner, Optional<ProjectOptions> options) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.description = checkNotNull(description);
        this.file = checkNotNull(file);
        this.owner = checkNotNull(owner);
        this.options = (options.isPresent() ? checkNotNull(options.get()) : null);
    }

    @Override
    public ProjectId getId() {
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

    public File getFile() {
        return file;
    }

    @Override
    public UserId getOwner() {
        return owner;
    }

    @Override
    public Optional<ProjectOptions> getOptions() {
        return Optional.ofNullable(options);
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectImpl project = (ProjectImpl) o;
        return Objects.equal(id, project.id) &&
                Objects.equal(name, project.name) &&
                Objects.equal(description, project.description) &&
                Objects.equal(file, project.file) &&
                Objects.equal(owner, project.owner) &&
                Objects.equal(options, project.options);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, file, owner, options);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("file", file)
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
