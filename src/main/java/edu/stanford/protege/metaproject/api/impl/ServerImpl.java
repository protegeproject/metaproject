package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.Server;
import edu.stanford.protege.metaproject.api.ServerConfiguration;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ServerImpl implements Server {
    private final ServerConfiguration configuration;

    /**
     * Constructor
     *
     * @param configuration Server configuration
     */
    public ServerImpl(ServerConfiguration configuration) {
        this.configuration = checkNotNull(configuration);
    }

    @Override
    public ServerConfiguration getConfiguration() {
        return configuration;
    }
}
