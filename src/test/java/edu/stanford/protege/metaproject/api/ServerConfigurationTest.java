package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerConfigurationTest {
    private static final String toStringHead = "ServerConfiguration";
    private static final Host host = Utils.getHost();
    private static final Policy policy = Utils.getPolicySample();
    private static final OntologyTermIdStatus idStatus = Utils.getOntologyTermIdStatus();
    private static Map<String,String> propertiesMap = Utils.getStringPropertyMap();

    private ServerConfiguration serverConfiguration, otherServerConfiguration, diffServerConfiguration;

    @Before
    public void setUp() {
        serverConfiguration = Utils.getServerConfiguration(host, policy, propertiesMap, idStatus);
        otherServerConfiguration = Utils.getServerConfiguration(host, policy, propertiesMap, idStatus);
        diffServerConfiguration = Utils.getServerConfiguration();
    }

    @Test
    public void testNotNull() {
        assertThat(serverConfiguration, is(not(equalTo(null))));
    }

    @Test
    public void testGetHost() {
        assertThat(serverConfiguration.getHost(), is(host));
    }

    @Test
    public void testGetPolicy() {
        assertThat(serverConfiguration.getPolicy(), is(policy));
    }

    @Test
    public void testGetOntologyTermIdStatus() {
        assertThat(serverConfiguration.getOntologyTermIdStatus(), is(idStatus));
    }

    @Test
    public void testGetProperties() {
        assertThat(serverConfiguration.getProperties(), is(propertiesMap));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(serverConfiguration, is(serverConfiguration));
    }

    @Test
    public void testEquals() {
        assertThat(serverConfiguration, is(otherServerConfiguration));
    }

    @Test
    public void testNotEquals() {
        assertThat(serverConfiguration, is(not(diffServerConfiguration)));
    }

    @Test
    public void testHashcode() {
        assertThat(serverConfiguration.hashCode(), is(otherServerConfiguration.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(serverConfiguration.toString(), startsWith(toStringHead));
    }
}
