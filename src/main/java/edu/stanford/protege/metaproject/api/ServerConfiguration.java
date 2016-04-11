package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.IdAlreadyInUseException;

import java.io.File;

/**
 * A representation of a server configuration, composed of host information, the access control policy, the status of
 * ontology term identifiers (i.e., last generated identifiers), and optional additional configuration properties
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ServerConfiguration extends HasProperties {

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
     * Enable the guest user with the usual guest credentials
     *
     * @param enableGuestUser   true if guest user should be enabled, false otherwise
     * @throws IdAlreadyInUseException  Guest user identifier is already taken by another user
     */
    void enableGuestUser(boolean enableGuestUser) throws IdAlreadyInUseException;

}