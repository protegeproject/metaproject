package edu.stanford.protege.metaproject.impl;

import com.google.common.io.Files;
import edu.stanford.protege.metaproject.api.ConfigurationWriter;
import edu.stanford.protege.metaproject.api.Serializer;
import edu.stanford.protege.metaproject.api.ServerConfiguration;
import edu.stanford.protege.metaproject.serialization.DefaultJsonSerializer;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ConfigurationWriterTest {
    private ConfigurationWriter writer;
    private Serializer serializer;

    @Before
    public void setUp() throws Exception {
        serializer = new DefaultJsonSerializer();
        writer = new ConfigurationWriterImpl(serializer);
    }

    @Test
    public void testSaveConfiguration() throws Exception {
        File dir = Files.createTempDir();
        File f = new File(dir, "config.json");
        assertThat(f.exists(), is(false));
        ServerConfiguration config = new ConfigurationBuilder().createServerConfiguration();
        writer.saveConfiguration(config, f);
        assertThat(f.exists(), is(true));

        ServerConfiguration parsedConfig = serializer.parse(f, ServerConfiguration.class);
        assertThat(parsedConfig, is(config));
    }
}