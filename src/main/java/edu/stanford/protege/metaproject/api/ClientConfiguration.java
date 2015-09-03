package edu.stanford.protege.metaproject.api;

import javax.swing.*;
import java.util.Map;
import java.util.Set;

/**
 * A representation of a client configuration, consisting of the value for the client-server synchronisation delay,
 * the set of UI components that are disabled for this client
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ClientConfiguration extends Configuration {

    /**
     * Get the access control policy in effect for the client
     *
     * @return Access control policy
     */
    Policy getPolicy();

    /**
     * Get the time (in seconds) between client-server synchronisation attempts
     *
     * @return Synchronisation delay in seconds
     */
    int getSynchronisationDelay();

    /**
     * Get the set of swing components that are disabled
     *
     * @return Set of java swing components
     */
    Set<? extends JComponent> getDisabledUIElements();

    /**
     * Get the map of client configuration properties
     *
     * @return Map of string properties
     */
    Map<String,String> getProperties();

}
