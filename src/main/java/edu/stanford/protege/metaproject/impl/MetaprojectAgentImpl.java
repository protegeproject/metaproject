package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.IdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.ProjectNotInPolicyException;
import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class MetaprojectAgentImpl implements MetaprojectAgent {
    private final static Logger logger = LoggerFactory.getLogger(MetaprojectAgent.class);
    private Policy policy;
    private RoleRegistry roleRegistry;
    private UserRegistry userRegistry;
    private OperationRegistry operationRegistry;
    private ProjectRegistry projectRegistry;

    /**
     * Constructor
     *
     * @param policy            Policy
     * @param roleRegistry      Role registry
     * @param operationRegistry Operation registry
     * @param userRegistry      User registry
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
            Set<RoleId> roles = new HashSet<>(policy.getRoles(userId, projectId));
            roles.addAll(policy.getRoles(userId, MetaprojectUtils.getUniversalProjectId()));
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
    public boolean isOperationAllowed(OperationId operationId, UserId userId) {
        try {
            Set<RoleId> roles = policy.getRoles(userId);
            for (RoleId role : roles) {
                Role r = roleRegistry.get(role);
                if (r.getOperations().contains(operationId)) {
                    return true;
                }
            }
        } catch (UnknownMetaprojectObjectIdException | UserNotInPolicyException e) {
            return false;
        }
        return false;
    }

    @Override
    public Set<Project> getProjects(UserId userId) throws UserNotInPolicyException {
        Set<Project> projects = new HashSet<>();
        Set<ProjectId> projectIds = policy.getProjects(userId);
        for (ProjectId projectId : projectIds) {
            try {
                projects.add(projectRegistry.get(projectId));
            } catch (UnknownMetaprojectObjectIdException e) {
                logger.debug("The project with identifier '" + projectId.get() + "' is stated in the access control policy " +
                        "but there is no project with that identifier in the project registry.");
            }
        }
        return projects;
    }

    @Override
    public Set<Role> getRoles(UserId userId) throws UserNotInPolicyException {
        Set<Role> roles = new HashSet<>();
        Set<RoleId> roleIds = policy.getRoles(userId);
        for (RoleId roleId : roleIds) {
            try {
                roles.add(roleRegistry.get(roleId));
            } catch (UnknownMetaprojectObjectIdException e) {
                logger.debug("The role with identifier '" + roleId.get() + "' is stated in the access control policy " +
                        "but there is no role with that identifier in the role registry.");
            }
        }
        return roles;
    }

    @Override
    public Set<Role> getRoles(UserId userId, ProjectId projectId) throws UserNotInPolicyException, ProjectNotInPolicyException {
        Set<Role> roles = new HashSet<>();
        Set<RoleId> roleIds = policy.getRoles(userId, projectId);
        for (RoleId roleId : roleIds) {
            try {
                roles.add(roleRegistry.get(roleId));
            } catch (UnknownMetaprojectObjectIdException e) {
                logger.debug("The role with identifier '" + roleId.get() + "' is stated in the access control policy " +
                        "but there is no role with that identifier in the role registry.");
            }
        }
        return roles;
    }

    @Override
    public Set<Operation> getOperations(UserId userId) throws UserNotInPolicyException {
        Set<Role> roles = getRoles(userId);
        return getOperations(roles);
    }

    @Override
    public Set<Operation> getOperations(UserId userId, ProjectId projectId) throws UserNotInPolicyException, ProjectNotInPolicyException {
        Set<Role> roles = getRoles(userId, projectId);
        return getOperations(roles);
    }

    @Override
    public Set<Operation> getOperations(Set<Role> roles) {
        Set<Operation> operations = new HashSet<>();
        for (Role role : roles) {
            operations.addAll(getOperations(role));
        }
        return operations;
    }

    @Override
    public Set<Operation> getOperations(Role role) {
        Set<Operation> operations = new HashSet<>();
        for (OperationId opId : role.getOperations()) {
            try {
                operations.add(operationRegistry.get(opId));
            } catch (UnknownMetaprojectObjectIdException e) {
                logger.debug("The operation with identifier '" + opId.get() + "' is stated in the operation list of the role '" +
                        role.getName() + "', but there is no operation with that identifier in the operation registry.");
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
        if (obj instanceof User) {
            performAction(userRegistry, (User) obj, action);
        } else if (obj instanceof Role) {
            performAction(roleRegistry, (Role) obj, action);
        } else if (obj instanceof Project) {
            performAction(projectRegistry, (Project) obj, action);
        } else if (obj instanceof Operation) {
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
}
