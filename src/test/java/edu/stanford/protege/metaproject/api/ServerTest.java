package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerTest {
    private static final String toStringHead = "Server";
    private static ServerConfiguration config = Utils.getServerConfiguration(), diffConfig = Utils.getServerConfiguration();
    private static OntologyTermIdGenerator idGenerator = Utils.getOntologyTermUUIDGenerator();

    private Server server, otherServer, diffServer;

    @Before
    public void setUp() {
        server = Utils.getServer(config, idGenerator);
        otherServer = Utils.getServer(config, idGenerator);
        diffServer = Utils.getServer(diffConfig, idGenerator);
    }

    @Test
    public void testNotNull() {
        assertThat(server, is(not(equalTo(null))));
    }

    @Test
    public void testGetConfiguration() {
        assertThat(server.getConfiguration(), is(config));
    }

    @Test
    public void testGetOntologyTermIdGenerator() {
        assertThat(server.getOntologyTermIdGenerator(), is(idGenerator));
    }

    @Test
    public void testUpdateConfigurationPolicy() {
        Metaproject newMetaproject = Utils.getMetaproject();
        server.updateConfiguration(newMetaproject);
        assertThat(server.getConfiguration().getMetaproject(), is(newMetaproject));
    }

    @Test
    public void testUpdateConfigurationTermStatus() {
        OntologyTermIdStatus newStatus = Utils.getOntologyTermIdStatus();
        server.updateConfiguration(newStatus);
        assertThat(server.getConfiguration().getOntologyTermIdStatus(), is(Optional.of(newStatus)));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(server, is(server));
    }

    @Test
    public void testEquals() {
        assertThat(server, is(otherServer));
    }

    @Test
    public void testNotEquals() {
        assertThat(server, is(not(diffServer)));
    }

    @Test
    public void testHashCode() {
        assertThat(server.hashCode(), is(otherServer.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(server.toString(), startsWith(toStringHead));
    }
}