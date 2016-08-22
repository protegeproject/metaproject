package edu.stanford.protege.metaproject.serialization;

import com.google.common.io.Files;
import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.Project;
import edu.stanford.protege.metaproject.api.ServerConfiguration;
import edu.stanford.protege.metaproject.impl.ConfigurationBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class DefaultJsonSerializerTest {
    private DefaultJsonSerializer serializer;

    @Before
    public void setUp() throws Exception {
        serializer = new DefaultJsonSerializer();
    }

    @Test
    public void getGson() throws Exception {
        assertThat(serializer.getGson(), is(not(equalTo(null))));
    }

    @Test
    public void parse() throws Exception {
        File dir = Files.createTempDir();
        File f = new File(dir, "config.json");
        ServerConfiguration config = new ConfigurationBuilder().createServerConfiguration();
        String configStr = serializer.getGson().toJson(config, ServerConfiguration.class);
        FileWriter writer = new FileWriter(f);
        writer.append(configStr);
        writer.close();

        ServerConfiguration parsedConfig = serializer.parse(f, ServerConfiguration.class);
        assertThat(parsedConfig, is(not(equalTo(null))));
        assertThat(parsedConfig, is(config));
    }

    @Test
    public void parseFromReader() throws Exception {
        File dir = Files.createTempDir();
        File f = new File(dir, "config.json");
        ServerConfiguration config = new ConfigurationBuilder().createServerConfiguration();
        String configStr = serializer.getGson().toJson(config, ServerConfiguration.class);
        FileWriter writer = new FileWriter(f);
        writer.append(configStr);
        writer.close();

        ServerConfiguration parsedConfig = serializer.parse(new FileReader(f), ServerConfiguration.class);
        assertThat(parsedConfig, is(not(equalTo(null))));
        assertThat(parsedConfig, is(config));
    }

    @Test
    public void write() throws Exception {
        Project project = TestUtils.getProject();
        assertThat(serializer.write(project, Project.class), is(not(equalTo(null))));
        assertThat(serializer.write(project, Project.class), is(serializer.getGson().toJson(project, Project.class)));
    }
}