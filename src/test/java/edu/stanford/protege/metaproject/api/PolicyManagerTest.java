package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.exception.PolicyException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PolicyManagerTest {
    private static final UserId user1 = Utils.getUserId(), user2 = Utils.getUserId();
    private static final RoleId role1 = Utils.getRoleId(), role2 = Utils.getRoleId(), role3 = Utils.getRoleId();
    private static final String toStringHead = "PolicyManager";

    private static Map<UserId,Set<RoleId>> map = new HashMap<>();
    private static Set<RoleId> set1 = new HashSet<>(), set2 = new HashSet<>();

    private PolicyManager policyManager, otherPolicyManager, diffPolicyManager;

    @Before
    public void setUp() {
        set1.add(role1); set1.add(role2);
        map.put(user1, set1);

        set2.add(role2); set2.add(role3);
        map.put(user2, set2);

        policyManager = Utils.getPolicyManager(map);
        otherPolicyManager = Utils.getPolicyManager(map);
        diffPolicyManager = Utils.getPolicyManager();
    }

    @Test
    public void testNotNull() {
        assertThat(policyManager, is(not(equalTo(null))));
    }

    @Test
    public void testGetUserRoleMappings() {
        assertThat(policyManager.getUserRoleMappings(), is(map));
    }

    @Test
    public void testRemovePolicy() throws UserNotInPolicyException {
        assertThat(policyManager.hasRole(user2, role2), is(true));
        policyManager.remove(user2, role2);
        assertThat(policyManager.hasRole(user2, role2), is(false));
    }

    @Test
    public void testAddPolicy() throws UserNotInPolicyException {
        RoleId roleId = Utils.getRoleId();
        assertThat(policyManager.hasRole(user2, roleId), is(false));

        policyManager.add(user2, roleId);
        assertThat(policyManager.hasRole(user2, roleId), is(true));
        assertThat(policyManager.getRoles(user2).contains(roleId), is(true));
    }
    @Test
    public void testAddPolicyRoleBased() throws PolicyException {
        assertThat(policyManager.getUserRoleMappings().get(user2).contains(role1), is(false));
        UserId newUser = Utils.getUserId();
        policyManager.add(role1, user2, newUser);

        assertThat(policyManager.getUserRoleMappings().get(newUser), is(not(equalTo(null))));
        assertThat(policyManager.hasRole(newUser, role1), is(true));
        assertThat(policyManager.hasRole(user2, role1), is(true));
    }

    @Test
    public void testHasRole() throws PolicyException {
        assertThat(policyManager.hasRole(user1, role1), is(true));
    }

    @Test(expected=UserNotInPolicyException.class)
    public void testHasRoleThrowsException() throws UserNotInPolicyException {
        policyManager.hasRole(Utils.getUserId(), role3);
    }

    @Test
    public void testGetRoles() throws UserNotInPolicyException {
        assertThat(policyManager.getRoles(user1), is(set1));
    }

    @Test(expected=UserNotInPolicyException.class)
    public void testGetRolesThrowsException() throws UserNotInPolicyException {
        policyManager.getRoles(Utils.getUserId());
    }

    @Test
    public void testHasRoleAssignments() {
        assertThat(policyManager.hasRoleAssignments(user1), is(true));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(policyManager, is(policyManager));
    }

    @Test
    public void testEquals() {
        assertThat(policyManager, is(otherPolicyManager));
    }

    @Test
    public void testNotEquals() {
        assertThat(policyManager, is(not(diffPolicyManager)));
    }

    @Test
    public void testHashCode() {
        assertThat(policyManager.hashCode(), is(otherPolicyManager.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(policyManager.toString(), startsWith(toStringHead));
    }
}
