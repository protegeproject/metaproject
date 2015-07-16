package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of a natural language description of a project
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ProjectDescription extends Description implements Serializable {
    private static final long serialVersionUID = 2143323918627966073L;
    private final String projectDescription;

    /**
     * Constructor
     *
     * @param projectDescription    Project description
     */
    public ProjectDescription(String projectDescription) {
        this.projectDescription = checkNotNull(projectDescription);
    }

    /**
     * Get project description
     *
     * @return Project description
     */
    public String getDescription() {
        return projectDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDescription that = (ProjectDescription) o;
        return Objects.equal(projectDescription, that.projectDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(projectDescription);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("projectDescription", projectDescription)
                .toString();
    }
}
