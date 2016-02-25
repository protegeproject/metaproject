package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class MetaprojectImpl implements Metaproject, Serializable {
    private static final long serialVersionUID = 6530049898256408956L;
    private Policy policy;
    private RoleRegistry roleRegistry;
    private OperationRegistry operationRegistry;
    private UserRegistry userRegistry;
    private ProjectRegistry projectRegistry;

    /**
     * Package-private constructor; use builder
     *
     * @param policy Policy manager
     */
    MetaprojectImpl(Policy policy, RoleRegistry roleRegistry, OperationRegistry operationRegistry, UserRegistry userRegistry, ProjectRegistry projectRegistry) {
        this.policy = checkNotNull(policy);
        this.roleRegistry = checkNotNull(roleRegistry);
        this.operationRegistry = checkNotNull(operationRegistry);
        this.userRegistry = checkNotNull(userRegistry);
        this.projectRegistry = checkNotNull(projectRegistry);
    }

    public Policy getPolicy() {
        return policy;
    }

    public RoleRegistry getRoleRegistry() {
        return roleRegistry;
    }

    public OperationRegistry getOperationRegistry() {
        return operationRegistry;
    }

    public UserRegistry getUserRegistry() {
        return userRegistry;
    }

    public ProjectRegistry getProjectRegistry() {
        return projectRegistry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetaprojectImpl that = (MetaprojectImpl) o;
        return Objects.equal(policy, that.policy) &&
                Objects.equal(roleRegistry, that.roleRegistry) &&
                Objects.equal(operationRegistry, that.operationRegistry) &&
                Objects.equal(userRegistry, that.userRegistry) &&
                Objects.equal(projectRegistry, that.projectRegistry);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(policy, roleRegistry, operationRegistry, userRegistry, projectRegistry);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("policy", policy)
                .add("roleRegistry", roleRegistry)
                .add("operationRegistry", operationRegistry)
                .add("userRegistry", userRegistry)
                .add("projectRegistry", projectRegistry)
                .toString();
    }

    /**
     * Builder
     */
    public static class Builder {
        private Policy policy = new PolicyImpl();
        private RoleRegistry roleRegistry = new RoleRegistryImpl();
        private OperationRegistry operationRegistry = new OperationRegistryImpl();
        private UserRegistry userRegistry = new UserRegistryImpl();
        private ProjectRegistry projectRegistry = new ProjectRegistryImpl();

        public Builder setPolicy(Policy policy) {
            this.policy = policy;
            return this;
        }

        public Builder setRoleRegistry(RoleRegistry roleRegistry) {
            this.roleRegistry = roleRegistry;
            return this;
        }

        public Builder setOperationRegistry(OperationRegistry operationRegistry) {
            this.operationRegistry = operationRegistry;
            return this;
        }

        public Builder setUserRegistry(UserRegistry userRegistry) {
            this.userRegistry = userRegistry;
            return this;
        }

        public Builder setProjectRegistry(ProjectRegistry projectRegistry) {
            this.projectRegistry = projectRegistry;
            return this;
        }

        public Metaproject createMetaproject() {
            return new MetaprojectImpl(policy, roleRegistry, operationRegistry, userRegistry, projectRegistry);
        }
    }
}
