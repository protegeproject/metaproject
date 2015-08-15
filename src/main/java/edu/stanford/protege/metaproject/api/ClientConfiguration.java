package edu.stanford.protege.metaproject.api;

import javax.swing.*;
import java.util.Set;

/**
 * Manager for client-side configurations
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ClientConfiguration extends Configuration {

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

}
