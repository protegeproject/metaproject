package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.PolicyManager;
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
public class PolicyManagerSerializerTest {
    private static final Map<UserId,Set<RoleId>> map1 = Utils.getUserRoleMap(), map2 = Utils.getUserRoleMap();

    private String jsonPolicyManager, jsonOtherPolicyManager, jsonDiffPolicyManager;
    private PolicyManager policyManager, otherPolicyManager, diffPolicyManager;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new SimpleGsonSerializer().getDefaultSerializer();

        policyManager = Utils.getPolicyManager(map1);
        otherPolicyManager = Utils.getPolicyManager(map1);
        diffPolicyManager = Utils.getPolicyManager(map2);

        jsonPolicyManager = gson.toJson(policyManager, PolicyManager.class);
        jsonOtherPolicyManager = gson.toJson(otherPolicyManager, PolicyManager.class);
        jsonDiffPolicyManager = gson.toJson(diffPolicyManager, PolicyManager.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonPolicyManager, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonPolicyManager, PolicyManager.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(policyManager, is(gson.fromJson(jsonPolicyManager, PolicyManager.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(policyManager, is(otherPolicyManager));
        assertThat(jsonPolicyManager, is(jsonOtherPolicyManager));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(policyManager, is(not(diffPolicyManager)));
        assertThat(jsonPolicyManager, is(not(gson.toJson(diffPolicyManager, PolicyManager.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonPolicyManager, PolicyManager.class), is(gson.fromJson(jsonOtherPolicyManager, PolicyManager.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonPolicyManager, PolicyManager.class), is(not(gson.fromJson(jsonDiffPolicyManager, PolicyManager.class))));
    }

    @Test
    public void testGetUserRoleMappingsNotNull() {
        assertThat(gson.fromJson(jsonPolicyManager, PolicyManager.class).getUserRoleMappings(), is(not(equalTo(null))));
    }

    @Test
    public void testGetUserRoleMappings() {
        assertThat(gson.fromJson(jsonPolicyManager, PolicyManager.class).getUserRoleMappings(), is(map1));
    }
}