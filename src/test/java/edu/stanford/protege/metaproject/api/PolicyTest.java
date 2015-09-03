package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.exception.PolicyException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PolicyTest {
    private static final String toStringHead = "Policy";
    private static final User user1 = Utils.getUser(), user2 = Utils.getUser(), user3 = Utils.getUser(), user4 = Utils.getUser();
    private static final UserManager userManager = Utils.getUserManager(Utils.getUserSet(user1, user2, user3));

    private static final Operation operation1 = Utils.getOperation(), operation2 = Utils.getOperation(), operation3 = Utils.getOperation();
    private static final OperationManager operationManager = Utils.getOperationManager(Utils.getOperationSet(operation1, operation2, operation3));

    private static final Project project1 = Utils.getProject(), project2 = Utils.getProject(), project3 = Utils.getProject();
    private static final ProjectManager projectManager = Utils.getProjectManager(Utils.getProjectSet(project1, project2, project3));

    private static final Role
            role1 = Utils.getRole(project1.getId(), operation1.getId()),
            role2 = Utils.getRole(project2.getId(), operation2.getId()),
            role3 = Utils.getRole(project3.getId(), operation3.getId());

    private static final Set<RoleId> roles = Utils.getRoleIdSet(role1.getId(), role2.getId());

    private static final RoleManager roleManager = Utils.getRoleManager(Utils.getRoleSet(role1, role2, role3));

    private static Map<UserId,Set<RoleId>> userRoleMap = new HashMap<>();

    private Policy policy, otherPolicy, diffPolicy;

    @Before
    public void setUp() throws PolicyException {
        userRoleMap.put(user1.getId(), roles);
        userRoleMap.put(user3.getId(), roles);

        policy = Utils.getPolicy(userRoleMap, userManager, roleManager, operationManager, projectManager);
        otherPolicy = Utils.getPolicy(userRoleMap, userManager, roleManager, operationManager, projectManager);
        diffPolicy = Utils.getPolicy();
    }

    @Test
    public void testNotNull() {
        assertThat(policy, is(not(equalTo(null))));
    }

    @Test
    public void testGetUserManager() {
        assertThat(policy.getUserManager(), is(userManager));
    }

    @Test
    public void testGetRoleManager() {
        assertThat(policy.getRoleManager(), is(roleManager));
    }

    @Test
    public void testGetProjectManager() {
        assertThat(policy.getProjectManager(), is(projectManager));
    }

    @Test
    public void testGetOperationManager() {
        assertThat(policy.getOperationManager(), is(operationManager));
    }

    @Test
    public void testGetUserRoleMappings() {
        assertThat(policy.getUserRoleMappings(), is(userRoleMap));
    }

    @Test(expected=PolicyException.class)
    public void testAddPolicyThrowsException() throws PolicyException {
        UserId userId = Utils.getUserId();
        policy.addPolicy(userId, role1.getId());
    }

    @Test
    public void testAddPolicy() throws PolicyException {
        assertThat(policy.getUserRoleMappings().get(user2.getId()), is(equalTo(null)));

        policy.addPolicy(user2.getId(), role1.getId(), role2.getId());
        assertThat(policy.getUserRoleMappings().get(user2.getId()), is(not(equalTo(null))));
        assertThat(policy.hasRole(user2.getId(), role1.getId()), is(true));
        assertThat(policy.hasRole(user2.getId(), role2.getId()), is(true));
    }

    @Test
    public void testAddPolicyRoleBased() throws PolicyException {
        assertThat(policy.getUserRoleMappings().get(user3.getId()).contains(role3.getId()), is(false));

        policy.addPolicy(role3.getId(), user3.getId());
        assertThat(policy.getUserRoleMappings().get(user3.getId()), is(not(equalTo(null))));
        assertThat(policy.hasRole(user3.getId(), role3.getId()), is(true));
    }

    @Test
    public void testRemovePolicy() throws PolicyException {
        assertThat(policy.getRoles(user3.getId()).contains(role1.getId()), is(true));

        policy.removePolicy(user3.getId(), role1.getId());
        assertThat(policy.getRoles(user3.getId()).contains(role1.getId()), is(false));
    }

    @Test
    public void testHasRole() throws PolicyException {
        assertThat(policy.hasRole(user1.getId(), role1.getId()), is(true));
    }

    @Test(expected=PolicyException.class)
    public void testHasRoleThrowsException() throws PolicyException {
        policy.hasRole(user4.getId(), role1.getId());
    }

    @Test
    public void testGetRoles() throws UserNotInPolicyException {
        assertThat(policy.getRoles(user1.getId()), is(roles));
    }

    @Test(expected=UserNotInPolicyException.class)
    public void testGetRolesThrowsException() throws UserNotInPolicyException {
        policy.getRoles(Utils.getUserId());
    }

    @Test
    public void testIsOperationAllowed() throws PolicyException {
        assertThat(policy.getRoles(user1.getId()).contains(role1.getId()), is(true));
        assertThat(policy.isOperationAllowed(operation1.getId(), project1.getId(), user1.getId()), is(true));
        assertThat(policy.isOperationAllowed(operation3.getId(), project1.getId(), user1.getId()), is(false));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(policy, is(policy));
    }

    @Test
    public void testEquals() {
        assertThat(policy, is(otherPolicy));
    }

    @Test
    public void testNotEquals() {
        assertThat(policy, is(not(diffPolicy)));
    }

    @Test
    public void testHashcode() {
        assertThat(policy.hashCode(), is(otherPolicy.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(policy.toString(), startsWith(toStringHead));
    }
}
