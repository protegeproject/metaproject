package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Address;
import edu.stanford.protege.metaproject.api.Host;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class HostImpl implements Host, Serializable {
    private static final long serialVersionUID = 5091292167433983241L;
    private final Address address;
    private final int port;

    /**
     * Constructor
     * @param address   Host address
     * @param port  Host port
     */
    public HostImpl(Address address, int port) {
        this.address = checkNotNull(address);
        this.port = checkNotNull(port);
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostImpl host = (HostImpl) o;
        return Objects.equal(port, host.port) &&
                Objects.equal(address, host.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address, port);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("address", address)
                .add("port", port)
                .toString();
    }
}
