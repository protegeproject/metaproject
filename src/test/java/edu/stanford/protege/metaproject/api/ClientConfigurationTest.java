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
    private static final Metaproject metaproject = Utils.getMetaproject();
    private static final Set<GUIRestriction> disabledElements = Utils.getGUIRestrictionSet();
    private static final int syncDelay = 30;
    private static final Map<String,String> propertiesMap = Utils.getStringPropertyMap();

    private ClientConfiguration clientConfiguration, otherClientConfiguration, diffClientConfiguration;

    @Before
    public void setUp() {
        clientConfiguration = Utils.getClientConfiguration(metaproject, syncDelay, disabledElements, propertiesMap);
        otherClientConfiguration = Utils.getClientConfiguration(metaproject, syncDelay, disabledElements, propertiesMap);;
        diffClientConfiguration = Utils.getClientConfiguration();
    }

    @Test
    public void testNotNull() {
        assertThat(clientConfiguration, is(not(equalTo(null))));
    }

    @Test
    public void testGetPolicy() {
        assertThat(clientConfiguration.getMetaproject(), is(metaproject));
    }

    @Test
    public void testGetSynchronisationDelay() {
        assertThat(clientConfiguration.getSynchronisationDelay(), is(syncDelay));
    }

    @Test
    public void testGetGUIRestrictions() {
        assertThat(clientConfiguration.getGUIRestrictions(), is(disabledElements));
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
