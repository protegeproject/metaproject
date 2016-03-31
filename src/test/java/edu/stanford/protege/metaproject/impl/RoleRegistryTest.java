package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.UnknownRoleIdException;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleRegistryTest {
    private static final Role role1 = Utils.getRole(), role2 = Utils.getRole(), role3 = Utils.getRole(), role4 = Utils.getRole();
    private static final Set<Role> roleSet = Utils.getRoleSet(role1, role2, role3, role4);
    private static final String toStringHead = RoleRegistry.class.getSimpleName();

    private RoleRegistry roleRegistry, otherRoleRegistry, diffRoleRegistry;

    @Before
    public void setUp() {
        roleRegistry = Utils.getRoleManager(roleSet);
        otherRoleRegistry = Utils.getRoleManager(roleSet);
        diffRoleRegistry = Utils.getRoleManager();
    }

    @Test
    public void testNotNull() {
        assertThat(roleRegistry, is(not(equalTo(null))));
    }

    @Test
    public void testGetRoles() {
        assertThat(roleRegistry.getRoles(), is(roleSet));
    }

    @Test
    public void testGetRoleById() throws UnknownRoleIdException {
        assertThat(roleRegistry.getRole(role1.getId()), is(role1));
    }

    @Test
    public void testRemoveRole() {
        assertThat(roleRegistry.getRoles().contains(role4), is(true));
        roleRegistry.remove(role4);
        assertThat(roleRegistry.getRoles().contains(role4), is(false));
    }

    @Test
    public void testAddRole() throws UnknownRoleIdException {
        Role role5 = Utils.getRole();
        assertThat(roleRegistry.getRoles().contains(role5), is(false));

        roleRegistry.add(role5);
        assertThat(roleRegistry.getRoles().contains(role5), is(true));
        assertThat(roleRegistry.getRole(role5.getId()), is(role5));
    }

    @Test
    public void testChangeDescription() throws UnknownRoleIdException {
        Description newDescription = Utils.getDescription("new test description");
        roleRegistry.setDescription(role2.getId(), newDescription);
        assertThat(roleRegistry.getRole(role2.getId()).getDescription(), is(newDescription));
    }

    @Test
    public void testChangeName() throws UnknownRoleIdException {
        Name newName = Utils.getName("new test name");
        roleRegistry.setName(role2.getId(), newName);
        assertThat(roleRegistry.getRole(role2.getId()).getName(), is(newName));
    }

    @Test
    public void testExists() {
        RoleId roleId = Utils.getRoleId("newTestRoleId");
        assertThat(roleRegistry.contains(role1.getId()), is(true));
        assertThat(roleRegistry.contains(roleId), is(false));
    }

    @Test
    public void testAddOperation() throws UnknownRoleIdException {
        OperationId newOperation = Utils.getOperationId();
        assertThat(roleRegistry.getRole(role2.getId()).getOperations().contains(newOperation), is(false));

        roleRegistry.addOperation(role2.getId(), newOperation);
        assertThat(roleRegistry.getRole(role2.getId()).getOperations().contains(newOperation), is(true));
    }

    @Test
    public void testRemoveOperation() throws UnknownRoleIdException {
        OperationId operationId = role2.getOperations().iterator().next();
        roleRegistry.removeOperation(role2.getId(), operationId);
        assertThat(roleRegistry.getRole(role2.getId()).getOperations().contains(operationId), is(false));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(roleRegistry, is(roleRegistry));
    }

    @Test
    public void testEquals() {
        assertThat(roleRegistry, is(otherRoleRegistry));
    }

    @Test
    public void testNotEquals() {
        assertThat(roleRegistry, is(not(diffRoleRegistry)));
    }

    @Test
    public void testHashCode() {
        assertThat(roleRegistry.hashCode(), is(otherRoleRegistry.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(roleRegistry.toString(), startsWith(toStringHead));
    }
}
