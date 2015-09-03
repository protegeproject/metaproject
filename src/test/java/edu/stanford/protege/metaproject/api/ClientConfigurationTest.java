package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
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
    private static final Policy policy = Utils.getPolicySample();
    private static final Set<JComponent> disabledElements = Utils.getJComponentSet();
    private static final int syncDelay = 30;
    private static final Map<String,String> propertiesMap = Utils.getStringPropertyMap();

    private ClientConfiguration clientConfiguration, otherClientConfiguration, diffClientConfiguration;

    @Before
    public void setUp() {
        clientConfiguration = Utils.getClientConfiguration(policy, syncDelay, disabledElements, propertiesMap);
        otherClientConfiguration = Utils.getClientConfiguration(policy, syncDelay, disabledElements, propertiesMap);;
        diffClientConfiguration = Utils.getClientConfiguration();
    }

    @Test
    public void testNotNull() {
        assertThat(clientConfiguration, is(not(equalTo(null))));
    }

    @Test
    public void testGetPolicy() {
        assertThat(clientConfiguration.getPolicy(), is(policy));
    }

    @Test
    public void testGetSynchronisationDelay() {
        assertThat(clientConfiguration.getSynchronisationDelay(), is(syncDelay));
    }

    @Test
    public void testGetDisabledUIElements() {
        assertThat(clientConfiguration.getDisabledUIElements(), is(disabledElements));
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
