package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.UserId;

import javax.annotation.Nonnull;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class UserIdImpl implements UserId, Serializable {
    private static final long serialVersionUID = 2302244066978932230L;
    private final String id;

    /**
     * Constructor
     *
     * @param id    Identifier
     */
    public UserIdImpl(String id) {
        this.id = checkNotNull(id);
    }

    @Override
    public String get() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!getClass().getName().equals(o.getClass().getName())) return false;
        UserId that = (UserId) o;
        return Objects.equal(this.get(), that.get());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull UserId that) {
        return ComparisonChain.start()
                .compare(id, that.get())
                .result();
    }
}
