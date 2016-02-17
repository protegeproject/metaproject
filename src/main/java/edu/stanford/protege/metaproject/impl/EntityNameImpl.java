package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.EntityName;
import edu.stanford.protege.metaproject.api.EntityNamePrefix;
import edu.stanford.protege.metaproject.api.EntityNameSuffix;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class EntityNameImpl implements EntityName, Serializable {
    private static final long serialVersionUID = -6576503821552716002L;
    private final EntityNamePrefix prefix;
    private final EntityNameSuffix suffix;

    /**
     * Constructor
     *
     * @param prefix    Entity name prefix
     * @param suffix    Entity name suffix
     */
    public EntityNameImpl(EntityNamePrefix prefix, EntityNameSuffix suffix) {
        this.prefix = checkNotNull(prefix);
        this.suffix = checkNotNull(suffix);
    }

    @Override
    public String get() {
        return prefix.get() + suffix.get();
    }

    @Override
    public EntityNamePrefix getPrefix() {
        return prefix;
    }

    @Override
    public EntityNameSuffix getSuffix() {
        return suffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityNameImpl that = (EntityNameImpl) o;
        return Objects.equal(prefix, that.prefix) &&
                Objects.equal(suffix, that.suffix);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(prefix, suffix);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("prefix", prefix)
                .add("suffix", suffix)
                .toString();
    }
}
