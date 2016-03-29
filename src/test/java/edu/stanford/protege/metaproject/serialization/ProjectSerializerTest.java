package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectSerializerTest {
    private static final String projectIdStr = "testProjectId1", diffIdStr = "testProjectId2";
    private static final ProjectId projectId = Utils.getProjectId(projectIdStr), diffProjectId = Utils.getProjectId(diffIdStr);
    private static final Name projectName = Utils.getName();
    private static final Description projectDescription = Utils.getDescription();
    private static final Address projectAddress = Utils.getAddress();
    private static final UserId owner = Utils.getUserId();
    private static final ProjectOptions projectOptions = Utils.getProjectOptions();

    private String jsonProject, jsonOtherProject, jsonDiffProject;
    private Project project, otherProject, diffProject;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        project = Utils.getProject(projectId, projectName, projectDescription, projectAddress, owner,
                Optional.of(projectOptions));
        otherProject = Utils.getProject(projectId, projectName, projectDescription, projectAddress, owner,
                Optional.of(projectOptions));
        diffProject = Utils.getProject(diffProjectId, projectName, projectDescription, projectAddress, owner,
                Optional.of(projectOptions));

        jsonProject = gson.toJson(project);
        jsonOtherProject = gson.toJson(otherProject);
        jsonDiffProject = gson.toJson(diffProject);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonProject, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonProject, Project.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(project, is(gson.fromJson(jsonProject, Project.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(project, is(otherProject));
        assertThat(jsonProject, is(jsonOtherProject));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(project, is(not(diffProject)));
        assertThat(jsonProject, is(not(gson.toJson(diffProject))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonProject, Project.class), is(gson.fromJson(jsonOtherProject, Project.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonProject, Project.class), is(not(gson.fromJson(jsonDiffProject, Project.class))));
    }

    @Test
    public void testGetId() {
        assertThat(gson.fromJson(jsonProject, Project.class).getId(), is(projectId));
    }

    @Test
    public void testGetName() {
        assertThat(gson.fromJson(jsonProject, Project.class).getName(), is(projectName));
    }

    @Test
    public void testGetDescription() {
        assertThat(gson.fromJson(jsonProject, Project.class).getDescription(), is(projectDescription));
    }

    @Test
    public void testGetAddress() {
        assertThat(gson.fromJson(jsonProject, Project.class).getAddress(), is(projectAddress));
    }

    @Test
    public void testGetOwner() {
        assertThat(gson.fromJson(jsonProject, Project.class).getOwner(), is(owner));
    }

    @Test
    public void testGetOptions() {
        assertThat(gson.fromJson(jsonProject, Project.class).getOptions(), is(Optional.of(projectOptions)));
    }
}