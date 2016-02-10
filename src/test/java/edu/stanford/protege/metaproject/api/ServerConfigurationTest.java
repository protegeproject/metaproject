package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
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
public class ServerConfigurationTest {
    private static final String toStringHead = "ServerConfiguration";
    private static final Host host = Utils.getHost();
    private static final AccessControlPolicy accessControlPolicy = Utils.getAccessControlPolicy();
    private static final EntityIriStatus idStatus = Utils.getEntityIriStatus();
    private static final AuthenticationManager authenticationManager = Utils.getAuthenticationManager();
    private static Map<String,String> propertiesMap = Utils.getStringPropertyMap();

    private ServerConfiguration serverConfiguration, otherServerConfiguration, diffServerConfiguration;

    @Before
    public void setUp() {
        serverConfiguration = Utils.getServerConfiguration(host, accessControlPolicy, authenticationManager, propertiesMap, idStatus);
        otherServerConfiguration = Utils.getServerConfiguration(host, accessControlPolicy, authenticationManager, propertiesMap, idStatus);
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
        assertThat(serverConfiguration.getAccessControlPolicy(), is(accessControlPolicy));
    }

    @Test
    public void testGetOntologyTermIdStatus() {
        assertThat(serverConfiguration.getOntologyTermIdStatus(), is(Optional.of(idStatus)));
    }

    @Test
    public void testGetProperties() {
        assertThat(serverConfiguration.getProperties(), is(Optional.of(propertiesMap)));
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
