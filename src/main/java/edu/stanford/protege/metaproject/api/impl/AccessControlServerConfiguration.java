package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.*;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class AccessControlServerConfiguration implements ServerConfiguration, Serializable {
    private static final long serialVersionUID = 370532534982206740L;
    private final Host host;
    private final Policy policy;
    private final OntologyTermIdGenerator ontologyTermIdGenerator;
    private final AccessControlObjectIdGenerator accessControlObjectIdGenerator;

    /**
     * Package-private constructor; use builder
     *
     * @param host    Host
     * @param ontologyTermIdGenerator
     * @param accessControlObjectIdGenerator
     */
    AccessControlServerConfiguration(Host host, Policy policy, OntologyTermIdGenerator ontologyTermIdGenerator, AccessControlObjectIdGenerator accessControlObjectIdGenerator) {
        this.host = checkNotNull(host);
        this.policy = checkNotNull(policy);
        this.ontologyTermIdGenerator = checkNotNull(ontologyTermIdGenerator);
        this.accessControlObjectIdGenerator = checkNotNull(accessControlObjectIdGenerator);
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
    public OntologyTermIdGenerator getOntologyTermIdGenerator() {
        return ontologyTermIdGenerator;
    }

    @Override
    public AccessControlObjectIdGenerator getAccessControlObjectIdGenerator() {
        return accessControlObjectIdGenerator;
    }


    /**
     * @author Rafael Gonçalves <br>
     * Stanford Center for Biomedical Informatics Research
     */
    public static class AccessControlServerConfigurationBuilder {
        private Host host;
        private Policy policy;
        private OntologyTermIdGenerator ontologyTermIdGenerator;
        private AccessControlObjectIdGenerator accessControlObjectIdGenerator;

        public AccessControlServerConfigurationBuilder setHost(Host host) {
            this.host = host;
            return this;
        }

        public AccessControlServerConfigurationBuilder setPolicy(Policy policy) {
            this.policy = policy;
            return this;
        }

        public AccessControlServerConfigurationBuilder setOntologyTermIdGenerator(OntologyTermIdGenerator ontologyTermIdGenerator) {
            this.ontologyTermIdGenerator = ontologyTermIdGenerator;
            return this;
        }

        public AccessControlServerConfigurationBuilder setAccessControlObjectIdGenerator(AccessControlObjectIdGenerator accessControlObjectIdGenerator) {
            this.accessControlObjectIdGenerator = accessControlObjectIdGenerator;
            return this;
        }

        public AccessControlServerConfiguration createAccessControlServerConfiguration() {
            return new AccessControlServerConfiguration(host, policy, ontologyTermIdGenerator, accessControlObjectIdGenerator);
        }
    }
}