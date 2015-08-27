package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.OntologyTermIdPrefix;
import edu.stanford.protege.metaproject.api.OntologyTermIdSuffix;
import edu.stanford.protege.metaproject.api.OntologyTermId;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class OntologyTermIdImpl implements OntologyTermId, Serializable {
    private static final long serialVersionUID = -3431336312540440484L;
    private final OntologyTermIdPrefix prefix;
    private final OntologyTermIdSuffix suffix;

    /**
     * Constructor
     *
     * @param prefix    Identifier prefix
     * @param suffix    Identifier suffix
     */
    public OntologyTermIdImpl(OntologyTermIdPrefix prefix, OntologyTermIdSuffix suffix) {
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
    public OntologyTermIdPrefix getPrefix() {
        return prefix;
    }

    /**
     * Get the identifier suffix
     *
     * @return Identifier suffix
     */
    @Override
    public OntologyTermIdSuffix getSuffix() {
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
