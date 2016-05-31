package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.PolicyException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class MetaprojectTest {
    private static final String toStringHead = Metaproject.class.getSimpleName();
    private static final User user1 = Utils.getUser(), user2 = Utils.getUser(), user3 = Utils.getUser();
    private static final UserRegistry userRegistry = Utils.getUserRegistry(Utils.getUserSet(user1, user2, user3));

    private static final Operation operation1 = Utils.getSystemOperation(), operation2 = Utils.getSystemOperation(), operation3 = Utils.getSystemOperation();
    private static final OperationRegistry operationRegistry = Utils.getOperationManager(Utils.getOperationSet(operation1, operation2, operation3));

    private static final Project project1 = Utils.getProject(), project2 = Utils.getProject(), project3 = Utils.getProject();
    private static final ProjectRegistry projectRegistry = Utils.getProjectRegistry(Utils.getProjectSet(project1, project2, project3));

    private static final Role
            role1 = Utils.getRole(operation1.getId()),
            role2 = Utils.getRole(operation2.getId()),
            role3 = Utils.getRole(operation3.getId());

    private static final RoleRegistry roleRegistry = Utils.getRoleManager(Utils.getRoleSet(role1, role2, role3));

    private static Policy policy = Utils.getPolicy();

    private Metaproject metaproject, otherMetaproject, diffMetaproject;

    @Before
    public void setUp() throws PolicyException {
        policy.add(user1.getId(), project1.getId(), role1.getId(), role2.getId());
        policy.add(user3.getId(), project2.getId(), role1.getId(), role2.getId());

        metaproject = Utils.getMetaproject(policy, userRegistry, roleRegistry, operationRegistry, projectRegistry);
        otherMetaproject = Utils.getMetaproject(policy, userRegistry, roleRegistry, operationRegistry, projectRegistry);
        diffMetaproject = Utils.getMetaproject();
    }

    @Test
    public void testNotNull() {
        assertThat(metaproject, is(not(equalTo(null))));
    }

    @Test
    public void testGetUserManager() {
        assertThat(metaproject.getUserRegistry(), is(userRegistry));
    }

    @Test
    public void testGetRoleManager() {
        assertThat(metaproject.getRoleRegistry(), is(roleRegistry));
    }

    @Test
    public void testGetProjectManager() {
        assertThat(metaproject.getProjectRegistry(), is(projectRegistry));
    }

    @Test
    public void testGetOperationManager() {
        assertThat(metaproject.getOperationRegistry(), is(operationRegistry));
    }

    @Test
    public void testGetPolicyManager() {
        assertThat(metaproject.getPolicy(), is(policy));
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
