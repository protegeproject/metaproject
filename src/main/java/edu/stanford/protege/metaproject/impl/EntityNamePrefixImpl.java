package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.EntityNamePrefix;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class EntityNamePrefixImpl implements EntityNamePrefix, Serializable {
    private static final long serialVersionUID = -6109573628527568751L;
    static final EntityNamePrefix EMPTY = new EntityNamePrefixImpl("");
    private final String prefix;

    /**
     * Constructor
     *
     * @param prefix    Entity name prefix
     */
    public EntityNamePrefixImpl(String prefix) {
        this.prefix = checkNotNull(prefix);
    }

    @Override
    public String get() {
        return prefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityNamePrefixImpl that = (EntityNamePrefixImpl) o;
        return Objects.equal(prefix, that.prefix);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(prefix);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("prefix", prefix)
                .toString();
    }
}
