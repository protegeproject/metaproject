package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerConfigurationSerializerTest {
    private static final Host host = Utils.getHost(), diffHost = Utils.getHost();
    private static final AccessControlPolicy accessControlPolicy = Utils.getAccessControlPolicy();
    private static final AuthenticationManager authenticationManager = Utils.getAuthenticationManager();
    private static final Map<String,String> propertyMap = Utils.getPropertyMap();
    private static final EntityIriStatus idStatus = Utils.getEntityIriStatus();

    private String jsonServerConfiguration, jsonOtherServerConfiguration, jsonDiffServerConfiguration;
    private ServerConfiguration config, otherServerConfiguration, diffServerConfiguration;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new SimpleGsonSerializer().getDefaultSerializer();

        config = Utils.getServerConfiguration(host, accessControlPolicy, authenticationManager, propertyMap, idStatus);
        otherServerConfiguration = Utils.getServerConfiguration(host, accessControlPolicy, authenticationManager, propertyMap, idStatus);
        diffServerConfiguration = Utils.getServerConfiguration(diffHost, accessControlPolicy, authenticationManager, propertyMap, idStatus);

        jsonServerConfiguration = gson.toJson(config, ServerConfiguration.class);
        jsonOtherServerConfiguration = gson.toJson(otherServerConfiguration, ServerConfiguration.class);
        jsonDiffServerConfiguration = gson.toJson(diffServerConfiguration, ServerConfiguration.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonServerConfiguration, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonServerConfiguration, ServerConfiguration.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(config, is(gson.fromJson(jsonServerConfiguration, ServerConfiguration.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(config, is(otherServerConfiguration));
        assertThat(jsonServerConfiguration, is(jsonOtherServerConfiguration));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(config, is(not(diffServerConfiguration)));
        assertThat(jsonServerConfiguration, is(not(gson.toJson(diffServerConfiguration, ServerConfiguration.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonServerConfiguration, ServerConfiguration.class), is(gson.fromJson(jsonOtherServerConfiguration, ServerConfiguration.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonServerConfiguration, ServerConfiguration.class), is(not(gson.fromJson(jsonDiffServerConfiguration, ServerConfiguration.class))));
    }

    @Test
    public void testGetHost() {
        assertThat(gson.fromJson(jsonServerConfiguration, ServerConfiguration.class).getHost(), is(host));
    }

    @Test
    public void testGetPolicy() {
        assertThat(gson.fromJson(jsonServerConfiguration, ServerConfiguration.class).getAccessControlPolicy(), is(accessControlPolicy));
    }

    @Test
    public void testGetProperties() {
        assertThat(gson.fromJson(jsonServerConfiguration, ServerConfiguration.class).getProperties(), is(Optional.of(propertyMap)));
    }

    @Test
    public void testGetOntologyTermIdStatus() {
        assertThat(gson.fromJson(jsonServerConfiguration, ServerConfiguration.class).getOntologyTermIdStatus(), is(Optional.of(idStatus)));
    }
}