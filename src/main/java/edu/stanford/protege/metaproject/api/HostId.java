package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple representation of a hostname
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class HostId extends Identifier implements Serializable {
    private static final long serialVersionUID = 2973283101789401093L;
    private final String hostId;

    /**
     * Constructor
     *
     * @param hostId    Host identifier
     */
    public HostId(String hostId) {
        this.hostId = checkNotNull(hostId);
    }

    /**
     * Get the host name
     *
     * @return Host name
     */
    public String getId() {
        return hostId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostId hostId1 = (HostId) o;
        return Objects.equal(hostId, hostId1.hostId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hostId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("hostId", hostId)
                .toString();
    }
}
