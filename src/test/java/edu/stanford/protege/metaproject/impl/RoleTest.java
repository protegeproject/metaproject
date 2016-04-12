package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleTest {
    private static final String
            roleIdStr = "testRoleId1",
            otherIdStr = "testRoleId2",
            roleNameStr = "test role name",
            otherRoleNameStr = "test role name 2",
            roleDescriptionStr = "test role description",
            toStringHead = Role.class.getSimpleName();

    private static final RoleId roleId = Utils.getRoleId(roleIdStr), diffRoleId = Utils.getRoleId(otherIdStr);
    private static final Name roleName = Utils.getName(roleNameStr), otherRoleName = Utils.getName(otherRoleNameStr);
    private static final Description roleDescription = Utils.getDescription(roleDescriptionStr);
    private static final Set<OperationId> operations = Utils.getOperationIdSet("testOperationId1", "testOperationId2");

    private Role role, otherRole, diffRole;

    @Before
    public void setUp() {
        role = Utils.getRole(roleId, roleName, roleDescription, operations);
        otherRole = Utils.getRole(roleId, roleName, roleDescription, operations);
        diffRole = Utils.getRole(diffRoleId, otherRoleName, roleDescription, operations);
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
    public void testGetOperations() {
        assertThat(role.getOperations(), is(operations));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(role, is(role));
    }

    @Test
    public void testEquals() {
        assertThat(role, is(otherRole));
    }

    @Test
    public void testNotEquals() {
        assertThat(role, is(not(diffRole)));
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