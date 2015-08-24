package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.*;

import java.io.Serializable;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class AccessControlServerConfiguration implements ServerConfiguration, Serializable {
    private static final long serialVersionUID = -2167646552408654273L;
    private final Host host;
    private final Policy policy;
    private final OntologyTermIdStatus ontologyTermIdStatus;

    /**
     * Package-private constructor; use builder
     *
     * @param host    Host
     * @param policy    Access control policy
     * @param ontologyTermIdStatus  Ontology term identifier status
     */
    AccessControlServerConfiguration(Host host, Policy policy, Optional<OntologyTermIdStatus> ontologyTermIdStatus) {
        this.host = checkNotNull(host);
        this.policy = checkNotNull(policy);
        this.ontologyTermIdStatus = (ontologyTermIdStatus.isPresent() ? checkNotNull(ontologyTermIdStatus.get()) : null);
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
    public OntologyTermIdStatus getOntologyTermIdStatus() {
        return ontologyTermIdStatus;
    }


    /**
     * Access control server configuration builder
     */
    public static class Builder {
        private Host host;
        private Policy policy;
        private OntologyTermIdStatus ontologyTermIdStatus;

        public Builder setHost(Host host) {
            this.host = host;
            return this;
        }

        public Builder setPolicy(Policy policy) {
            this.policy = policy;
            return this;
        }

        public Builder setOntologyTermIdStatus(OntologyTermIdStatus ontologyTermIdStatus) {
            this.ontologyTermIdStatus = ontologyTermIdStatus;
            return this;
        }

        public AccessControlServerConfiguration createAccessControlServerConfiguration() {
            return new AccessControlServerConfiguration(host, policy, Optional.ofNullable(ontologyTermIdStatus));
        }
    }
}