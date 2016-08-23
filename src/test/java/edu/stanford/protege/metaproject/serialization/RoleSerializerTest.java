package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class RoleSerializerTest {
    private static final String roleIdStr = "testRoleId1", diffIdStr = "testRoleId2";
    private static final RoleId roleId = TestUtils.getRoleId(roleIdStr), diffRoleId = TestUtils.getRoleId(diffIdStr);
    private static final Name roleName = TestUtils.getName();
    private static final Description roleDescription = TestUtils.getDescription();
    private static final Set<OperationId> operations = TestUtils.getOperationIdSet(3);

    private String jsonRole, jsonOtherRole, jsonDiffRole;
    private Role role, otherRole, diffRole;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getGson();

        role = TestUtils.getRole(roleId, roleName, roleDescription, operations);
        otherRole = TestUtils.getRole(roleId, roleName, roleDescription, operations);
        diffRole = TestUtils.getRole(diffRoleId, roleName, roleDescription, operations);

        jsonRole = gson.toJson(role);
        jsonOtherRole = gson.toJson(otherRole);
        jsonDiffRole = gson.toJson(diffRole);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonRole, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonRole, Role.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(role, is(gson.fromJson(jsonRole, Role.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(role, is(otherRole));
        assertThat(jsonRole, is(jsonOtherRole));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(role, is(not(diffRole)));
        assertThat(jsonRole, is(not(gson.toJson(diffRole))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonRole, Role.class), is(gson.fromJson(jsonOtherRole, Role.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonRole, Role.class), is(not(gson.fromJson(jsonDiffRole, Role.class))));
    }

    @Test
    public void testGetId() {
        assertThat(gson.fromJson(jsonRole, Role.class).getId(), is(roleId));
    }

    @Test
    public void testGetName() {
        assertThat(gson.fromJson(jsonRole, Role.class).getName(), is(roleName));
    }

    @Test
    public void testGetDescription() {
        assertThat(gson.fromJson(jsonRole, Role.class).getDescription(), is(roleDescription));
    }

    @Test
    public void testGetOperations() {
        assertThat(gson.fromJson(jsonRole, Role.class).getOperations(), is(operations));
    }
}