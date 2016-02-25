package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Metaproject;
import edu.stanford.protege.metaproject.api.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerImpl implements Server {
    private ServerConfiguration configuration;
    private EntityIriGenerator idGenerator;

    /**
     * Constructor
     *
     * @param configuration Server configuration
     * @param idGenerator   Ontology term identifier generator
     */
    public ServerImpl(ServerConfiguration configuration, EntityIriGenerator idGenerator) {
        this.configuration = checkNotNull(configuration);
        this.idGenerator = checkNotNull(idGenerator);
    }

    @Override
    public ServerConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public EntityIriGenerator getEntityIriGenerator() {
        return idGenerator;
    }

    @Override
    public void updateConfiguration(Metaproject metaproject) {
        this.configuration = new ServerConfigurationImpl.Builder()
                .setHost(configuration.getHost())
                .setMetaproject(checkNotNull(metaproject))
                .setAuthenticationManager(configuration.getAuthenticationManager())
                .setEntityIriStatus(configuration.getOntologyTermIdStatus().get())
                .createServerConfiguration();
    }

    @Override
    public void updateConfiguration(EntityIriStatus entityIriStatus) {
        this.configuration = new ServerConfigurationImpl.Builder()
                .setHost(configuration.getHost())
                .setMetaproject(configuration.getMetaproject())
                .setAuthenticationManager(configuration.getAuthenticationManager())
                .setEntityIriStatus(checkNotNull(entityIriStatus))
                .createServerConfiguration();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerImpl server = (ServerImpl) o;
        return Objects.equal(configuration, server.configuration) &&
                Objects.equal(idGenerator, server.idGenerator);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(configuration, idGenerator);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("configuration", configuration)
                .add("idGenerator", idGenerator)
                .toString();
    }
}
