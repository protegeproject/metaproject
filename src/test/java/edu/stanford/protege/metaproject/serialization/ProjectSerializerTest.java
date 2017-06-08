package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ProjectSerializerTest {
    private static final String projectIdStr = "testProjectId1", diffIdStr = "testProjectId2";
    private static final ProjectId projectId = TestUtils.getProjectId(projectIdStr), diffProjectId = TestUtils.getProjectId(diffIdStr);
    private static final Name projectName = TestUtils.getName();
    private static final Description projectDescription = TestUtils.getDescription();
    private static final String projectFile = TestUtils.getFile();
    private static final UserId owner = TestUtils.getUserId();
    private static final ProjectOptions projectOptions = TestUtils.getProjectOptions();

    private String jsonProject, jsonOtherProject, jsonDiffProject;
    private Project project, otherProject, diffProject;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getGson();

        project = TestUtils.getProject(projectId, projectName, projectDescription, owner,
                Optional.of(projectOptions));
        otherProject = TestUtils.getProject(projectId, projectName, projectDescription, owner,
                Optional.of(projectOptions));
        diffProject = TestUtils.getProject(diffProjectId, projectName, projectDescription, owner,
                Optional.of(projectOptions));

        jsonProject = gson.toJson(project, Project.class);
        jsonOtherProject = gson.toJson(otherProject, Project.class);
        jsonDiffProject = gson.toJson(diffProject, Project.class);
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
    public void testGetOwner() {
        assertThat(gson.fromJson(jsonProject, Project.class).getOwner(), is(owner));
    }

    @Test
    public void testGetOptions() {
        assertThat(gson.fromJson(jsonProject, Project.class).getOptions(), is(Optional.of(projectOptions)));
    }
}