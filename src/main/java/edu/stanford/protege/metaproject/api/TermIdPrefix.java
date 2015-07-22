package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A representation of a prefix of a term identifier
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class TermIdPrefix {
    private final String prefix;

    /**
     * Constructor
     *
     * @param prefix    Prefix string
     */
    public TermIdPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Get the term identifier prefix as a string
     *
     * @return Prefix string
     */
    public String getPrefix() {
        return prefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TermIdPrefix that = (TermIdPrefix) o;
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
