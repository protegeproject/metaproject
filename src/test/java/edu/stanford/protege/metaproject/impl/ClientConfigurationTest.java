package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.ClientConfiguration;
import edu.stanford.protege.metaproject.api.Metaproject;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ClientConfigurationTest {
    private static final String toStringHead = ClientConfiguration.class.getSimpleName();
    private static final Metaproject metaproject = Utils.getMetaproject();
    private static final int syncDelay = 30;
    private static final Map<String,String> propertiesMap = Utils.getPropertyMap();

    private ClientConfiguration clientConfiguration, otherClientConfiguration, diffClientConfiguration;

    @Before
    public void setUp() {
        clientConfiguration = Utils.getClientConfiguration(metaproject, syncDelay, propertiesMap);
        otherClientConfiguration = Utils.getClientConfiguration(metaproject, syncDelay, propertiesMap);;
        diffClientConfiguration = Utils.getClientConfiguration();
    }

    @Test
    public void testNotNull() {
        assertThat(clientConfiguration, is(not(equalTo(null))));
    }

    @Test
    public void testGetMetaproject() {
        assertThat(clientConfiguration.getMetaproject(), is(metaproject));
    }

    @Test
    public void testGetSynchronisationDelay() {
        assertThat(clientConfiguration.getSynchronisationDelay(), is(syncDelay));
    }

    @Test
    public void testGetProperties() {
        assertThat(clientConfiguration.getProperties(), is(propertiesMap));
    }

    @Test
    public void testAddProperty() {
        String key = "key1", value = "val1";
        assertThat(clientConfiguration.getProperties().containsKey(key), is(false));
        clientConfiguration.addProperty(key, value);
        assertThat(clientConfiguration.getProperties().containsKey(key), is(true));
        assertThat(clientConfiguration.getProperties().get(key), is(value));
    }

    @Test
    public void testRemoveProperty() {
        String key = "key2", value = "val2";
        clientConfiguration.addProperty(key, value);
        assertThat(clientConfiguration.getProperties().containsKey(key), is(true));
        clientConfiguration.removeProperty(key);
        assertThat(clientConfiguration.getProperties().containsKey(key), is(false));
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
