package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.*;

import java.io.Serializable;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PolicyImpl implements Policy, Serializable {
    private static final long serialVersionUID = -8143188193792068841L;
    private Map<UserId, Map<ProjectId, Set<RoleId>>> userRoleMap = new HashMap<>();

    /**
     * Constructor
     *
     * @param userRoleMap Map of user identifier to map of project to role identifiers
     *                    associated with the user
     */
    public PolicyImpl(Map<UserId, Map<ProjectId, Set<RoleId>>> userRoleMap) {
        this.userRoleMap = checkNotNull(userRoleMap);
    }

    /**
     * No-arguments constructor
     */
    public PolicyImpl() { }

    @Override
    public void add(UserId userId, ProjectId projectId, RoleId... roleIds) {
        if (userRoleMap.containsKey(userId)) {
            // get {project -> roles} assignments for this user
            Map<ProjectId, Set<RoleId>> projectRoleMap = userRoleMap.get(userId);
            if (projectRoleMap.containsKey(projectId)) {
                Set<RoleId> roles = projectRoleMap.get(projectId);
                Collections.addAll(roles, roleIds);
                projectRoleMap.put(projectId, roles);
            } else {
                Set<RoleId> roles = new HashSet<>();
                Collections.addAll(roles, roleIds);
                projectRoleMap.put(projectId, roles);
            }
            userRoleMap.put(userId, projectRoleMap);
        } else {
            Set<RoleId> roles = new HashSet<>();
            Collections.addAll(roles, roleIds);
            Map<ProjectId, Set<RoleId>> map = new HashMap<>();
            map.put(projectId, roles);
            userRoleMap.put(userId, map);
        }
    }

    @Override
    public void add(RoleId roleId, ProjectId projectId, UserId... userIds) {
        for (UserId userId : userIds) {
            add(userId, projectId, roleId);
        }
    }

    @Override
    public void remove(UserId userId, ProjectId projectId, RoleId roleId) {
        Map<ProjectId, Set<RoleId>> map = userRoleMap.get(userId);
        Set<RoleId> roles = map.get(projectId);
        roles.remove(roleId);
        if (roles.isEmpty()) {
            map.remove(projectId);
            if(map.isEmpty()) {
                userRoleMap.remove(userId);
            }
        } else {
            map.put(projectId, roles);
        }
        userRoleMap.put(userId, map);
    }

    @Override
    public boolean hasRole(UserId userId, ProjectId projectId, RoleId roleId) {
        if (userRoleMap.containsKey(userId) && userRoleMap.get(userId).containsKey(projectId)) {
            if (userRoleMap.get(userId).get(projectId).contains(roleId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<RoleId> getRoleIds(UserId userId, ProjectId projectId) throws UserNotInPolicyException, ProjectNotInPolicyException {
        checkUserIsInPolicy(userId);
        checkProjectIsInPolicy(userId, projectId);
        return userRoleMap.get(userId).get(projectId);
    }

    @Override
    public Set<ProjectId> getProjectIds(UserId userId) throws UserNotInPolicyException {
        checkUserIsInPolicy(userId);
        return userRoleMap.get(userId).keySet();
    }

    @Override
    public boolean hasRoleAssignments(UserId userId, ProjectId projectId) {
        for (UserId user : userRoleMap.keySet()) {
            if (user.equals(userId)) {
                for (ProjectId project : userRoleMap.get(userId).keySet()) {
                    if (project.equals(projectId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Map<UserId, Map<ProjectId, Set<RoleId>>> getPolicyMappings() {
        return userRoleMap;
    }

    @Override
    public Map<ProjectId, Set<RoleId>> getUserRoleMap(UserId userId) {
        return userRoleMap.get(userId);
    }

    private Optional<Metaproject> fetchMetaproject() {
        try {
            return Optional.of(Manager.getMetaproject());
        } catch (MetaprojectNotLoadedException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId)
            throws UnknownAccessControlObjectIdException, UserNotInPolicyException, ProjectNotInPolicyException {
        if (fetchMetaproject().isPresent()) {
            Manager.checkExistence(fetchMetaproject().get().getOperationRegistry(), operationId);
            Manager.checkExistence(fetchMetaproject().get().getProjectRegistry(), projectId);
            Set<RoleId> roles = getRoleIds(userId, projectId);
            for (RoleId role : roles) {
                RoleRegistry roleRegistry = fetchMetaproject().get().getRoleRegistry();
                Role r = roleRegistry.getRole(role);
                if (r.getOperations().contains(operationId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<Operation> getOperationsInProject(UserId userId, ProjectId projectId) throws UserNotInPolicyException, ProjectNotInPolicyException {
        Set<Operation> operations = new HashSet<>();
        if(fetchMetaproject().isPresent()) {
            OperationRegistry operationRegistry = fetchMetaproject().get().getOperationRegistry();
            Set<Role> roles = getRoles(userId, projectId);
            for (Role role : roles) {
                for (OperationId operationId : role.getOperations()) {
                    try {
                        operations.add(operationRegistry.getOperation(operationId));
                    } catch (UnknownOperationIdException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return operations;
    }

    @Override
    public Set<Project> getProjects(UserId userId) throws UserNotInPolicyException {
        Set<Project> projects = new HashSet<>();
        if(fetchMetaproject().isPresent()) {
            Set<ProjectId> projectIds = getProjectIds(userId);
            for (ProjectId p : projectIds) {
                try {
                    projects.add(fetchMetaproject().get().getProjectRegistry().getProject(p));
                } catch (UnknownProjectIdException e) {
                    e.printStackTrace();
                }
            }
        }
        return projects;
    }

    @Override
    public Set<Role> getRoles(UserId userId, ProjectId projectId) throws UserNotInPolicyException, ProjectNotInPolicyException {
        Set<Role> roles = new HashSet<>();
        if(fetchMetaproject().isPresent()) {
            RoleRegistry roleRegistry = fetchMetaproject().get().getRoleRegistry();
            for (RoleId roleId : getRoleIds(userId, projectId)) {
                try {
                    roles.add(roleRegistry.getRole(roleId));
                } catch (UnknownRoleIdException e) {
                    e.printStackTrace();
                }
            }
        }
        return roles;
    }

    @Override
    public Set<User> getUsers(ProjectId projectId) throws UnknownAccessControlObjectIdException {
        Set<User> users = new HashSet<>();
        if(fetchMetaproject().isPresent()) {
            Manager.checkExistence(fetchMetaproject().get().getProjectRegistry(), projectId);
            for (UserId userId : getPolicyMappings().keySet()) {
                try {
                    if (getProjectIds(userId).contains(projectId)) {
                        try {
                            users.add(fetchMetaproject().get().getUserRegistry().getUser(userId));
                        } catch (UnknownUserIdException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (UserNotInPolicyException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    /**
     * Verify whether given user identifier(s) are registered in the access control policy, i.e., in the user-role map
     *
     * @param users One or more user identifiers
     * @throws UserNotInPolicyException UserId not registered in the access control policy
     */
    private void checkUserIsInPolicy(UserId... users) throws UserNotInPolicyException {
        for (UserId user : users) {
            if (!contains(user)) {
                throw new UserNotInPolicyException("The specified user (id: " + user.get() + ") is not registered in the access control policy");
            }
        }
    }

    /**
     * Verify whether given project identifier(s) are registered in the access control policy for the specified user
     *
     * @param userId User identifier
     * @throws ProjectNotInPolicyException Project not registered in the access control policy
     */
    private void checkProjectIsInPolicy(UserId userId, ProjectId... projects) throws ProjectNotInPolicyException {
        Map<ProjectId, Set<RoleId>> roles = userRoleMap.get(userId);
        for (ProjectId project : projects) {
            if (!roles.containsKey(project)) {
                throw new ProjectNotInPolicyException();
            }
        }
    }

    @Override
    public boolean contains(AccessControlObjectId id) {
        for (UserId userId : userRoleMap.keySet()) {
            if (userId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolicyImpl that = (PolicyImpl) o;
        return Objects.equal(userRoleMap, that.userRoleMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userRoleMap);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userRoleMap", userRoleMap)
                .toString();
    }
}
