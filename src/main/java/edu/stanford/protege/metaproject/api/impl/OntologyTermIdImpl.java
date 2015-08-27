package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.OntologyTermId;
import edu.stanford.protege.metaproject.api.OntologyTermIdPrefix;
import edu.stanford.protege.metaproject.api.OntologyTermIdSuffix;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of an identifier of an ontology term, such as a class or property
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class OntologyTermIdImpl implements OntologyTermId, Serializable {
    private static final long serialVersionUID = -1818949363645495437L;
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
