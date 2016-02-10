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

    /**
     * Get the ontology entity IRI generator
     *
     * @return Entity IRI generator
     */
    EntityIriGenerator getEntityIriGenerator();

    /**
     * Update the server configuration with a new access control policy
     *
     * @param accessControlPolicy    New access control policy
     */
    void updateConfiguration(AccessControlPolicy accessControlPolicy);

    /**
     * Update the server configuration with a new ontology term identifier status, i.e.,
     * the last generated identifiers for ontology terms
     *
     * @param entityIriStatus  New ontology term identifier status
     */
    void updateConfiguration(EntityIriStatus entityIriStatus);

}
