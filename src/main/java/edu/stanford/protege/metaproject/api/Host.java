package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.Optional;

/**
 * A representation of host information, consisting of a URI and a secondary (optional) port
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface Host {

    /**
     * Get the URI of the server host
     *
     * @return URI of host
     */
    @Nonnull
    URI getUri();

    /**
     * Get the secondary port that may be used, for example, as an RMI registry port
     *
     * @return Optional secondary port
     */
    @Nonnull
    Optional<Port> getSecondaryPort();

}
