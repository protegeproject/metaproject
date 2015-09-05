package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.PolicyManager;
import edu.stanford.protege.metaproject.api.RoleId;
import edu.stanford.protege.metaproject.api.UserId;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;

import java.io.Serializable;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PolicyManagerImpl implements PolicyManager, Serializable {
    private static final long serialVersionUID = -8363249880496153539L;
    private Map<UserId, Set<RoleId>> userRoleMap = new HashMap<>();

    /**
     * Constructor
     *
     * @param userRoleMap   Map of user identifier to set of role identifiers
     */
    public PolicyManagerImpl(Map<UserId, Set<RoleId>> userRoleMap) {
        this.userRoleMap = checkNotNull(userRoleMap);
    }

    /**
     * No-arguments constructor
     */
    public PolicyManagerImpl() { }

    @Override
    public void add(UserId userId, RoleId... roleIds) {
        if(userRoleMap.containsKey(userId)) {
            Set<RoleId> roles = userRoleMap.get(userId);
            Collections.addAll(roles, roleIds);
            userRoleMap.put(userId, roles);
        }
        else {
            Set<RoleId> roles = new HashSet<>();
            Collections.addAll(roles, roleIds);
            userRoleMap.put(userId, roles);
        }
    }

    @Override
    public void add(RoleId roleId, UserId... userIds) {
        for(UserId userId : userIds) {
            add(userId, roleId);
        }
    }

    @Override
    public void remove(UserId userId, RoleId roleId) throws UserNotInPolicyException {
        Set<RoleId> roles = new HashSet<>(getRoles(userId));
        roles.remove(roleId);

        if(roles.isEmpty()) {
            userRoleMap.remove(userId);
        }
        else {
            userRoleMap.put(userId, roles);
        }
    }

    @Override
    public boolean hasRole(UserId userId, RoleId roleId) throws UserNotInPolicyException {
        checkUserIsInPolicy(userId);
        return userRoleMap.get(userId).contains(roleId);
    }

    @Override
    public Set<RoleId> getRoles(UserId userId) throws UserNotInPolicyException {
        checkUserIsInPolicy(userId);
        return userRoleMap.get(userId);
    }

    @Override
    public boolean hasRoleAssignments(UserId id) {
        checkNotNull(id);
        for(UserId userId : userRoleMap.keySet()) {
            if(userId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<UserId,Set<RoleId>> getUserRoleMappings() {
        return userRoleMap;
    }

    /**
     * Verify whether given user identifier(s) are registered in the access control policy, i.e., in the user-role map
     *
     * @param users   One or more user identifiers
     * @throws UserNotInPolicyException UserId not registered in the access control policy
     */
    private void checkUserIsInPolicy(UserId... users) throws UserNotInPolicyException {
        for(UserId user : users) {
            if (!userRoleMap.containsKey(user)) {
                throw new UserNotInPolicyException("The specified user is not registered in the access control policy");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolicyManagerImpl that = (PolicyManagerImpl) o;
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
