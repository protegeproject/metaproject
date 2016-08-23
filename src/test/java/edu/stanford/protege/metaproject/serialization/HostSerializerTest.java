package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.Port;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class HostSerializerTest {
    private static final URI hostAddress = TestUtils.getUri("rmi://testHostId1"), diffHostAddress = TestUtils.getUri("rmi://testHostId2");
    private static final Optional<Port> optionalPort = Optional.of(TestUtils.getPort(5100));

    private String jsonHost, jsonOtherHost, jsonDiffHost;
    private Host host, otherHost, diffHost;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getGson();

        host = TestUtils.getHost(hostAddress, optionalPort);
        otherHost = TestUtils.getHost(hostAddress, optionalPort);
        diffHost = TestUtils.getHost(diffHostAddress, optionalPort);

        jsonHost = gson.toJson(host);
        jsonOtherHost = gson.toJson(otherHost);
        jsonDiffHost = gson.toJson(diffHost);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonHost, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonHost, Host.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(host, is(gson.fromJson(jsonHost, Host.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(host, is(otherHost));
        assertThat(jsonHost, is(jsonOtherHost));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(host, is(not(diffHost)));
        assertThat(jsonHost, is(not(gson.toJson(diffHost))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonHost, Host.class), is(gson.fromJson(jsonOtherHost, Host.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonHost, Host.class), is(not(gson.fromJson(jsonDiffHost, Host.class))));
    }

    @Test
    public void testGetAddress() {
        assertThat(gson.fromJson(jsonHost, Host.class).getUri(), is(hostAddress));
    }
}