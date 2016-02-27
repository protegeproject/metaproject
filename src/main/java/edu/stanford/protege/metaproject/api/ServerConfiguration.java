package edu.stanford.protege.metaproject.api;

import java.util.Map;
import java.util.Optional;

/**
 * A representation of a server configuration, composed of host information, the access control policy, the status of
 * ontology term identifiers (i.e., last generated identifiers), and optional additional configuration properties
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ServerConfiguration {

    /**
     * Get the server host
     *
     * @return Server host
     */
    Host getHost();

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
    AuthenticationManager getAuthenticationManager();

    /**
     * Get the server conflict manager
     *
     * @return Conflict manager
     */
    Optional<ConflictManager> getConflictManager();

    /**
     * Get the ontology term identifier status, which contains the last generated identifiers and/or their prefixes
     *
     * @return Ontology term identifier status instance
     */
    Optional<EntityIriStatus> getOntologyTermIdStatus();

    /**
     * Get the key-value server configuration properties
     *
     * @return Server configuration properties (aside from policy, host, and term identifiers status)
     */
    Optional<Map<String,String>> getProperties();

}