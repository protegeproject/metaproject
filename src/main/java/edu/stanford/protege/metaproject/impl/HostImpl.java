package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.Port;

import java.io.Serializable;
import java.net.URI;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class HostImpl implements Host, Serializable {
    private static final long serialVersionUID = -1110049103826593951L;
    private final URI uri;
    private final Port secondaryPort;

    /**
     * Constructor
     *
     * @param uri   Host uri
     * @param secondaryPort Secondary port
     */
    public HostImpl(URI uri, Optional<Port> secondaryPort) {
        this.uri = checkNotNull(uri);
        this.secondaryPort = (secondaryPort.isPresent() ? checkNotNull(secondaryPort.get()) : null);
    }

    @Override
    public Optional<Port> getSecondaryPort() {
        return Optional.ofNullable(secondaryPort);
    }

    @Override
    public URI getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uri", uri)
                .add("secondaryPort", secondaryPort)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HostImpl)) {
            return false;
        }
        HostImpl host = (HostImpl) o;
        return Objects.equal(uri, host.uri) &&
                Objects.equal(secondaryPort, host.secondaryPort);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uri, secondaryPort);
    }
}
