package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
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
public class RoleSerializerTest {
    private static final String roleIdStr = "testRoleId1", diffIdStr = "testRoleId2";
    private static final RoleId roleId = Utils.getRoleId(roleIdStr), diffRoleId = Utils.getRoleId(diffIdStr);
    private static final Name roleName = Utils.getName();
    private static final Description roleDescription = Utils.getDescription();
    private static final Set<ProjectId> projects = Utils.getProjectIdSet(3);
    private static final Set<OperationId> operations = Utils.getOperationIdSet(3);

    private String jsonRole, jsonOtherRole, jsonDiffRole;
    private Role role, otherRole, diffRole;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new SimpleGsonSerializer().getDefaultSerializer();

        role = Utils.getRole(roleId, roleName, roleDescription, projects, operations);
        otherRole = Utils.getRole(roleId, roleName, roleDescription, projects, operations);
        diffRole = Utils.getRole(diffRoleId, roleName, roleDescription, projects, operations);

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

    @Test
    public void testGetProjects() {
        assertThat(gson.fromJson(jsonRole, Role.class).getProjects(), is(projects));
    }
}