package edu.stanford.protege.metaproject.api;

import java.io.File;

/**
 * A representation of a server configuration, composed of host information, the metaproject access control policy, the root directory
 * of the server (where project files are located), the authentication registry, and optional additional configuration properties.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ServerConfiguration extends HasModifiableProperties {

    /**
     * Get the server host
     *
     * @return Server host
     */
    Host getHost();

    /**
     * Get the root directory of the server, where (ontology) project files are located
     *
     * @return Server root directory
     */
    File getServerRoot();

    /**
     * Get the manager of the access control policy in effect on the server
     *
     * @return Metaproject
     */
    Metaproject getMetaproject();

    /**
     * Get the user authentication manager
     *
     * @return Authentication manager
     */
    AuthenticationRegistry getAuthenticationRegistry();

    /**
     * Set the server host
     *
     * @param host  Server host
     */
    void setHost(Host host);

    /**
     * Set the server root directory
     *
     * @param root  Server root directory
     */
    void setServerRoot(File root);

}