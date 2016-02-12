package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.ClientConfiguration;
import edu.stanford.protege.metaproject.api.GuiRestriction;
import edu.stanford.protege.metaproject.api.AccessControlPolicy;
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
public class ClientConfigurationSerializerTest {
    private static final Map<String,String> propertyMap = Utils.getPropertyMap();
    private static final AccessControlPolicy accessControlPolicy = Utils.getAccessControlPolicy(), diffAccessControlPolicy = Utils.getAccessControlPolicy();
    private static final Set<GuiRestriction> disabledUIElements = Utils.getGUIRestrictionSet();
    private static final int syncDelay = 150;

    private String jsonClientConfiguration, jsonOtherClientConfiguration, jsonDiffClientConfiguration;
    private ClientConfiguration config, otherClientConfiguration, diffClientConfiguration;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new SimpleGsonSerializer().getDefaultSerializer();

        config = Utils.getClientConfiguration(accessControlPolicy, syncDelay, disabledUIElements, propertyMap);
        otherClientConfiguration = Utils.getClientConfiguration(accessControlPolicy, syncDelay, disabledUIElements, propertyMap);
        diffClientConfiguration = Utils.getClientConfiguration(diffAccessControlPolicy, syncDelay, disabledUIElements, propertyMap);

        jsonClientConfiguration = gson.toJson(config);
        jsonOtherClientConfiguration = gson.toJson(otherClientConfiguration);
        jsonDiffClientConfiguration = gson.toJson(diffClientConfiguration);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonClientConfiguration, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonClientConfiguration, ClientConfiguration.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(config, is(gson.fromJson(jsonClientConfiguration, ClientConfiguration.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(config, is(otherClientConfiguration));
        assertThat(jsonClientConfiguration, is(jsonOtherClientConfiguration));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(config, is(not(diffClientConfiguration)));
        assertThat(jsonClientConfiguration, is(not(gson.toJson(diffClientConfiguration, ClientConfiguration.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonClientConfiguration, ClientConfiguration.class), is(gson.fromJson(jsonOtherClientConfiguration, ClientConfiguration.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonClientConfiguration, ClientConfiguration.class), is(not(gson.fromJson(jsonDiffClientConfiguration, ClientConfiguration.class))));
    }

    @Test
    public void testGetSynchronisationDelay() {
        assertThat(gson.fromJson(jsonClientConfiguration, ClientConfiguration.class).getSynchronisationDelay(), is(syncDelay));
    }

    @Test
    public void testGetAccessControlPolicy() {
        assertThat(gson.fromJson(jsonClientConfiguration, ClientConfiguration.class).getAccessControlPolicy(), is(accessControlPolicy));
    }

    @Test
    public void testGetProperties() {
        assertThat(gson.fromJson(jsonClientConfiguration, ClientConfiguration.class).getProperties(), is(propertyMap));
    }

    @Test
    public void testGetDisabledUIElements() {
        assertThat(gson.fromJson(jsonClientConfiguration, ClientConfiguration.class).getGuiRestrictions(), is(disabledUIElements));
    }
}