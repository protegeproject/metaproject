package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.DateProperty;

import java.io.Serializable;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ModificationDate implements DateProperty, Serializable {
    private static final long serialVersionUID = 2722753762567493760L;
    private final Date date;

    /**
     * Constructor
     *
     * @param date  Date
     */
    public ModificationDate(Date date) {
        this.date = checkNotNull(date);
    }

    /**
     * Get the modification date
     *
     * @return Modification date
     */
    public Date get() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModificationDate that = (ModificationDate) o;
        return Objects.equal(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("date", date)
                .toString();
    }
}
