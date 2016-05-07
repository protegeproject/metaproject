package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.IdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.ProjectNotInPolicyException;
import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class MetaprojectAgentImpl implements MetaprojectAgent {
    private Policy policy;
    private RoleRegistry roleRegistry;
    private UserRegistry userRegistry;
    private OperationRegistry operationRegistry;
    private ProjectRegistry projectRegistry;

    /**
     * Constructor
     *
     * @param policy    Policy
     * @param roleRegistry  Role registry
     * @param operationRegistry Operation registry
     * @param userRegistry  User registry
     * @param projectRegistry   Project registry
     */
    public MetaprojectAgentImpl(Policy policy, RoleRegistry roleRegistry, OperationRegistry operationRegistry, UserRegistry
            userRegistry, ProjectRegistry projectRegistry) {
        this.policy = checkNotNull(policy);
        this.roleRegistry = checkNotNull(roleRegistry);
        this.operationRegistry = checkNotNull(operationRegistry);
        this.userRegistry = checkNotNull(userRegistry);
        this.projectRegistry = checkNotNull(projectRegistry);
    }

    @Override
    public boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId) {
        try {
            Set<RoleId> roles = policy.getRoles(userId, projectId);
            for (RoleId role : roles) {
                Role r = roleRegistry.get(role);
                if (r.getOperations().contains(operationId)) {
                    return true;
                }
            }
        } catch (UnknownMetaprojectObjectIdException | UserNotInPolicyException | ProjectNotInPolicyException e) {
            return false;
        }
        return false;
    }

    @Override
    public Set<Project> getProjects(UserId userId) {
        Set<Project> projects = new HashSet<>();
        try {
            Set<ProjectId> projectIds = policy.getProjects(userId);
            for (ProjectId projectId : projectIds) {
                projects.add(projectRegistry.get(projectId));
            }
        } catch (UserNotInPolicyException | UnknownMetaprojectObjectIdException e) { /* no-op */ }
        return projects;
    }

    @Override
    public Set<Role> getRoles(UserId userId) {
        Set<Role> roles = new HashSet<>();
        try {
            Set<RoleId> roleIds = policy.getRoles(userId);
            for (RoleId roleId : roleIds) {
                roles.add(roleRegistry.get(roleId));
            }
        } catch (UserNotInPolicyException | UnknownMetaprojectObjectIdException e) { /* no-op */ }
        return roles;
    }

    @Override
    public Set<Role> getRoles(UserId userId, ProjectId projectId) {
        Set<Role> roles = new HashSet<>();
        try {
            Set<RoleId> roleIds = policy.getRoles(userId, projectId);
            for (RoleId roleId : roleIds) {
                roles.add(roleRegistry.get(roleId));
            }
        } catch (UserNotInPolicyException | ProjectNotInPolicyException | UnknownMetaprojectObjectIdException e) { /* no-op */ }
        return roles;
    }

    @Override
    public Set<Operation> getOperations(UserId userId) {
        Set<Role> roles = getRoles(userId);
        return getOperations(roles);
    }

    @Override
    public Set<Operation> getOperations(UserId userId, ProjectId projectId) {
        Set<Role> roles = getRoles(userId, projectId);
        return getOperations(roles);
    }

    private Set<Operation> getOperations(Set<Role> roles) {
        Set<Operation> operations = new HashSet<>();
        for(Role r : roles) {
            for(OperationId opId : r.getOperations()) {
                try {
                    operations.add(operationRegistry.get(opId));
                } catch (UnknownMetaprojectObjectIdException e) { /* no-op */ }
            }
        }
        return operations;
    }

    @Override
    public <T extends MetaprojectObject> void add(T obj) throws IdAlreadyInUseException {
        performAction(obj, Action.ADD);
    }

    @Override
    public <T extends MetaprojectObject> void remove(T obj) {
        try {
            performAction(obj, Action.REMOVE);
        } catch (IdAlreadyInUseException e) { /* no-op */ }
    }

    private <T extends MetaprojectObject> void performAction(T obj, Action action) throws IdAlreadyInUseException {
        if(obj instanceof User) {
            performAction(userRegistry, (User) obj, action);
        }
        else if(obj instanceof Role) {
            performAction(roleRegistry, (Role) obj, action);
        }
        else if(obj instanceof Project) {
            performAction(projectRegistry, (Project) obj, action);
        }
        else if(obj instanceof Operation) {
            performAction(operationRegistry, (Operation) obj, action);
        }
    }

    private <T extends MetaprojectObject<?>> void performAction(Registry<T> registry, T obj, Action action) throws IdAlreadyInUseException {
        if (action.equals(Action.REMOVE)) {
            registry.remove(obj);
            if (!(obj instanceof Operation)) {
                policy.remove(obj.getId());
            }
        } else if (action.equals(Action.ADD)) {
            registry.add(obj);
        }
    }

    private enum Action {
        ADD, REMOVE
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaprojectAgentImpl)) {
            return false;
        }
        MetaprojectAgentImpl that = (MetaprojectAgentImpl) o;
        return Objects.equal(policy, that.policy) &&
                Objects.equal(roleRegistry, that.roleRegistry) &&
                Objects.equal(userRegistry, that.userRegistry) &&
                Objects.equal(operationRegistry, that.operationRegistry) &&
                Objects.equal(projectRegistry, that.projectRegistry);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(policy, roleRegistry, userRegistry, operationRegistry, projectRegistry);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("policy", policy)
                .add("roleRegistry", roleRegistry)
                .add("userRegistry", userRegistry)
                .add("operationRegistry", operationRegistry)
                .add("projectRegistry", projectRegistry)
                .toString();
    }
}
