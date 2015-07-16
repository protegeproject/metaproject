package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of a role name
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class RoleName extends Name implements Serializable {
    private static final long serialVersionUID = 8037040189909495509L;
    private final String roleName;

    /**
     * Constructor
     *
     * @param roleName  Role name
     */
    public RoleName(String roleName) {
        this.roleName = checkNotNull(roleName);
    }

    /**
     * Get the name of the role
     *
     * @return  Role name
     */
    public String getName() {
        return roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleName roleName1 = (RoleName) o;
        return Objects.equal(roleName, roleName1.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roleName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("roleName", roleName)
                .toString();
    }
}
