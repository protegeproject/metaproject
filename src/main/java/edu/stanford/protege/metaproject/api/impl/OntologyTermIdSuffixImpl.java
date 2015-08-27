package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.OntologyTermIdSuffix;

import java.io.Serializable;

/**
 * A representation of a suffix of a term identifier
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class OntologyTermIdSuffixImpl implements OntologyTermIdSuffix, Serializable {
    private static final long serialVersionUID = -4323837642196972627L;
    private final String suffix;

    /**
     * Constructor
     *
     * @param suffix    Prefix string
     */
    public OntologyTermIdSuffixImpl(String suffix) {
        this.suffix = suffix;
    }

    /**
     * Get the term identifier suffix as a string
     *
     * @return Prefix string
     */
    public String get() {
        return suffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OntologyTermIdSuffixImpl that = (OntologyTermIdSuffixImpl) o;
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
