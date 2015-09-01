package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.Project;
import edu.stanford.protege.metaproject.api.ProjectManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectManagerSerializerTest {
    private static final Set<Project> projectSet = Utils.getProjectSet(5), diffProjectSet = Utils.getProjectSet(3);

    private String jsonProjectManager, jsonOtherProjectManager, jsonDiffProjectManager;
    private ProjectManager projectManager, otherProjectManager, diffProjectManager;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new SimpleGsonSerializer().getDefaultSerializer();

        projectManager = Utils.getProjectManager(projectSet);
        otherProjectManager = Utils.getProjectManager(projectSet);
        diffProjectManager = Utils.getProjectManager(diffProjectSet);

        jsonProjectManager = gson.toJson(projectManager, ProjectManager.class);
        jsonOtherProjectManager = gson.toJson(otherProjectManager, ProjectManager.class);
        jsonDiffProjectManager = gson.toJson(diffProjectManager, ProjectManager.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonProjectManager, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonProjectManager, ProjectManager.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(projectManager, is(gson.fromJson(jsonProjectManager, ProjectManager.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(projectManager, is(otherProjectManager));
        assertThat(jsonProjectManager, is(jsonOtherProjectManager));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(projectManager, is(not(diffProjectManager)));
        assertThat(jsonProjectManager, is(not(gson.toJson(diffProjectManager, ProjectManager.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonProjectManager, ProjectManager.class), is(gson.fromJson(jsonOtherProjectManager, ProjectManager.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonProjectManager, ProjectManager.class), is(not(gson.fromJson(jsonDiffProjectManager, ProjectManager.class))));
    }

    @Test
    public void testGetProjectsNotNull() {
        assertThat(gson.fromJson(jsonProjectManager, ProjectManager.class).getProjects(), is(not(equalTo(null))));
    }

    @Test
    public void testGetProjects() {
        assertThat(gson.fromJson(jsonProjectManager, ProjectManager.class).getProjects(), is(projectSet));
    }
}