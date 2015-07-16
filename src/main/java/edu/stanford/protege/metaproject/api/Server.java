package edu.stanford.protege.metaproject.api;

/**
 * A representation of a server
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Server {

    /**
     * Get the owner (root user) of the server, who can do operations like delete projects and shutdown the server
     *
     * @return User identifier
     */
    UserId getOwner();

    /**
     * Get the access control policy of the server
     *
     * @return Access control policy instance
     */
    AccessControlPolicy getPolicy();

    /**
     * Get the server configuration
     *
     * @return Server configuraration
     */
    ServerConfiguration getConfiguration();

}
