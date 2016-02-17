package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleManagerTest {
    private static final Role role1 = Utils.getRole(), role2 = Utils.getRole(), role3 = Utils.getRole(), role4 = Utils.getRole();
    private static final Set<Role> roleSet = Utils.getRoleSet(role1, role2, role3, role4);
    private static final String toStringHead = "RoleManager";

    private RoleManager roleManager, otherRoleManager, diffRoleManager;

    @Before
    public void setUp() {
        roleManager = Utils.getRoleManager(roleSet);
        otherRoleManager = Utils.getRoleManager(roleSet);
        diffRoleManager = Utils.getRoleManager();
    }

    @Test
    public void testNotNull() {
        assertThat(roleManager, is(not(equalTo(null))));
    }

    @Test
    public void testGetRoles() {
        assertThat(roleManager.getRoles(), is(roleSet));
    }

    @Test
    public void testGetRoleById() throws UnknownRoleIdException {
        assertThat(roleManager.getRole(role1.getId()), is(role1));
    }

    @Test
    public void testRemoveRole() throws UnknownAccessControlObjectIdException {
        assertThat(roleManager.getRoles().contains(role4), is(true));
        roleManager.remove(role4);
        assertThat(roleManager.getRoles().contains(role4), is(false));
    }

    @Test
    public void testAddRole() throws UnknownRoleIdException {
        Role role5 = Utils.getRole();
        assertThat(roleManager.getRoles().contains(role5), is(false));

        roleManager.add(role5);
        assertThat(roleManager.getRoles().contains(role5), is(true));
        assertThat(roleManager.getRole(role5.getId()), is(role5));
    }

    @Test
    public void testChangeDescription() throws UnknownRoleIdException {
        Description newDescription = Utils.getDescription("new test description");
        roleManager.changeDescription(role2.getId(), newDescription);
        assertThat(roleManager.getRole(role2.getId()).getDescription(), is(newDescription));
    }

    @Test
    public void testChangeName() throws UnknownRoleIdException {
        Name newName = Utils.getName("new test name");
        roleManager.changeName(role2.getId(), newName);
        assertThat(roleManager.getRole(role2.getId()).getName(), is(newName));
    }

    @Test
    public void testExists() {
        RoleId roleId = Utils.getRoleId("newTestRoleId");
        assertThat(roleManager.contains(role1.getId()), is(true));
        assertThat(roleManager.contains(roleId), is(false));
    }

    @Test
    public void testAddOperation() throws UnknownRoleIdException {
        OperationId newOperation = Utils.getOperationId();
        assertThat(roleManager.getRole(role2.getId()).getOperations().contains(newOperation), is(false));

        roleManager.addOperation(role2.getId(), newOperation);
        assertThat(roleManager.getRole(role2.getId()).getOperations().contains(newOperation), is(true));
    }

    @Test
    public void testRemoveOperation() throws UnknownRoleIdException {
        OperationId operationId = role2.getOperations().iterator().next();
        roleManager.removeOperation(role2.getId(), operationId);
        assertThat(roleManager.getRole(role2.getId()).getOperations().contains(operationId), is(false));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(roleManager, is(roleManager));
    }

    @Test
    public void testEquals() {
        assertThat(roleManager, is(otherRoleManager));
    }

    @Test
    public void testNotEquals() {
        assertThat(roleManager, is(not(diffRoleManager)));
    }

    @Test
    public void testHashCode() {
        assertThat(roleManager.hashCode(), is(otherRoleManager.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(roleManager.toString(), startsWith(toStringHead));
    }
}
