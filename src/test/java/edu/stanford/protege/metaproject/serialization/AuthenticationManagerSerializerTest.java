package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.AuthenticationManager;
import edu.stanford.protege.metaproject.api.AuthenticationDetails;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AuthenticationManagerSerializerTest {
    private static final Set<AuthenticationDetails> authenticationSet = Utils.getAuthenticationDetailsSet(), diffAuthenticationSet = Utils.getAuthenticationDetailsSet(1);

    private String jsonAuthenticationManager, jsonOtherAuthenticationManager, jsonDiffAuthenticationManager;
    private AuthenticationManager authenticationManager, otherAuthenticationManager, diffAuthenticationManager;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        authenticationManager = Utils.getAuthenticationManager(authenticationSet);
        otherAuthenticationManager = Utils.getAuthenticationManager(authenticationSet);
        diffAuthenticationManager = Utils.getAuthenticationManager(diffAuthenticationSet);

        jsonAuthenticationManager = gson.toJson(authenticationManager, AuthenticationManager.class);
        jsonOtherAuthenticationManager = gson.toJson(otherAuthenticationManager, AuthenticationManager.class);
        jsonDiffAuthenticationManager = gson.toJson(diffAuthenticationManager, AuthenticationManager.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonAuthenticationManager, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonAuthenticationManager, AuthenticationManager.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(authenticationManager, is(gson.fromJson(jsonAuthenticationManager, AuthenticationManager.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(authenticationManager, is(otherAuthenticationManager));
        assertThat(jsonAuthenticationManager, is(jsonOtherAuthenticationManager));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(authenticationManager, is(not(diffAuthenticationManager)));
        assertThat(jsonAuthenticationManager, is(not(gson.toJson(diffAuthenticationManager, AuthenticationManager.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonAuthenticationManager, AuthenticationManager.class), is(gson.fromJson(jsonOtherAuthenticationManager, AuthenticationManager.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonAuthenticationManager, AuthenticationManager.class), is(not(gson.fromJson(jsonDiffAuthenticationManager, AuthenticationManager.class))));
    }

    @Test
    public void testGetAuthenticationsNotNull() {
        assertThat(gson.fromJson(jsonAuthenticationManager, AuthenticationManager.class).getAuthenticationDetails(), is(not(equalTo(null))));
    }

    @Test
    public void testGetAuthentications() {
        assertThat(gson.fromJson(jsonAuthenticationManager, AuthenticationManager.class).getAuthenticationDetails(), is(authenticationSet));
    }
}