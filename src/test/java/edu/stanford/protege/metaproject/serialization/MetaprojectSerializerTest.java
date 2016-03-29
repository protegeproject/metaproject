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
    private static final Policy POLICY = Utils.getPolicy(Utils.getUserRoleMap());
    private static final UserRegistry USER_REGISTRY = Utils.getUserRegistry(Utils.getUserSet());
    private static final RoleRegistry ROLE_REGISTRY = Utils.getRoleManager(Utils.getRoleSet());
    private static final ProjectRegistry PROJECT_REGISTRY = Utils.getProjectRegistry(Utils.getProjectSet());
    private static final OperationRegistry OPERATION_REGISTRY = Utils.getOperationManager(Utils.getOperationSet());

    private String jsonPolicy, jsonOtherPolicy, jsonDiffPolicy;
    private Metaproject metaproject, otherMetaproject, diffMetaproject;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        metaproject = Utils.getMetaproject(POLICY, USER_REGISTRY, ROLE_REGISTRY, OPERATION_REGISTRY, PROJECT_REGISTRY);
        otherMetaproject = Utils.getMetaproject(POLICY, USER_REGISTRY, ROLE_REGISTRY, OPERATION_REGISTRY, PROJECT_REGISTRY);
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
        assertThat(gson.fromJson(jsonPolicy, Metaproject.class).getPolicy(), is(POLICY));
    }

    @Test
    public void testGetUserManager() {
        assertThat(gson.fromJson(jsonPolicy, Metaproject.class).getUserRegistry(), is(USER_REGISTRY));
    }

    @Test
    public void testGetRoleManager() {
        assertThat(gson.fromJson(jsonPolicy, Metaproject.class).getRoleRegistry(), is(ROLE_REGISTRY));
    }

    @Test
    public void testGetProjectManager() {
        assertThat(gson.fromJson(jsonPolicy, Metaproject.class).getProjectRegistry(), is(PROJECT_REGISTRY));
    }

    @Test
    public void testGetOperationManager() {
        assertThat(gson.fromJson(jsonPolicy, Metaproject.class).getOperationRegistry(), is(OPERATION_REGISTRY));
    }
}