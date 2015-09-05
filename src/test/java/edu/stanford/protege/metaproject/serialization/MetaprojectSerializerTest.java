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
public class MetaprojectSerializerTest {
    private static final PolicyManager policyManager = Utils.getPolicyManager(Utils.getUserRoleMap());
    private static final UserManager userManager = Utils.getUserManager(Utils.getUserSet());
    private static final RoleManager roleManager = Utils.getRoleManager(Utils.getRoleSet());
    private static final ProjectManager projectManager = Utils.getProjectManager(Utils.getProjectSet());
    private static final OperationManager operationManager = Utils.getOperationManager(Utils.getOperationSet());

    private String jsonPolicy, jsonOtherPolicy, jsonDiffPolicy;
    private Metaproject metaproject, otherMetaproject, diffMetaproject;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new SimpleGsonSerializer().getDefaultSerializer();

        metaproject = Utils.getMetaproject(policyManager, userManager, roleManager, operationManager, projectManager);
        otherMetaproject = Utils.getMetaproject(policyManager, userManager, roleManager, operationManager, projectManager);
        diffMetaproject = Utils.getMetaproject();


        jsonPolicy = gson.toJson(metaproject, Metaproject.class);
        jsonOtherPolicy = gson.toJson(otherMetaproject, Metaproject.class);
        jsonDiffPolicy = gson.toJson(diffMetaproject, Metaproject.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonPolicy, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonPolicy, Metaproject.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(metaproject, is(gson.fromJson(jsonPolicy, Metaproject.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(metaproject, is(otherMetaproject));
        assertThat(jsonPolicy, is(jsonOtherPolicy));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(metaproject, is(not(diffMetaproject)));
        assertThat(jsonPolicy, is(not(gson.toJson(diffMetaproject, Metaproject.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonPolicy, Metaproject.class), is(gson.fromJson(jsonOtherPolicy, Metaproject.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonPolicy, Metaproject.class), is(not(gson.fromJson(jsonDiffPolicy, Metaproject.class))));
    }

    @Test
    public void testGetPolicyManager() {
        assertThat(gson.fromJson(jsonPolicy, Metaproject.class).getPolicyManager(), is(policyManager));
    }

    @Test
    public void testGetUserManager() {
        assertThat(gson.fromJson(jsonPolicy, Metaproject.class).getUserManager(), is(userManager));
    }

    @Test
    public void testGetRoleManager() {
        assertThat(gson.fromJson(jsonPolicy, Metaproject.class).getRoleManager(), is(roleManager));
    }

    @Test
    public void testGetProjectManager() {
        assertThat(gson.fromJson(jsonPolicy, Metaproject.class).getProjectManager(), is(projectManager));
    }

    @Test
    public void testGetOperationManager() {
        assertThat(gson.fromJson(jsonPolicy, Metaproject.class).getOperationManager(), is(operationManager));
    }
}