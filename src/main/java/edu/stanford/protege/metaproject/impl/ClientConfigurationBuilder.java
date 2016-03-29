package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.GuiRestriction;
import edu.stanford.protege.metaproject.api.Metaproject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClientConfigurationBuilder {
    private Metaproject metaproject = new MetaprojectBuilder().createMetaproject();
    private int synchronisationDelay = 300; // initialize with 5-minutes delay
    private Set<GuiRestriction> guiRestrictions = new HashSet<>();
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
     * Set the set of GUI restrictions in effect for this client
     *
     * @param guiRestrictions   Set of GUI restrictions
     * @return ClientConfigurationBuilder
     */
    public ClientConfigurationBuilder setGuiRestrictions(Set<GuiRestriction> guiRestrictions) {
        this.guiRestrictions = guiRestrictions;
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
        return new ClientConfigurationImpl(metaproject, synchronisationDelay, guiRestrictions, properties);
    }
}