package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.ProjectId;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of a project identifier
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ProjectIdImpl implements ProjectId, Serializable {
    private static final long serialVersionUID = -255425634150665564L;
    private final String id;

    /**
     * Constructor
     *
     * @param id    Identifier
     */
    public ProjectIdImpl(String id) {
        this.id = checkNotNull(id);
    }

    @Override
    public String get() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectIdImpl that = (ProjectIdImpl) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
