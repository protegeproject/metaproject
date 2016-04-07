package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.Metaproject;

import java.util.HashMap;
import java.util.Map;

public class ClientConfigurationBuilder {
    private Metaproject metaproject = new MetaprojectBuilder().createMetaproject();
    private int synchronisationDelay = 300; // initialize with 5-minutes delay
    private Map<String, String> properties = new HashMap<>();

    /**
     * Set the metaproject definition for this client
     *
     * @param metaproject   Metaproject
     * @return ClientConfigurationBuilder
     */
    public ClientConfigurationBuilder setMetaproject(Metaproject metaproject) {
        this.metaproject = metaproject;
        return this;
    }

    /**
     * Set the delay (in seconds) for the client to synchronise with the server
     *
     * @param synchronisationDelay  Delay in seconds
     * @return ClientConfigurationBuilder
     */
    public ClientConfigurationBuilder setSynchronisationDelay(int synchronisationDelay) {
        this.synchronisationDelay = synchronisationDelay;
        return this;
    }

    /**
     * Set the map of custom properties for the client
     *
     * @param properties    Map of string properties
     * @return ClientConfigurationBuilder
     */
    public ClientConfigurationBuilder setProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    /**
     * Create a new instance of a client configuration
     *
     * @return ClientConfiguration
     */
    public ClientConfigurationImpl createClientConfiguration() {
        return new ClientConfigurationImpl(metaproject, synchronisationDelay, properties);
    }
}