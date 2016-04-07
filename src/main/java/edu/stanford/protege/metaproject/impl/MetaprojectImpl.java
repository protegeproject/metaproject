package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class MetaprojectImpl implements Metaproject, Serializable {
    private static final long serialVersionUID = -7167580667424252242L;
    private Policy policy;
    private RoleRegistry roleRegistry;
    private OperationRegistry operationRegistry;
    private UserRegistry userRegistry;
    private ProjectRegistry projectRegistry;

    /**
     * Package-private constructor; use {@link MetaprojectBuilder}
     *
     * @param policy    Policy
     * @param roleRegistry  Role registry
     * @param operationRegistry Operation registry
     * @param userRegistry  User registry
     * @param projectRegistry   Project registry
     */
    MetaprojectImpl(Policy policy, RoleRegistry roleRegistry, OperationRegistry operationRegistry, UserRegistry
            userRegistry, ProjectRegistry projectRegistry) {
        this.policy = checkNotNull(policy);
        this.roleRegistry = checkNotNull(roleRegistry);
        this.operationRegistry = checkNotNull(operationRegistry);
        this.userRegistry = checkNotNull(userRegistry);
        this.projectRegistry = checkNotNull(projectRegistry);
    }

    @Override
    public Policy getPolicy() {
        return policy;
    }

    @Override
    public PolicyChecker getPolicyAgent() {
        return Manager.getFactory().getPolicyAgent(policy, roleRegistry);
    }

    @Override
    public RoleRegistry getRoleRegistry() {
        return roleRegistry;
    }

    @Override
    public OperationRegistry getOperationRegistry() {
        return operationRegistry;
    }

    @Override
    public UserRegistry getUserRegistry() {
        return userRegistry;
    }

    @Override
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
}
