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
public class PortTest {
    private static final String toStringHead = Port.class.getSimpleName();
    private static final Integer
            portNr = 8008,
            diffPortNr = 8009;

    private Port port, otherPort, diffPort;

    @Before
    public void setUp() {
        port = Utils.getPort(portNr);
        otherPort = Utils.getPort(portNr);
        diffPort = Utils.getPort(diffPortNr);
    }

    @Test
    public void testNotNull() {
        assertThat(port, is(not(equalTo(null))));
    }

    @Test
    public void testGet() {
        assertThat(port.get(), is(portNr));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(port, is(port));
    }

    @Test
    public void testEquals() {
        assertThat(port, is(otherPort));
    }

    @Test
    public void testNotEquals() {
        assertThat(port, is(not(diffPort)));
    }

    @Test
    public void testHashCode() {
        assertThat(port.hashCode(), is(otherPort.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(port.toString(), startsWith(toStringHead));
    }
}