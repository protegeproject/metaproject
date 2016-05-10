package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.Description;

import javax.annotation.Nonnull;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class DescriptionImpl implements Description, Serializable {
    private static final long serialVersionUID = -3951323580770065998L;
    private final String description;

    /**
     * Constructor
     *
     * @param description    Description
     */
    public DescriptionImpl(String description) {
        this.description = checkNotNull(description);
    }

    @Override
    public String get() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Description)) {
            return false;
        }
        Description that = (Description) o;
        return Objects.equal(description, that.get());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("description", description)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull Description that) {
        return ComparisonChain.start()
                .compare(description, that.get())
                .result();
    }
}
