package edu.stanford.protege.metaproject.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
@RunWith(MockitoJUnitRunner.class)
public class RoleTest {
    private static final String
            roleIdStr = "testRoleId1",
            otherIdStr = "testRoleId2",
            roleNameStr = "test role name",
            roleDescriptionStr = "test role description",
            toStringHead = "Role";

    private static final RoleId roleId = TestUtils.getRoleId(roleIdStr), diffRoleId = TestUtils.getRoleId(otherIdStr);
    private static final Name roleName = TestUtils.getName(roleNameStr);
    private static final Description roleDescription = TestUtils.getDescription(roleDescriptionStr);
    private static final Set<OperationId> operations = TestUtils.getOperationIdSet("testOperationId1", "testOperationId2");
    private static final Set<ProjectId> projects = TestUtils.getProjectIdSet("testProjectId1", "testProjectId2");

    private Role role, otherRole, diffRole;

    @Before
    public void setUp() {
        role = TestUtils.getRole(roleId, roleName, roleDescription, projects, operations);
        otherRole = TestUtils.getRole(roleId, roleName, roleDescription, projects, operations);
        diffRole = TestUtils.getRole(diffRoleId, roleName, roleDescription, projects, operations);
    }

    @Test
    public void testNotNull() {
        assertThat(role, is(not(equalTo(null))));
    }

    @Test
    public void testGetId() {
        assertThat(role.getId().get(), is(roleIdStr));
    }

    @Test
    public void testGetName() {
        assertThat(role.getName().get(), is(roleNameStr));
    }

    @Test
    public void testGetDescription() {
        assertThat(role.getDescription().get(), is(roleDescriptionStr));
    }

    @Test
    public void testGetProjects() {
        assertThat(role.getProjects(), is(projects));
    }

    @Test
    public void testGetOperations() {
        assertThat(role.getOperations(), is(operations));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(role, is(equalTo(role)));
    }

    @Test
    public void testEquals() {
        assertThat(role, is(equalTo(otherRole)));
    }

    @Test
    public void testNotEquals() {
        assertThat(role, is(not(equalTo(diffRole))));
    }

    @Test
    public void testHashCode() {
        assertThat(role.hashCode(), is(otherRole.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(role.toString(), startsWith(toStringHead));
    }

    @Test
    public void testCompareToSelf() {
        assertThat(role.compareTo(role), is(0));
    }

    @Test
    public void testCompareTo() {
        assertThat(role.compareTo(otherRole), is(0));
    }

    @Test
    public void testCompareToAnother() {
        assertThat(role.compareTo(diffRole), is(-1));
    }

    @Test
    public void testCompareToAnotherReversed() {
        assertThat(diffRole.compareTo(role), is(1));
    }
}