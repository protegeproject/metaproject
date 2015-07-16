package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of a name of a project
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ProjectName extends Name implements Serializable {
    private static final long serialVersionUID = 7432843290599095794L;
    private final String projectName;

    /**
     * Constructor
     *
     * @param projectName   Project name
     */
    public ProjectName(String projectName) {
        this.projectName = checkNotNull(projectName);
    }

    /**
     * Get the name of the project
     *
     * @return Project name
     */
    public String getName() {
        return projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectName that = (ProjectName) o;
        return Objects.equal(projectName, that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(projectName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("projectName", projectName)
                .toString();
    }
}
