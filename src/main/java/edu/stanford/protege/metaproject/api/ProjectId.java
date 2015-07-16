package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of a unique project identifier
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ProjectId extends Identifier implements Serializable {
    private static final long serialVersionUID = -3388962492679680842L;
    private final String projectId;

    /**
     * Constructor
     *
     * @param projectId Project identifier
     */
    public ProjectId(String projectId) {
        this.projectId = checkNotNull(projectId);
    }

    /**
     * Get project identifier
     *
     * @return Project identifier
     */
    public String getId() {
        return projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectId projectId1 = (ProjectId) o;
        return Objects.equal(projectId, projectId1.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(projectId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("projectId", projectId)
                .toString();
    }
}
