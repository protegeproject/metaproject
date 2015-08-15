package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.IdPrefix;
import edu.stanford.protege.metaproject.api.IdSuffix;
import edu.stanford.protege.metaproject.api.OntologyTermId;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class OntologyTermIdImpl implements OntologyTermId, Serializable {
    private static final long serialVersionUID = -3431336312540440484L;
    private final IdPrefix prefix;
    private final IdSuffix suffix;

    /**
     * Constructor
     *
     * @param prefix    Identifier prefix
     * @param suffix    Identifier suffix
     */
    public OntologyTermIdImpl(IdPrefix prefix, IdSuffix suffix) {
        this.prefix = checkNotNull(prefix);
        this.suffix = checkNotNull(suffix);
    }

    /**
     * Get the full identifier (i.e., concatenation of prefix and suffix)
     *
     * @return Full identifier built from the prefix and suffix
     */
    @Override
    public String get() {
        return prefix.get()+ suffix.get();
    }

    /**
     * Get the identifier prefix
     *
     * @return Identifier prefix
     */
    @Override
    public IdPrefix getPrefix() {
        return prefix;
    }

    /**
     * Get the identifier suffix
     *
     * @return Identifier suffix
     */
    @Override
    public IdSuffix getSuffix() {
        return suffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OntologyTermIdImpl that = (OntologyTermIdImpl) o;
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
