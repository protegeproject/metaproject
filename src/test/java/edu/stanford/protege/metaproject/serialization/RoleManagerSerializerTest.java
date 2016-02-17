package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.Role;
import edu.stanford.protege.metaproject.api.RoleManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleManagerSerializerTest {
    private static final Set<Role> roleSet = Utils.getRoleSet(5), diffRoleSet = Utils.getRoleSet(3);

    private String jsonRoleManager, jsonOtherRoleManager, jsonDiffRoleManager;
    private RoleManager roleManager, otherRoleManager, diffRoleManager;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultGsonSerializer().getDefaultSerializer();

        roleManager = Utils.getRoleManager(roleSet);
        otherRoleManager = Utils.getRoleManager(roleSet);
        diffRoleManager = Utils.getRoleManager(diffRoleSet);

        jsonRoleManager = gson.toJson(roleManager, RoleManager.class);
        jsonOtherRoleManager = gson.toJson(otherRoleManager, RoleManager.class);
        jsonDiffRoleManager = gson.toJson(diffRoleManager, RoleManager.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonRoleManager, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonRoleManager, RoleManager.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(roleManager, is(gson.fromJson(jsonRoleManager, RoleManager.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(roleManager, is(otherRoleManager));
        assertThat(jsonRoleManager, is(jsonOtherRoleManager));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(roleManager, is(not(diffRoleManager)));
        assertThat(jsonRoleManager, is(not(gson.toJson(diffRoleManager, RoleManager.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonRoleManager, RoleManager.class), is(gson.fromJson(jsonOtherRoleManager, RoleManager.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonRoleManager, RoleManager.class), is(not(gson.fromJson(jsonDiffRoleManager, RoleManager.class))));
    }

    @Test
    public void testGetRolesNotNull() {
        assertThat(gson.fromJson(jsonRoleManager, RoleManager.class).getRoles(), is(not(equalTo(null))));
    }

    @Test
    public void testGetRoles() {
        assertThat(gson.fromJson(jsonDiffRoleManager, RoleManager.class).getRoles(), is(diffRoleSet));
    }
}