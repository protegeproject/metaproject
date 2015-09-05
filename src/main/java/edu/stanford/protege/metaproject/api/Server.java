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
     * Get the ontology term identifier generator
     *
     * @return Ontology term identifier generator
     */
    OntologyTermIdGenerator getOntologyTermIdGenerator();

    /**
     * Update the server configuration with a new metaproject definition
     *
     * @param metaproject    New metaproject
     */
    void updateConfiguration(Metaproject metaproject);

    /**
     * Update the server configuration with a new ontology term identifier status, i.e.,
     * the last generated identifiers for ontology terms
     *
     * @param ontologyTermIdStatus  New ontology term identifier status
     */
    void updateConfiguration(OntologyTermIdStatus ontologyTermIdStatus);

}
