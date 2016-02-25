package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.AuthenticationDetails;
import edu.stanford.protege.metaproject.api.SaltedPasswordDigest;
import edu.stanford.protege.metaproject.api.UserId;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AuthenticationDetailsSerializerTest {
    private static final UserId userId = Utils.getUserId(), diffUserId = Utils.getUserId();
    private static final SaltedPasswordDigest password = Utils.getSaltedPassword(), diffPassword = Utils.getSaltedPassword();

    private String jsonAuthenticationDetails, jsonOtherAuthenticationDetails, jsonDiffAuthenticationDetails;
    private AuthenticationDetails authenticationDetails, otherAuthenticationDetails, diffAuthenticationDetails;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        authenticationDetails = Utils.getAuthenticationDetails(userId, password);
        otherAuthenticationDetails = Utils.getAuthenticationDetails(userId, password);
        diffAuthenticationDetails = Utils.getAuthenticationDetails(diffUserId, diffPassword);

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