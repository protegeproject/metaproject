package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.Host;
import edu.stanford.protege.metaproject.api.Port;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
@RunWith(MockitoJUnitRunner.class)
public class HostTest {
    private static final String toStringHead = Host.class.getSimpleName();

    @Mock private Port port, diffPort;
    private URI hostAddress = Utils.getUri(), diffHostAddress = Utils.getUri();
    private Port optionalPort = Utils.getPort(5100);
    private Host host, otherHost, diffHost;

    @Before
    public void setUp() {
        host = Utils.getHost(hostAddress, Optional.of(optionalPort));
        otherHost = Utils.getHost(hostAddress, Optional.of(optionalPort));
        diffHost = Utils.getHost(diffHostAddress, Optional.of(optionalPort));
    }

    @Test
    public void testNotNull() {
        assertThat(host, is(not(equalTo(null))));
    }

    @Test
    public void testGetUri() {
        assertThat(host.getUri(), is(hostAddress));
    }

    @Test
    public void getOptionalPort() {
        assertThat(host.getSecondaryPort(), is(Optional.of(optionalPort)));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(host, is(host));
    }

    @Test
    public void testEquals() {
        assertThat(host, is(otherHost));
    }

    @Test
    public void testNotEquals() {
        assertThat(host, is(not(diffHost)));
    }

    @Test
    public void testHashCode() {
        assertThat(host.hashCode(), is(otherHost.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(host.toString(), startsWith(toStringHead));
    }
}