package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.ProjectNotInPolicyException;
import edu.stanford.protege.metaproject.api.exception.UnknownAccessControlObjectIdException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PolicyAgentImpl implements PolicyAgent {
    private Policy policy;
    private RoleRegistry roleRegistry;

    /**
     * Constructor
     *
     * @param policy    Policy
     * @param roleRegisty   Role registry
     */
    public PolicyAgentImpl(Policy policy, RoleRegistry roleRegisty) {
        this.policy = checkNotNull(policy);
        this.roleRegistry = checkNotNull(roleRegisty);
    }

    @Override
    public boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId) {
        try {
            Set<RoleId> roles = policy.getRoles(userId, projectId);
            for (RoleId role : roles) {
                Role r = roleRegistry.getRole(role);
                if (r.getOperations().contains(operationId)) {
                    return true;
                }
            }
        } catch (UnknownAccessControlObjectIdException | UserNotInPolicyException | ProjectNotInPolicyException e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolicyAgentImpl that = (PolicyAgentImpl) o;
        return Objects.equal(policy, that.policy) &&
                Objects.equal(roleRegistry, that.roleRegistry);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(policy, roleRegistry);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("policy", policy)
                .add("roleRegistry", roleRegistry)
                .toString();
    }
}
