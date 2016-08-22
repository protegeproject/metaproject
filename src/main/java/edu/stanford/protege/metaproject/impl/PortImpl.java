package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.Port;

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
public final class PortImpl implements Port, Serializable {
    private static final long serialVersionUID = 5510762950428135513L;
    @Nonnull private final Integer port;

    /**
     * Constructor
     *
     * @param port  Port number
     */
    public PortImpl(@Nonnull Integer port) {
        this.port = checkNotNull(port);
    }

    @Override
    @Nonnull
    public Integer get() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Port)) {
            return false;
        }
        Port that = (Port) o;
        return Objects.equal(port, that.get());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(port);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("port", port)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull Port that) {
        return ComparisonChain.start()
                .compare(port, that.get())
                .result();
    }
}
