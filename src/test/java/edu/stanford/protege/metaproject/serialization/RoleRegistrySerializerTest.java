package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.Role;
import edu.stanford.protege.metaproject.api.RoleRegistry;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class RoleRegistrySerializerTest {
    private static final Set<Role> roleSet = Utils.getRoleSet(5), diffRoleSet = Utils.getRoleSet(3);

    private String jsonRoleManager, jsonOtherRoleManager, jsonDiffRoleManager;
    private RoleRegistry roleRegistry, otherRoleRegistry, diffRoleRegistry;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        roleRegistry = Utils.getRoleManager(roleSet);
        otherRoleRegistry = Utils.getRoleManager(roleSet);
        diffRoleRegistry = Utils.getRoleManager(diffRoleSet);

        jsonRoleManager = gson.toJson(roleRegistry, RoleRegistry.class);
        jsonOtherRoleManager = gson.toJson(otherRoleRegistry, RoleRegistry.class);
        jsonDiffRoleManager = gson.toJson(diffRoleRegistry, RoleRegistry.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonRoleManager, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonRoleManager, RoleRegistry.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(roleRegistry, is(gson.fromJson(jsonRoleManager, RoleRegistry.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(roleRegistry, is(otherRoleRegistry));
        assertThat(jsonRoleManager, is(jsonOtherRoleManager));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(roleRegistry, is(not(diffRoleRegistry)));
        assertThat(jsonRoleManager, is(not(gson.toJson(diffRoleRegistry, RoleRegistry.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonRoleManager, RoleRegistry.class), is(gson.fromJson(jsonOtherRoleManager, RoleRegistry.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonRoleManager, RoleRegistry.class), is(not(gson.fromJson(jsonDiffRoleManager, RoleRegistry.class))));
    }

    @Test
    public void testGetRolesNotNull() {
        assertThat(gson.fromJson(jsonRoleManager, RoleRegistry.class).getEntries(), is(not(equalTo(null))));
    }

    @Test
    public void testGetRoles() {
        assertThat(gson.fromJson(jsonDiffRoleManager, RoleRegistry.class).getEntries(), is(diffRoleSet));
    }
}