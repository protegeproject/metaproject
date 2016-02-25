package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.Policy;
import edu.stanford.protege.metaproject.api.ProjectId;
import edu.stanford.protege.metaproject.api.RoleId;
import edu.stanford.protege.metaproject.api.UserId;
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
    private static final Map<UserId,Map<ProjectId,Set<RoleId>>> map1 = Utils.getUserRoleMap(), map2 = Utils.getUserRoleMap();

    private String jsonPolicyManager, jsonOtherPolicyManager, jsonDiffPolicyManager;
    private Policy policy, otherPolicy, diffPolicy;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        policy = Utils.getPolicyManager(map1);
        otherPolicy = Utils.getPolicyManager(map1);
        diffPolicy = Utils.getPolicyManager(map2);

        jsonPolicyManager = gson.toJson(policy, Policy.class);
        jsonOtherPolicyManager = gson.toJson(otherPolicy, Policy.class);
        jsonDiffPolicyManager = gson.toJson(diffPolicy, Policy.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonPolicyManager, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonPolicyManager, Policy.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(policy, is(gson.fromJson(jsonPolicyManager, Policy.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(policy, is(otherPolicy));
        assertThat(jsonPolicyManager, is(jsonOtherPolicyManager));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(policy, is(not(diffPolicy)));
        assertThat(jsonPolicyManager, is(not(gson.toJson(diffPolicy, Policy.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonPolicyManager, Policy.class), is(gson.fromJson(jsonOtherPolicyManager, Policy.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonPolicyManager, Policy.class), is(not(gson.fromJson(jsonDiffPolicyManager, Policy.class))));
    }

    @Test
    public void testGetUserRoleMappingsNotNull() {
        assertThat(gson.fromJson(jsonPolicyManager, Policy.class).getPolicyMappings(), is(not(equalTo(null))));
    }

    @Test
    public void testGetUserRoleMappings() {
        assertThat(gson.fromJson(jsonPolicyManager, Policy.class).getPolicyMappings(), is(map1));
    }
}