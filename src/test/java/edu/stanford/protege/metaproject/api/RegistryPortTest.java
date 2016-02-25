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
public class RegistryPortTest {
    private static final String toStringHead = RegistryPort.class.getSimpleName();
    private static final Integer
            portNr = 5100,
            diffPortNr = 5200;

    private RegistryPort port, otherPort, diffPort;

    @Before
    public void setUp() {
        port = Utils.getRegistryPort(portNr);
        otherPort = Utils.getRegistryPort(portNr);
        diffPort = Utils.getRegistryPort(diffPortNr);
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