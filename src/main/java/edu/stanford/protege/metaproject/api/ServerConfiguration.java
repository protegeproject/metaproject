package edu.stanford.protege.metaproject.api;

/**
 * Manager for server-side configurations
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ServerConfiguration extends Configuration {

    /**
     * Get the server host
     *
     * @return Server host
     */
    Host getHost();

    /**
     * Get the access control policy for the server
     *
     * @return Access control policy
     */
    Policy getPolicy();

    /**
     * Get the ontology term identifier generator
     *
     * @return Ontology term identifier generator instance
     */
    OntologyTermIdGenerator getOntologyTermIdGenerator();

    /**
     * Get the access control object identifier generator
     *
     * @return Access control object identifier generator
     */
    AccessControlObjectIdGenerator getAccessControlObjectIdGenerator();

}