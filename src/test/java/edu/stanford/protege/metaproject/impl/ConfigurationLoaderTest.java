package edu.stanford.protege.metaproject.impl;

import com.google.common.io.Files;
import edu.stanford.protege.metaproject.ConfigurationManager;
import edu.stanford.protege.metaproject.api.ConfigurationLoader;
import edu.stanford.protege.metaproject.api.ServerConfiguration;
import edu.stanford.protege.metaproject.serialization.DefaultJsonSerializer;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ConfigurationLoaderTest {
    private ConfigurationLoader loader;

    @Before
    public void setUp() throws Exception {
        loader = new ConfigurationLoaderImpl(new DefaultJsonSerializer());
    }

    @Test
    public void testLoadConfigurationFromFile() throws Exception {
        File dir = Files.createTempDir();
        File f = new File(dir, "config.json");
        ServerConfiguration config = new ConfigurationBuilder().createServerConfiguration();
        ConfigurationManager.getConfigurationWriter().saveConfiguration(config, f);
        assertThat(f.exists(), is(true));

        ServerConfiguration parsedConfig = loader.loadConfiguration(f);
        assertThat(parsedConfig, is(not(equalTo(null))));
        assertThat(parsedConfig, is(config));
    }

    @Test
    public void testLoadConfigurationFromReader() throws Exception {
        File dir = Files.createTempDir();
        File f = new File(dir, "config.json");
        ServerConfiguration config = new ConfigurationBuilder().createServerConfiguration();
        ConfigurationManager.getConfigurationWriter().saveConfiguration(config, f);
        assertThat(f.exists(), is(true));

        ServerConfiguration parsedConfig = loader.loadConfiguration(new FileReader(f));
        assertThat(parsedConfig, is(not(equalTo(null))));
        assertThat(parsedConfig, is(config));
    }
}