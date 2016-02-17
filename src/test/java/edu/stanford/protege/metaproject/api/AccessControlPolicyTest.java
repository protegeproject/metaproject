package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AccessControlPolicyTest {
    private static final String toStringHead = AccessControlPolicy.class.getSimpleName();
    private static final User user1 = Utils.getUser(), user2 = Utils.getUser(), user3 = Utils.getUser();
    private static final UserManager userManager = Utils.getUserManager(Utils.getUserSet(user1, user2, user3));

    private static final Operation operation1 = Utils.getOperation(), operation2 = Utils.getOperation(), operation3 = Utils.getOperation();
    private static final OperationManager operationManager = Utils.getOperationManager(Utils.getOperationSet(operation1, operation2, operation3));

    private static final Project project1 = Utils.getProject(), project2 = Utils.getProject(), project3 = Utils.getProject();
    private static final ProjectManager projectManager = Utils.getProjectManager(Utils.getProjectSet(project1, project2, project3));

    private static final Role
            role1 = Utils.getRole(operation1.getId()),
            role2 = Utils.getRole(operation2.getId()),
            role3 = Utils.getRole(operation3.getId());

    private static final RoleManager roleManager = Utils.getRoleManager(Utils.getRoleSet(role1, role2, role3));

    private static PolicyManager policyManager = Utils.getPolicyManager();

    private AccessControlPolicy accessControlPolicy, otherAccessControlPolicy, diffAccessControlPolicy;

    @Before
    public void setUp() throws PolicyException {
        policyManager.add(user1.getId(), project1.getId(), role1.getId(), role2.getId());
        policyManager.add(user3.getId(), project2.getId(), role1.getId(), role2.getId());

        accessControlPolicy = Utils.getAccessControlPolicy(policyManager, userManager, roleManager, operationManager, projectManager);
        otherAccessControlPolicy = Utils.getAccessControlPolicy(policyManager, userManager, roleManager, operationManager, projectManager);
        diffAccessControlPolicy = Utils.getAccessControlPolicy();
    }

    @Test
    public void testNotNull() {
        assertThat(accessControlPolicy, is(not(equalTo(null))));
    }

    @Test
    public void testGetUserManager() {
        assertThat(accessControlPolicy.getUserManager(), is(userManager));
    }

    @Test
    public void testGetRoleManager() {
        assertThat(accessControlPolicy.getRoleManager(), is(roleManager));
    }

    @Test
    public void testGetProjectManager() {
        assertThat(accessControlPolicy.getProjectManager(), is(projectManager));
    }

    @Test
    public void testGetOperationManager() {
        assertThat(accessControlPolicy.getOperationManager(), is(operationManager));
    }

    @Test
    public void testGetPolicyManager() {
        assertThat(accessControlPolicy.getPolicyManager(), is(policyManager));
    }

    @Test
    public void testIsOperationAllowed() throws ProjectNotInPolicyException, UnknownAccessControlObjectIdException, UserNotInPolicyException {
        assertThat(accessControlPolicy.getPolicyManager().getRoles(user1.getId(), project1.getId()).contains(role1.getId()), is(true));
        assertThat(accessControlPolicy.isOperationAllowed(operation1.getId(), project1.getId(), user1.getId()), is(true));
        assertThat(accessControlPolicy.isOperationAllowed(operation3.getId(), project1.getId(), user1.getId()), is(false));
    }

    @Test
    public void testGetOperationsInProject() throws UserNotInPolicyException, ProjectNotInPolicyException {
        Set<Operation> results = new HashSet<>();
        results.add(operation1); results.add(operation2);
        assertThat(accessControlPolicy.getOperationsInProject(user1.getId(), project1.getId()), is(results));
    }

    @Test(expected=ProjectNotInPolicyException.class)
    public void testGetOperationsInProjectThrowsException() throws UserNotInPolicyException, ProjectNotInPolicyException {
        assertThat(accessControlPolicy.getOperationsInProject(user3.getId(), project3.getId()).isEmpty(), is(true));
    }

    @Test
    public void testGetProjects() throws UserNotInPolicyException {
        Set<Project> projects = new HashSet<>();
        projects.add(project1);
        assertThat(accessControlPolicy.getProjects(user1.getId()), is(projects));
    }

    @Test
    public void testGetRoles() throws UserNotInPolicyException, ProjectNotInPolicyException {
        Set<Role> roles = new HashSet<>();
        roles.add(role1); roles.add(role2);
        assertThat(accessControlPolicy.getRoles(user1.getId(), project1.getId()), is(roles));
    }

    @Test
    public void testGetUsers() throws MetaprojectException {
        Set<User> users = new HashSet<>();
        users.add(user1);
        assertThat(accessControlPolicy.getUsers(project1.getId()), is(users));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(accessControlPolicy, is(accessControlPolicy));
    }

    @Test
    public void testEquals() {
        assertThat(accessControlPolicy, is(otherAccessControlPolicy));
    }

    @Test
    public void testNotEquals() {
        assertThat(accessControlPolicy, is(not(diffAccessControlPolicy)));
    }

    @Test
    public void testHashcode() {
        assertThat(accessControlPolicy.hashCode(), is(otherAccessControlPolicy.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(accessControlPolicy.toString(), startsWith(toStringHead));
    }
}
