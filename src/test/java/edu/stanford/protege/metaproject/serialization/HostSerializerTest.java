package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.Address;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.Port;
import edu.stanford.protege.metaproject.api.RegistryPort;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class HostSerializerTest {
    private static final Address hostAddress = Utils.getAddress("testHostId1"), diffHostAddress = Utils.getAddress("testHostId2");
    private static final Port port = Utils.getPort(8080), diffPort = Utils.getPort(8081);
    private static final RegistryPort registryPort = Utils.getRegistryPort(5100);

    private String jsonHost, jsonOtherHost, jsonDiffHost;
    private Host host, otherHost, diffHost;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        host = Utils.getHost(hostAddress, port, registryPort);
        otherHost = Utils.getHost(hostAddress, port, registryPort);
        diffHost = Utils.getHost(diffHostAddress, diffPort, registryPort);

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
        assertThat(gson.fromJson(jsonHost, Host.class).getAddress(), is(hostAddress));
    }

    @Test
    public void testGetPort() {
        assertThat(gson.fromJson(jsonHost, Host.class).getPort(), is(port));
    }
}