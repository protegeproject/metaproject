package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.EntityNameSuffix;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class EntityNameSuffixImpl implements EntityNameSuffix, Serializable {
    private static final long serialVersionUID = 907682460108443260L;
    private final String suffix;

    /**
     * Constructor
     *
     * @param suffix    Entity name suffix
     */
    public EntityNameSuffixImpl(String suffix) {
        this.suffix = checkNotNull(suffix);
    }

    @Override
    public String get() {
        return suffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityNameSuffixImpl that = (EntityNameSuffixImpl) o;
        return Objects.equal(suffix, that.suffix);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(suffix);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("suffix", suffix)
                .toString();
    }
}
