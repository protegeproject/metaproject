package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.Port;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.io.Serializable;
import java.net.URI;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class HostImpl implements Host, Serializable {
    private static final long serialVersionUID = -7653583714132431492L;
    @Nonnull private final URI uri;
    @Nonnull private final Optional<Port> secondaryPort;

    /**
     * Constructor
     *
     * @param uri   Host uri
     * @param secondaryPort Secondary port
     */
    public HostImpl(@Nonnull URI uri, @Nonnull Optional<Port> secondaryPort) {
        this.uri = checkNotNull(uri);
        this.secondaryPort = checkNotNull(secondaryPort);
    }

    @Override
    @Nonnull
    public Optional<Port> getSecondaryPort() {
        return secondaryPort;
    }

    @Override
    @Nonnull
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
        if (!(o instanceof Host)) {
            return false;
        }
        Host host = (Host) o;
        return Objects.equal(uri, host.getUri()) &&
                Objects.equal(secondaryPort, host.getSecondaryPort());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uri, secondaryPort);
    }
}
