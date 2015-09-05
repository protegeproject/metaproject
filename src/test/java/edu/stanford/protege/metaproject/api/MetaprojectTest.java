package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.exception.PolicyException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;
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
public class MetaprojectTest {
    private static final String toStringHead = "Metaproject";
    private static final User user1 = Utils.getUser(), user2 = Utils.getUser(), user3 = Utils.getUser();
    private static final UserManager userManager = Utils.getUserManager(Utils.getUserSet(user1, user2, user3));

    private static final Operation operation1 = Utils.getOperation(), operation2 = Utils.getOperation(), operation3 = Utils.getOperation();
    private static final OperationManager operationManager = Utils.getOperationManager(Utils.getOperationSet(operation1, operation2, operation3));

    private static final Project project1 = Utils.getProject(), project2 = Utils.getProject(), project3 = Utils.getProject();
    private static final ProjectManager projectManager = Utils.getProjectManager(Utils.getProjectSet(project1, project2, project3));

    private static final Role
            role1 = Utils.getRole(project1.getId(), operation1.getId()),
            role2 = Utils.getRole(project2.getId(), operation2.getId()),
            role3 = Utils.getRole(project3.getId(), operation3.getId());

    private static final RoleManager roleManager = Utils.getRoleManager(Utils.getRoleSet(role1, role2, role3));

    private static PolicyManager policyManager = Utils.getPolicyManager();

    private Metaproject metaproject, otherMetaproject, diffMetaproject;

    @Before
    public void setUp() throws PolicyException {
        policyManager.add(user1.getId(), role1.getId(), role2.getId());
        policyManager.add(user3.getId(), role1.getId(), role2.getId());

        metaproject = Utils.getMetaproject(policyManager, userManager, roleManager, operationManager, projectManager);
        otherMetaproject = Utils.getMetaproject(policyManager, userManager, roleManager, operationManager, projectManager);
        diffMetaproject = Utils.getMetaproject();
    }

    @Test
    public void testNotNull() {
        assertThat(metaproject, is(not(equalTo(null))));
    }

    @Test
    public void testGetUserManager() {
        assertThat(metaproject.getUserManager(), is(userManager));
    }

    @Test
    public void testGetRoleManager() {
        assertThat(metaproject.getRoleManager(), is(roleManager));
    }

    @Test
    public void testGetProjectManager() {
        assertThat(metaproject.getProjectManager(), is(projectManager));
    }

    @Test
    public void testGetOperationManager() {
        assertThat(metaproject.getOperationManager(), is(operationManager));
    }

    @Test
    public void testGetPolicyManager() {
        assertThat(metaproject.getPolicyManager(), is(policyManager));
    }

    @Test
    public void testIsOperationAllowed() throws PolicyException {
        assertThat(metaproject.getPolicyManager().getRoles(user1.getId()).contains(role1.getId()), is(true));
        assertThat(metaproject.isOperationAllowed(operation1.getId(), project1.getId(), user1.getId()), is(true));
        assertThat(metaproject.isOperationAllowed(operation3.getId(), project1.getId(), user1.getId()), is(false));
    }

    @Test
    public void testGetOperationsInProject() throws UserNotInPolicyException {
        Set<Operation> results = new HashSet<>(); results.add(operation1);
        assertThat(metaproject.getOperationsInProject(user1.getId(), project1.getId()), is(results));
        assertThat(metaproject.getOperationsInProject(user3.getId(), project3.getId()).isEmpty(), is(true));
    }

    @Test
    public void testGetProjects() throws UserNotInPolicyException {
        Set<Project> projects = new HashSet<>();
        projects.add(project1); projects.add(project2);
        assertThat(metaproject.getProjects(user1.getId()), is(projects));
    }

    @Test
    public void testGetRoles() throws UserNotInPolicyException {
        Set<Role> roles = new HashSet<>();
        roles.add(role1); roles.add(role2);
        assertThat(metaproject.getRoles(user1.getId()), is(roles));
    }

    @Test
    public void testGetUsers() {
        Set<User> users = new HashSet<>();
        users.add(user1); users.add(user3);
        assertThat(metaproject.getUsers(project1.getId()), is(users));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(metaproject, is(metaproject));
    }

    @Test
    public void testEquals() {
        assertThat(metaproject, is(otherMetaproject));
    }

    @Test
    public void testNotEquals() {
        assertThat(metaproject, is(not(diffMetaproject)));
    }

    @Test
    public void testHashcode() {
        assertThat(metaproject.hashCode(), is(otherMetaproject.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(metaproject.toString(), startsWith(toStringHead));
    }
}
