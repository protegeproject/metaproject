package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientImpl client = (ClientImpl) o;
        return Objects.equal(configuration, client.configuration);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(configuration);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("configuration", configuration)
                .toString();
    }
}
