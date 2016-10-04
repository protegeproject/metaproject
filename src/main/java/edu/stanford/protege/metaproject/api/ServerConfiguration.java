package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * A representation of a server configuration, composed of host information, the access control policy, the root directory of the
 * server (where project files are located), the authentication registry, and optional additional configuration properties.
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface ServerConfiguration extends ConfigurationBrowser, HasProperties {

    /**
     * Get the server host
     *
     * @return Server host
     */
    @Nonnull
    Host getHost();

    /**
     * Get the root directory of the server, where (ontology) project files are located
     *
     * @return Server root directory
     */
    @Nonnull
    String getServerRoot();

}