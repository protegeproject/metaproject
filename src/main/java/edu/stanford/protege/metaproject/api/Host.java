package edu.stanford.protege.metaproject.api;

import java.net.URI;
import java.util.Optional;

/**
 * A representation of host information, consisting of a URI and a secondary (optional) port
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Host {

    /**
     * Get the URI of the server host
     *
     * @return URI of host
     */
    URI getUri();

    /**
     * Get the secondary port that may be used, for example, as an RMI registry port
     *
     * @return Optional secondary port
     */
    Optional<Port> getSecondaryPort();

}
