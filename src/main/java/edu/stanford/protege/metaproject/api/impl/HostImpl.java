package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Address;
import edu.stanford.protege.metaproject.api.Host;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class HostImpl implements Host {
    private final Address hostAddress;
    private final int hostPort;

    public HostImpl(Address hostAddress, int hostPort) {
        this.hostAddress = checkNotNull(hostAddress);
        this.hostPort = checkNotNull(hostPort);
    }

    @Override
    public int getPort() {
        return hostPort;
    }

    @Override
    public Address getAddress() {
        return hostAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostImpl host = (HostImpl) o;
        return Objects.equal(hostPort, host.hostPort) &&
                Objects.equal(hostAddress, host.hostAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hostAddress, hostPort);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("hostAddress", hostAddress)
                .add("hostPort", hostPort)
                .toString();
    }
}
