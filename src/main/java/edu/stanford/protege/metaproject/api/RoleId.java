package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of a unique role identifier
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleId implements Serializable, HasId {
    private final String roleId;

    /**
     * Constructor
     *
     * @param roleId    Role identifier
     */
    public RoleId(String roleId) {
        this.roleId = checkNotNull(roleId);
    }

    /**
     * Get the role identifier
     *
     * @return Role identifier
     */
    public String getId() {
        return roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleId roleId1 = (RoleId) o;
        return Objects.equal(roleId, roleId1.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roleId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("roleId", roleId)
                .toString();
    }
}
