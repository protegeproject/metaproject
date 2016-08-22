package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.Description;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class DescriptionImpl implements Description, Serializable {
    private static final long serialVersionUID = -3951323580770065998L;
    @Nonnull private final String description;

    /**
     * Constructor
     *
     * @param description    Description
     */
    public DescriptionImpl(@Nonnull String description) {
        this.description = checkNotNull(description);
    }

    @Override
    @Nonnull
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
