package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.AuthenticationDetails;
import edu.stanford.protege.metaproject.api.SaltedPasswordDigest;
import edu.stanford.protege.metaproject.api.UserId;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class AuthenticationDetailsSerializerTest {
    private static final UserId userId = TestUtils.getUserId(), diffUserId = TestUtils.getUserId();
    private static final SaltedPasswordDigest password = TestUtils.getSaltedPassword(), diffPassword = TestUtils.getSaltedPassword();

    private String jsonAuthenticationDetails, jsonOtherAuthenticationDetails, jsonDiffAuthenticationDetails;
    private AuthenticationDetails authenticationDetails, otherAuthenticationDetails, diffAuthenticationDetails;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getGson();

        authenticationDetails = TestUtils.getAuthenticationDetails(userId, password);
        otherAuthenticationDetails = TestUtils.getAuthenticationDetails(userId, password);
        diffAuthenticationDetails = TestUtils.getAuthenticationDetails(diffUserId, diffPassword);

        jsonAuthenticationDetails = gson.toJson(authenticationDetails);
        jsonOtherAuthenticationDetails = gson.toJson(otherAuthenticationDetails);
        jsonDiffAuthenticationDetails = gson.toJson(diffAuthenticationDetails);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonAuthenticationDetails, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonAuthenticationDetails, AuthenticationDetails.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(authenticationDetails, is(gson.fromJson(jsonAuthenticationDetails, AuthenticationDetails.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(authenticationDetails, is(otherAuthenticationDetails));
        assertThat(jsonAuthenticationDetails, is(jsonOtherAuthenticationDetails));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(authenticationDetails, is(not(diffAuthenticationDetails)));
        assertThat(jsonAuthenticationDetails, is(not(gson.toJson(diffAuthenticationDetails))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonAuthenticationDetails, AuthenticationDetails.class), is(gson.fromJson(jsonOtherAuthenticationDetails, AuthenticationDetails.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonAuthenticationDetails, AuthenticationDetails.class), is(not(gson.fromJson(jsonDiffAuthenticationDetails, AuthenticationDetails.class))));
    }

    @Test
    public void testGetUserId() {
        assertThat(gson.fromJson(jsonAuthenticationDetails, AuthenticationDetails.class).getUserId(), is(userId));
    }

    @Test
    public void testGetPassword() {
        assertThat(gson.fromJson(jsonAuthenticationDetails, AuthenticationDetails.class).getPassword(), is(password));
    }
}