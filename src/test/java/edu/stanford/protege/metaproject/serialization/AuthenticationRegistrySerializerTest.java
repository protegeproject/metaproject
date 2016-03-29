package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.AuthenticationRegistry;
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
public class AuthenticationRegistrySerializerTest {
    private static final Set<AuthenticationDetails> authenticationSet = Utils.getAuthenticationDetailsSet(), diffAuthenticationSet = Utils.getAuthenticationDetailsSet(1);

    private String jsonAuthenticationManager, jsonOtherAuthenticationManager, jsonDiffAuthenticationManager;
    private AuthenticationRegistry authenticationRegistry, otherAuthenticationRegistry, diffAuthenticationRegistry;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        authenticationRegistry = Utils.getAuthenticationRegistry(authenticationSet);
        otherAuthenticationRegistry = Utils.getAuthenticationRegistry(authenticationSet);
        diffAuthenticationRegistry = Utils.getAuthenticationRegistry(diffAuthenticationSet);

        jsonAuthenticationManager = gson.toJson(authenticationRegistry, AuthenticationRegistry.class);
        jsonOtherAuthenticationManager = gson.toJson(otherAuthenticationRegistry, AuthenticationRegistry.class);
        jsonDiffAuthenticationManager = gson.toJson(diffAuthenticationRegistry, AuthenticationRegistry.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonAuthenticationManager, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonAuthenticationManager, AuthenticationRegistry.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(authenticationRegistry, is(gson.fromJson(jsonAuthenticationManager, AuthenticationRegistry.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(authenticationRegistry, is(otherAuthenticationRegistry));
        assertThat(jsonAuthenticationManager, is(jsonOtherAuthenticationManager));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(authenticationRegistry, is(not(diffAuthenticationRegistry)));
        assertThat(jsonAuthenticationManager, is(not(gson.toJson(diffAuthenticationRegistry, AuthenticationRegistry.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonAuthenticationManager, AuthenticationRegistry.class), is(gson.fromJson(jsonOtherAuthenticationManager, AuthenticationRegistry.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonAuthenticationManager, AuthenticationRegistry.class), is(not(gson.fromJson(jsonDiffAuthenticationManager, AuthenticationRegistry.class))));
    }

    @Test
    public void testGetAuthenticationsNotNull() {
        assertThat(gson.fromJson(jsonAuthenticationManager, AuthenticationRegistry.class).getAuthenticationDetails(), is(not(equalTo(null))));
    }

    @Test
    public void testGetAuthentications() {
        assertThat(gson.fromJson(jsonAuthenticationManager, AuthenticationRegistry.class).getAuthenticationDetails(), is(authenticationSet));
    }
}