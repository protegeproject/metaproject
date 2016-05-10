package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.Name;

import javax.annotation.Nonnull;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class NameImpl implements Name, Serializable {
    private static final long serialVersionUID = -5327050252921880749L;
    private final String name;

    /**
     * Constructor
     *
     * @param name    Name
     */
    public NameImpl(String name) {
        this.name = checkNotNull(name);
    }

    @Override
    public String get() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Name)) {
            return false;
        }
        Name that = (Name) o;
        return Objects.equal(name, that.get());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull Name that) {
        return ComparisonChain.start()
                .compare(name, that.get())
                .result();
    }
}
