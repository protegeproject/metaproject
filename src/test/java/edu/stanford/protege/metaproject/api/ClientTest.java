package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ClientTest {
    private static final String toStringHead = Client.class.getSimpleName();
    private static ClientConfiguration config = Utils.getClientConfiguration(), diffConfig = Utils.getClientConfiguration();

    private Client client, otherClient, diffClient;

    @Before
    public void setUp() {
        client = Utils.getClient(config);
        otherClient = Utils.getClient(config);
        diffClient = Utils.getClient(diffConfig);
    }

    @Test
    public void testNotNull() {
        assertThat(client, is(not(equalTo(null))));
    }

    @Test
    public void testGetConfiguration() {
        assertThat(client.getConfiguration(), is(config));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(client, is(client));
    }

    @Test
    public void testEquals() {
        assertThat(client, is(otherClient));
    }

    @Test
    public void testNotEquals() {
        assertThat(client, is(not(diffClient)));
    }

    @Test
    public void testHashCode() {
        assertThat(client.hashCode(), is(otherClient.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(client.toString(), startsWith(toStringHead));
    }
}