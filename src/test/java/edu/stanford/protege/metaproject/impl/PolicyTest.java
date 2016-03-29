package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.Policy;
import edu.stanford.protege.metaproject.api.ProjectId;
import edu.stanford.protege.metaproject.api.RoleId;
import edu.stanford.protege.metaproject.api.UserId;
import edu.stanford.protege.metaproject.api.exception.PolicyException;
import edu.stanford.protege.metaproject.api.exception.ProjectNotInPolicyException;
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
public class PolicyTest {
    private static final UserId user1 = Utils.getUserId(), user2 = Utils.getUserId();
    private static final RoleId role1 = Utils.getRoleId(), role2 = Utils.getRoleId(), role3 = Utils.getRoleId();
    private static final ProjectId project1 = Utils.getProjectId(), project2 = Utils.getProjectId(), project3 = Utils.getProjectId();
    private static final String toStringHead = Policy.class.getSimpleName();

    private static Map<UserId,Map<ProjectId, Set<RoleId>>> map = new HashMap<>();
    private static Set<RoleId> set1 = new HashSet<>(), set2 = new HashSet<>();

    private Policy policy, otherPolicy, diffPolicy;

    @Before
    public void setUp() {
        Map<ProjectId,Set<RoleId>> assignments1 = new HashMap<>();
        set1.add(role1); set1.add(role2);
        assignments1.put(project1, set1);
        map.put(user1, assignments1);

        Map<ProjectId,Set<RoleId>> assignments2 = new HashMap<>();
        set2.add(role2); set2.add(role3);
        assignments2.put(project2, set2);
        map.put(user2, assignments2);

        policy = Utils.getPolicy(map);
        otherPolicy = Utils.getPolicy(map);
        diffPolicy = Utils.getPolicy();
    }

    @Test
    public void testNotNull() {
        assertThat(policy, is(not(equalTo(null))));
    }

    @Test
    public void testGetUserRoleMappings() {
        assertThat(policy.getPolicyMappings(), is(map));
    }

    @Test
    public void testRemovePolicy() throws UserNotInPolicyException, ProjectNotInPolicyException {
        assertThat(policy.hasRole(user2, project2, role2), is(true));

        policy.remove(user2, project2, role2);
        assertThat(policy.hasRole(user2, project2, role2), is(false));

        policy.remove(user2, project2, role3);
        assertThat(policy.hasRole(user2, project2, role3), is(false));

        assertThat(policy.hasRole(user2, project2), is(false));
    }

    @Test
    public void testAddPolicy() throws UserNotInPolicyException, ProjectNotInPolicyException {
        RoleId roleId = Utils.getRoleId();
        assertThat(policy.hasRole(user2, project3, roleId), is(false));

        policy.add(user2, project3, roleId);
        assertThat(policy.hasRole(user2, project3, roleId), is(true));
        assertThat(policy.getRoles(user2, project3).contains(roleId), is(true));
    }
    @Test
    public void testAddPolicyRoleBased() throws PolicyException {
        assertThat(policy.getPolicyMappings().get(user2).get(project2).contains(role1), is(false));
        UserId newUser = Utils.getUserId();
        policy.add(role1, project2, user2, newUser);

        assertThat(policy.getPolicyMappings().get(newUser), is(not(equalTo(null))));
        assertThat(policy.hasRole(newUser, project2, role1), is(true));
        assertThat(policy.hasRole(user2, project2, role1), is(true));
    }

    @Test
    public void testHasRole() throws PolicyException {
        assertThat(policy.hasRole(user1, project1, role1), is(true));
        assertThat(policy.hasRole(user1, project2, role1), is(false));
    }

    @Test
    public void testGetRoles() throws UserNotInPolicyException, ProjectNotInPolicyException {
        assertThat(policy.getRoles(user1, project1), is(set1));
    }

    @Test(expected=UserNotInPolicyException.class)
    public void testGetRolesThrowsException() throws UserNotInPolicyException, ProjectNotInPolicyException {
        policy.getRoles(Utils.getUserId(), project1);
    }

    @Test
    public void testHasRoleAssignments() {
        assertThat(policy.hasRole(user1, project1), is(true));
    }

    /*
    @Test
    public void testIsOperationAllowed() throws ProjectNotInPolicyException, UnknownAccessControlObjectIdException, UserNotInPolicyException {
        assertThat(metaprojectManager.getPolicy().getRoles(user1.getId(), project1.getId()).hasRole(role1.getId()), is(true));
        assertThat(metaprojectManager.isOperationAllowed(operation1.getId(), project1.getId(), user1.getId()), is(true));
        assertThat(metaprojectManager.isOperationAllowed(operation3.getId(), project1.getId(), user1.getId()), is(false));
    }

    @Test
    public void testGetOperationsInProject() throws UserNotInPolicyException, ProjectNotInPolicyException {
        Set<Operation> results = new HashSet<>();
        results.add(operation1); results.add(operation2);
        assertThat(metaprojectManager.getOperationsInProject(user1.getId(), project1.getId()), is(results));
    }

    @Test(expected=ProjectNotInPolicyException.class)
    public void testGetOperationsInProjectThrowsException() throws UserNotInPolicyException, ProjectNotInPolicyException {
        assertThat(metaprojectManager.getOperationsInProject(user3.getId(), project3.getId()).isEmpty(), is(true));
    }

    @Test
    public void testGetProjects() throws UserNotInPolicyException {
        Set<Project> projects = new HashSet<>();
        projects.add(project1);
        assertThat(metaprojectManager.getProjects(user1.getId()), is(projects));
    }

    @Test
    public void testGetRoles() throws UserNotInPolicyException, ProjectNotInPolicyException {
        Set<Role> roles = new HashSet<>();
        roles.add(role1); roles.add(role2);
        assertThat(metaprojectManager.getRoles(user1.getId(), project1.getId()), is(roles));
    }

    @Test
    public void testGetUsers() throws MetaprojectException {
        Set<User> users = new HashSet<>();
        users.add(user1);
        assertThat(metaprojectManager.getUsers(project1.getId()), is(users));
    }
     */

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
    public void testHashCode() {
        assertThat(policy.hashCode(), is(otherPolicy.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(policy.toString(), startsWith(toStringHead));
    }
}
