package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PolicySerializerTest {
    private static final Map<UserId,Set<RoleId>> userRoleMap = Utils.getUserRoleMap();
    private static final UserManager userManager = Utils.getUserManager(Utils.getUserSet());
    private static final RoleManager roleManager = Utils.getRoleManager(Utils.getRoleSet());
    private static final ProjectManager projectManager = Utils.getProjectManager(Utils.getProjectSet());
    private static final OperationManager operationManager = Utils.getOperationManager(Utils.getOperationSet());

    private String jsonPolicy, jsonOtherPolicy, jsonDiffPolicy;
    private Policy policy, otherPolicy, diffPolicy;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new SimpleGsonSerializer().getDefaultSerializer();

        policy = Utils.getPolicy(userRoleMap, userManager, roleManager, operationManager, projectManager);
        otherPolicy = Utils.getPolicy(userRoleMap, userManager, roleManager, operationManager, projectManager);
        diffPolicy = Utils.getPolicySample();

        jsonPolicy = gson.toJson(policy, Policy.class);
        jsonOtherPolicy = gson.toJson(otherPolicy, Policy.class);
        jsonDiffPolicy = gson.toJson(diffPolicy, Policy.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonPolicy, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonPolicy, Policy.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(policy, is(gson.fromJson(jsonPolicy, Policy.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(policy, is(otherPolicy));
        assertThat(jsonPolicy, is(jsonOtherPolicy));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(policy, is(not(diffPolicy)));
        assertThat(jsonPolicy, is(not(gson.toJson(diffPolicy, Policy.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonPolicy, Policy.class), is(gson.fromJson(jsonOtherPolicy, Policy.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonPolicy, Policy.class), is(not(gson.fromJson(jsonDiffPolicy, Policy.class))));
    }

    @Test
    public void testGetUserRoleMappings() {
        assertThat(gson.fromJson(jsonPolicy, Policy.class).getUserRoleMappings(), is(userRoleMap));
    }

    @Test
    public void testGetUserManager() {
        assertThat(gson.fromJson(jsonPolicy, Policy.class).getUserManager(), is(userManager));
    }

    @Test
    public void testGetRoleManager() {
        assertThat(gson.fromJson(jsonPolicy, Policy.class).getRoleManager(), is(roleManager));
    }

    @Test
    public void testGetProjectManager() {
        assertThat(gson.fromJson(jsonPolicy, Policy.class).getProjectManager(), is(projectManager));
    }

    @Test
    public void testGetOperationManager() {
        assertThat(gson.fromJson(jsonPolicy, Policy.class).getOperationManager(), is(operationManager));
    }
}