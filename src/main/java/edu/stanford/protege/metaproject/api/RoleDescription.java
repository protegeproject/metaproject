package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of a natural language description of a role
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleDescription implements Serializable, HasDescription {
    private final String description;

    /**
     * Constructor
     *
     * @param description   Role description
     */
    public RoleDescription(String description) {
        this.description = checkNotNull(description);
    }

    /**
     * Get the role description
     *
     * @return Role description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDescription that = (RoleDescription) o;
        return Objects.equal(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("description", description)
                .toString();
    }
}
