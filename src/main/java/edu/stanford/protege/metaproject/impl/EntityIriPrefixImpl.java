package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.EntityIriPrefix;

import java.io.Serializable;

/**
 * A representation of a prefix of a term identifier
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class EntityIriPrefixImpl implements EntityIriPrefix, Serializable {
    private static final long serialVersionUID = -3067420543936427448L;
    private final String prefix;

    /**
     * Constructor
     *
     * @param prefix    Prefix string
     */
    public EntityIriPrefixImpl(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Get the term identifier prefix as a string
     *
     * @return Prefix string
     */
    @Override
    public String get() {
        return prefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityIriPrefixImpl that = (EntityIriPrefixImpl) o;
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
