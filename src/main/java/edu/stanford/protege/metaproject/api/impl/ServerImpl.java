package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerImpl implements Server {
    private ServerConfiguration configuration;
    private OntologyTermIdGenerator idGenerator;

    /**
     * Constructor
     *
     * @param configuration Server configuration
     * @param idGenerator   Ontology term identifier generator
     */
    public ServerImpl(ServerConfiguration configuration, OntologyTermIdGenerator idGenerator) {
        this.configuration = checkNotNull(configuration);
        this.idGenerator = checkNotNull(idGenerator);
    }

    @Override
    public ServerConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public OntologyTermIdGenerator getOntologyTermIdGenerator() {
        return idGenerator;
    }

    @Override
    public void updateConfiguration(Policy policy) {
        this.configuration = new ServerConfigurationImpl.Builder()
                .setHost(configuration.getHost())
                .setPolicy(checkNotNull(policy))
                .setOntologyTermIdStatus(configuration.getOntologyTermIdStatus())
                .createServerConfiguration();
    }

    @Override
    public void updateConfiguration(OntologyTermIdStatus ontologyTermIdStatus) {
        this.configuration = new ServerConfigurationImpl.Builder()
                .setHost(configuration.getHost())
                .setPolicy(configuration.getPolicy())
                .setOntologyTermIdStatus(checkNotNull(ontologyTermIdStatus))
                .createServerConfiguration();
    }
}
