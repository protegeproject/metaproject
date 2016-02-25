package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Address;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.Port;
import edu.stanford.protege.metaproject.api.RegistryPort;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class HostImpl implements Host, Serializable {
    private static final long serialVersionUID = -7354667414457398985L;
    private final Address address;
    private final Port port;
    private final RegistryPort registryPort;

    /**
     * Constructor
     *
     * @param address   Host address
     * @param port  Host port
     * @param registryPort RMI registry port
     */
    public HostImpl(Address address, Port port, RegistryPort registryPort) {
        this.address = checkNotNull(address);
        this.port = checkNotNull(port);
        this.registryPort = checkNotNull(registryPort);
    }

    @Override
    public Port getPort() {
        return port;
    }

    @Override
    public RegistryPort getRegistryPort() {
        return registryPort;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("address", address)
                .add("port", port)
                .add("registryPort", registryPort)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostImpl host = (HostImpl) o;
        return Objects.equal(address, host.address) &&
                Objects.equal(port, host.port) &&
                Objects.equal(registryPort, host.registryPort);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address, port, registryPort);
    }
}
