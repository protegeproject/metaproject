package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.ServerConfiguration;
import edu.stanford.protege.metaproject.impl.ConfigurationBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ServerConfigurationSerializerTest {
    private String jsonServerConfiguration, jsonOtherServerConfiguration, jsonDiffServerConfiguration;
    private ServerConfiguration config, otherServerConfiguration, diffServerConfiguration;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getGson();

        config = TestUtils.getServerConfiguration();
        otherServerConfiguration = new ConfigurationBuilder(config).createServerConfiguration();
        diffServerConfiguration = TestUtils.getServerConfiguration();

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
}