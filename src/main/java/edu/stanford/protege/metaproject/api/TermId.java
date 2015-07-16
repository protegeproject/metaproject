package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of a unique term identifier
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class TermId extends Identifier implements Serializable {
    private static final long serialVersionUID = -2490253450811847338L;
    private final String termId;

    /**
     * Constructor
     *
     * @param termId    Term identifier
     */
    public TermId(String termId) {
        this.termId = checkNotNull(termId);
    }

    /**
     * Get the term identifier
     *
     * @return Term identifier
     */
    public String getId() {
        return termId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TermId termId1 = (TermId) o;
        return Objects.equal(termId, termId1.termId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(termId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("termId", termId)
                .toString();
    }
}
