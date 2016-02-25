package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Port;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class PortImpl implements Port, Serializable {
    private static final long serialVersionUID = 4965093309501055806L;
    private final Integer port;

    /**
     * Constructor
     *
     * @param port  Port number
     */
    public PortImpl(Integer port) {
        this.port = checkNotNull(port);
    }

    @Override
    public Integer get() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortImpl port1 = (PortImpl) o;
        return Objects.equal(port, port1.port);
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
}
