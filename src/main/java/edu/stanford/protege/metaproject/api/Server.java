package edu.stanford.protege.metaproject.api;

/**
 * A representation of a server
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Server {

    /**
     * Get the server configuration
     *
     * @return Server configuration
     */
    ServerConfiguration getConfiguration();

}
