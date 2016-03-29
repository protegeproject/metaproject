package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UserIdAlreadyInUseException;

import java.util.Map;
import java.util.Set;

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
     * @throws UserIdAlreadyInUseException  Guest user identifier is already taken by another user
     */
    void enableGuestUser(boolean enableGuestUser) throws UserIdAlreadyInUseException;

    /**
     * Get the map of user identifiers to their corresponding sets of GUI restrictions
     *
     * @return Map of user identifiers to sets of GUI restrictions
     */
    Map<UserId,Set<GuiRestriction>> getUserGuiRestrictions();

}