package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.ProjectNotInPolicyException;
import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PolicyImpl implements Policy, Serializable {
    private static final long serialVersionUID = -9001259511982474948L;
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
    public void remove(UserId userId, ProjectId projectId) {
        Map<ProjectId,Set<RoleId>> roleAssignments = userRoleMap.get(userId);
        roleAssignments.remove(projectId);
        userRoleMap.put(userId, roleAssignments);
    }

    @Override
    public <E extends MetaprojectObjectId> void remove(E obj) {
        if(obj instanceof UserId) {
            remove((UserId)obj);
        }
        else if(obj instanceof RoleId) {
            remove((RoleId)obj);
        }
        else if(obj instanceof ProjectId) {
            remove((ProjectId)obj);
        }
        else {
            throw new IllegalArgumentException("Programmer error: Expected a user, role or project identifier");
        }
    }

    /**
     * Remove all policy entries for the user with the given identifier
     *
     * @param userId User identifier
     */
    private void remove(UserId userId) {
        userRoleMap.remove(userId);
    }

    /**
     * Remove all users' role assignments involving the role with the specified identifier
     *
     * @param roleId    Role identifier
     */
    private void remove(RoleId roleId) {
        Map<UserId, Map<ProjectId,Set<RoleId>>> toUpdate = new HashMap<>();
        for(UserId userId : userRoleMap.keySet()) {
            Map<ProjectId,Set<RoleId>> roleAssignments = new HashMap<>();
            for(ProjectId projectId : userRoleMap.get(userId).keySet()) {
                if(roleAssignments.get(projectId).contains(roleId)) {
                    Set<RoleId> roleIds = roleAssignments.get(projectId);
                    roleIds.remove(roleId);
                    roleAssignments.put(projectId, roleIds);
                }
            }
            toUpdate.put(userId, roleAssignments);
        }
        userRoleMap.putAll(toUpdate);
    }

    /**
     * Remove all users' role assignments to the project with the given identifier
     *
     * @param projectId Project identifier
     */
    private void remove(ProjectId projectId) {
        Map<UserId, Map<ProjectId,Set<RoleId>>> toUpdate = new HashMap<>();
        for(UserId userId : userRoleMap.keySet()) {
            Map<ProjectId, Set<RoleId>> roleAssignments = new HashMap<>();
            roleAssignments.remove(projectId);
            toUpdate.put(userId, roleAssignments);
        }
        userRoleMap.putAll(toUpdate);
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
    public Set<RoleId> getRoles(UserId userId, ProjectId projectId) throws UserNotInPolicyException, ProjectNotInPolicyException {
        checkUserIsInPolicy(userId);
        checkProjectIsInPolicy(userId, projectId);
        return userRoleMap.get(userId).get(projectId);
    }

    @Override
    public Set<RoleId> getRoles(UserId userId) throws UserNotInPolicyException {
        checkUserIsInPolicy(userId);
        Map<ProjectId,Set<RoleId>> map = userRoleMap.get(userId);
        Set<RoleId> roles = new HashSet<>();
        for(ProjectId p : map.keySet()) {
            roles.addAll(map.get(p));
        }
        return roles;
    }

    @Override
    public Set<ProjectId> getProjects(UserId userId) throws UserNotInPolicyException {
        checkUserIsInPolicy(userId);
        return userRoleMap.get(userId).keySet();
    }

    @Override
    public boolean hasRole(UserId userId, ProjectId projectId) {
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

    @Override
    public Set<UserId> getUsers(ProjectId projectId) throws UnknownMetaprojectObjectIdException {
        return getPolicyMappings().keySet().stream().filter(userId ->
                userRoleMap.get(userId).keySet().contains(projectId)).collect(Collectors.toSet());
    }

    /**
     * Verify whether given user identifier(s) are registered in the access control policy, i.e., in the user-role map
     *
     * @param users One or more user identifiers
     * @throws UserNotInPolicyException UserId not registered in the access control policy
     */
    private void checkUserIsInPolicy(UserId... users) throws UserNotInPolicyException {
        for (UserId user : users) {
            if (!hasRole(user)) {
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
    public boolean hasRole(UserId id) {
        for (UserId userId : userRoleMap.keySet()) {
            if (userId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PolicyImpl)) {
            return false;
        }
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
