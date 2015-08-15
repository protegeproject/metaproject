package edu.stanford.protege.metaproject.api;

/**
 * A representation of a client
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Client {

    /**
     * Get the client configuration
     *
     * @return Client configuration
     */
    ClientConfiguration getConfiguration();

}
