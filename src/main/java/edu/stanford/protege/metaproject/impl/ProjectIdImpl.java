package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.ProjectId;

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
public final class ProjectIdImpl implements ProjectId, Serializable {
    private static final long serialVersionUID = 3419677289045594606L;
    @Nonnull private final String id;

    /**
     * Constructor
     *
     * @param id    Identifier
     */
    public ProjectIdImpl(@Nonnull String id) {
        this.id = checkNotNull(id);
    }

    @Override
    @Nonnull
    public String get() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectId)) {
            return false;
        }
        ProjectId that = (ProjectId) o;
        return Objects.equal(id, that.get());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(id)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull ProjectId that) {
        return ComparisonChain.start()
                .compare(id, that.get())
                .result();
    }
}
