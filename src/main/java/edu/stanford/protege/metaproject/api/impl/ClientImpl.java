package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.Client;
import edu.stanford.protege.metaproject.api.ClientConfiguration;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ClientImpl implements Client {
    private final ClientConfiguration configuration;

    /**
     * Constructor
     *
     * @param configuration Client configuration
     */
    public ClientImpl(ClientConfiguration configuration) {
        this.configuration = checkNotNull(configuration);
    }

    @Override
    public ClientConfiguration getConfiguration() {
        return configuration;
    }
}
