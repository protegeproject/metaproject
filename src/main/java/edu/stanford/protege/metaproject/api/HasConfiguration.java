package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface HasConfiguration {

    /**
     * Get the server configuration
     *
     * @return Server configuration
     */
    @Nonnull
    ServerConfiguration getConfiguration();

}
