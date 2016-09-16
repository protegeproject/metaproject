package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.UserId;

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
public final class UserIdImpl implements UserId, Serializable {
    private static final long serialVersionUID = 2302244066978932230L;
    @Nonnull private final String id;

    /**
     * Constructor
     *
     * @param id    Identifier
     */
    public UserIdImpl(@Nonnull String id) {
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
        if (!(o instanceof UserId)) {
            return false;
        }
        UserId that = (UserId) o;
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
    public int compareTo(@Nonnull UserId that) {
        return ComparisonChain.start()
                .compare(id, that.get())
                .result();
    }
}
