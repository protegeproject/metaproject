package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.OntologyTermIdStatus;
import edu.stanford.protege.metaproject.api.Policy;
import edu.stanford.protege.metaproject.api.ServerConfiguration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ServerConfigurationImpl implements ServerConfiguration, Serializable {
    private static final long serialVersionUID = 8788413427327071798L;
    private final Host host;
    private final Policy policy;
    private final Map<String,String> properties;
    private final OntologyTermIdStatus termIdentifiers;

    /**
     * Package-private constructor; use builder
     *
     * @param host    Host
     * @param policy    Access control policy
     * @param properties   Map of simple configuration properties
     * @param termIdentifiers  Ontology term identifier status
     */
    ServerConfigurationImpl(Host host, Policy policy, Map<String,String> properties, Optional<OntologyTermIdStatus> termIdentifiers) {
        this.host = checkNotNull(host);
        this.policy = checkNotNull(policy);
        this.properties = checkNotNull(properties);
        this.termIdentifiers = (termIdentifiers.isPresent() ? checkNotNull(termIdentifiers.get()) : null);
    }

    @Override
    public Host getHost() {
        return host;
    }

    @Override
    public Policy getPolicy() {
        return policy;
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public OntologyTermIdStatus getOntologyTermIdStatus() {
        return termIdentifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerConfigurationImpl that = (ServerConfigurationImpl) o;
        return Objects.equal(host, that.host) &&
                Objects.equal(policy, that.policy) &&
                Objects.equal(properties, that.properties) &&
                Objects.equal(termIdentifiers, that.termIdentifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(host, policy, properties, termIdentifiers);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("host", host)
                .add("policy", policy)
                .add("properties", properties)
                .add("termIdentifiers", termIdentifiers)
                .toString();
    }

    /**
     * Access control server configuration builder
     */
    public static class Builder {
        private Host host;
        private Policy policy;
        private Map<String,String> properties = new HashMap<>();
        private OntologyTermIdStatus ontologyTermIdStatus = new OntologyTermIdStatusImpl.Builder().createOntologyTermIdStatus();

        public Builder setHost(Host host) {
            this.host = host;
            return this;
        }

        public Builder setPolicy(Policy policy) {
            this.policy = policy;
            return this;
        }

        public Builder setPropertyMap(Map<String,String> propertyMap) {
            this.properties = propertyMap;
            return this;
        }

        public Builder setOntologyTermIdStatus(OntologyTermIdStatus ontologyTermIdStatus) {
            this.ontologyTermIdStatus = ontologyTermIdStatus;
            return this;
        }

        public ServerConfigurationImpl createServerConfiguration() {
            return new ServerConfigurationImpl(host, policy, properties, Optional.ofNullable(ontologyTermIdStatus));
        }
    }
}