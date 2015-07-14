package edu.stanford.protege.metaproject;

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
     * Restart the server
     */
    void restart();

    /**
     * Shutdown the server
     */
    void shutdown();

    /**
     * Flush out the given commit to the server
     *
     * @param commit    Commit instance
     */
    void commit(Commit commit);

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
