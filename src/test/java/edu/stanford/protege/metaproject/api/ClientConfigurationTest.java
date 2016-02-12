package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ClientConfigurationTest {
    private static final String toStringHead = "ClientConfiguration";
    private static final AccessControlPolicy accessControlPolicy = Utils.getAccessControlPolicy();
    private static final Set<GuiRestriction> disabledElements = Utils.getGUIRestrictionSet();
    private static final int syncDelay = 30;
    private static final Map<String,String> propertiesMap = Utils.getPropertyMap();

    private ClientConfiguration clientConfiguration, otherClientConfiguration, diffClientConfiguration;

    @Before
    public void setUp() {
        clientConfiguration = Utils.getClientConfiguration(accessControlPolicy, syncDelay, disabledElements, propertiesMap);
        otherClientConfiguration = Utils.getClientConfiguration(accessControlPolicy, syncDelay, disabledElements, propertiesMap);;
        diffClientConfiguration = Utils.getClientConfiguration();
    }

    @Test
    public void testNotNull() {
        assertThat(clientConfiguration, is(not(equalTo(null))));
    }

    @Test
    public void testGetPolicy() {
        assertThat(clientConfiguration.getAccessControlPolicy(), is(accessControlPolicy));
    }

    @Test
    public void testGetSynchronisationDelay() {
        assertThat(clientConfiguration.getSynchronisationDelay(), is(syncDelay));
    }

    @Test
    public void testGetGUIRestrictions() {
        assertThat(clientConfiguration.getGuiRestrictions(), is(disabledElements));
    }

    @Test
    public void testGetProperties() {
        assertThat(clientConfiguration.getProperties(), is(propertiesMap));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(clientConfiguration, is(clientConfiguration));
    }

    @Test
    public void testEquals() {
        assertThat(clientConfiguration, is(otherClientConfiguration));
    }

    @Test
    public void testNotEquals() {
        assertThat(clientConfiguration, is(not(diffClientConfiguration)));
    }

    @Test
    public void testHashcode() {
        assertThat(clientConfiguration.hashCode(), is(otherClientConfiguration.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(clientConfiguration.toString(), startsWith(toStringHead));
    }
}
