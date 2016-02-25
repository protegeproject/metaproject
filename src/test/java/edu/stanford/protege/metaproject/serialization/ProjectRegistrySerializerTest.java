package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.Project;
import edu.stanford.protege.metaproject.api.ProjectRegistry;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectRegistrySerializerTest {
    private static final Set<Project> projectSet = Utils.getProjectSet(5), diffProjectSet = Utils.getProjectSet(3);

    private String jsonProjectManager, jsonOtherProjectManager, jsonDiffProjectManager;
    private ProjectRegistry projectRegistry, otherProjectRegistry, diffProjectRegistry;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        projectRegistry = Utils.getProjectManager(projectSet);
        otherProjectRegistry = Utils.getProjectManager(projectSet);
        diffProjectRegistry = Utils.getProjectManager(diffProjectSet);

        jsonProjectManager = gson.toJson(projectRegistry, ProjectRegistry.class);
        jsonOtherProjectManager = gson.toJson(otherProjectRegistry, ProjectRegistry.class);
        jsonDiffProjectManager = gson.toJson(diffProjectRegistry, ProjectRegistry.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonProjectManager, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonProjectManager, ProjectRegistry.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(projectRegistry, is(gson.fromJson(jsonProjectManager, ProjectRegistry.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(projectRegistry, is(otherProjectRegistry));
        assertThat(jsonProjectManager, is(jsonOtherProjectManager));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(projectRegistry, is(not(diffProjectRegistry)));
        assertThat(jsonProjectManager, is(not(gson.toJson(diffProjectRegistry, ProjectRegistry.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonProjectManager, ProjectRegistry.class), is(gson.fromJson(jsonOtherProjectManager, ProjectRegistry.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonProjectManager, ProjectRegistry.class), is(not(gson.fromJson(jsonDiffProjectManager, ProjectRegistry.class))));
    }

    @Test
    public void testGetProjectsNotNull() {
        assertThat(gson.fromJson(jsonProjectManager, ProjectRegistry.class).getProjects(), is(not(equalTo(null))));
    }

    @Test
    public void testGetProjects() {
        assertThat(gson.fromJson(jsonProjectManager, ProjectRegistry.class).getProjects(), is(projectSet));
    }
}