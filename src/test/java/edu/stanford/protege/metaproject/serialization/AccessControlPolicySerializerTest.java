package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AccessControlPolicySerializerTest {
    private static final PolicyManager policyManager = Utils.getPolicyManager(Utils.getUserRoleMap());
    private static final UserManager userManager = Utils.getUserManager(Utils.getUserSet());
    private static final RoleManager roleManager = Utils.getRoleManager(Utils.getRoleSet());
    private static final ProjectManager projectManager = Utils.getProjectManager(Utils.getProjectSet());
    private static final OperationManager operationManager = Utils.getOperationManager(Utils.getOperationSet());

    private String jsonPolicy, jsonOtherPolicy, jsonDiffPolicy;
    private AccessControlPolicy accessControlPolicy, otherAccessControlPolicy, diffAccessControlPolicy;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultGsonSerializer().getDefaultSerializer();

        accessControlPolicy = Utils.getAccessControlPolicy(policyManager, userManager, roleManager, operationManager, projectManager);
        otherAccessControlPolicy = Utils.getAccessControlPolicy(policyManager, userManager, roleManager, operationManager, projectManager);
        diffAccessControlPolicy = Utils.getAccessControlPolicy();


        jsonPolicy = gson.toJson(accessControlPolicy, AccessControlPolicy.class);
        jsonOtherPolicy = gson.toJson(otherAccessControlPolicy, AccessControlPolicy.class);
        jsonDiffPolicy = gson.toJson(diffAccessControlPolicy, AccessControlPolicy.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonPolicy, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonPolicy, AccessControlPolicy.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(accessControlPolicy, is(gson.fromJson(jsonPolicy, AccessControlPolicy.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(accessControlPolicy, is(otherAccessControlPolicy));
        assertThat(jsonPolicy, is(jsonOtherPolicy));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(accessControlPolicy, is(not(diffAccessControlPolicy)));
        assertThat(jsonPolicy, is(not(gson.toJson(diffAccessControlPolicy, AccessControlPolicy.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonPolicy, AccessControlPolicy.class), is(gson.fromJson(jsonOtherPolicy, AccessControlPolicy.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonPolicy, AccessControlPolicy.class), is(not(gson.fromJson(jsonDiffPolicy, AccessControlPolicy.class))));
    }

    @Test
    public void testGetPolicyManager() {
        assertThat(gson.fromJson(jsonPolicy, AccessControlPolicy.class).getPolicyManager(), is(policyManager));
    }

    @Test
    public void testGetUserManager() {
        assertThat(gson.fromJson(jsonPolicy, AccessControlPolicy.class).getUserManager(), is(userManager));
    }

    @Test
    public void testGetRoleManager() {
        assertThat(gson.fromJson(jsonPolicy, AccessControlPolicy.class).getRoleManager(), is(roleManager));
    }

    @Test
    public void testGetProjectManager() {
        assertThat(gson.fromJson(jsonPolicy, AccessControlPolicy.class).getProjectManager(), is(projectManager));
    }

    @Test
    public void testGetOperationManager() {
        assertThat(gson.fromJson(jsonPolicy, AccessControlPolicy.class).getOperationManager(), is(operationManager));
    }
}