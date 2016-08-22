package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ProjectTest {
    private static final String
            projectIdStr = "testProjectId1",
            otherIdStr = "testProjectId2",
            projectNameStr = "test project name",
            otherProjectNameStr = "test project name 2",
            projectDescriptionStr = "test project description",
            toStringHead = Project.class.getSimpleName();

    private static final ProjectId projectId = TestUtils.getProjectId(projectIdStr), diffProjectId = TestUtils.getProjectId(otherIdStr);
    private static final Name projectName = TestUtils.getName(projectNameStr), diffProjectName = TestUtils.getName(otherProjectNameStr);
    private static final Description projectDescription = TestUtils.getDescription(projectDescriptionStr);
    private static final File projectFile = TestUtils.getFile("/Users/test/folder/project.owl");
    private static final UserId ownerId = TestUtils.getUserId("owner");
    @Mock private ProjectOptions projectOptions;

    private Project project, otherProject, diffProject;

    @Before
    public void setUp() {
        project = TestUtils.getProject(projectId, projectName, projectDescription, projectFile, ownerId, Optional.ofNullable(projectOptions));
        otherProject = TestUtils.getProject(projectId, projectName, projectDescription, projectFile, ownerId, Optional.ofNullable(projectOptions));
        diffProject = TestUtils.getProject(diffProjectId, diffProjectName, projectDescription, projectFile, ownerId, Optional.ofNullable(projectOptions));
    }

    @Test
    public void testNotNull() {
        assertThat(project, is(not(equalTo(null))));
    }

    @Test
    public void testGetId() {
        assertThat(project.getId().get(), is(projectIdStr));
    }

    @Test
    public void testGetName() {
        assertThat(project.getName().get(), is(projectNameStr));
    }

    @Test
    public void testGetDescription() {
        assertThat(project.getDescription().get(), is(projectDescriptionStr));
    }

    @Test
    public void testGetFile() {
        assertThat(project.getFile(), is(projectFile));
    }

    @Test
    public void testGetOwner() {
        assertThat(project.getOwner(), is(ownerId));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(project, is(project));
    }

    @Test
    public void testEquals() {
        assertThat(project, is(otherProject));
    }

    @Test
    public void testNotEquals() {
        assertThat(project, is(not(diffProject)));
    }

    @Test
    public void testHashCode() {
        assertThat(project.hashCode(), is(otherProject.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(project.toString(), startsWith(toStringHead));
    }

    @Test
    public void testCompareToSelf() {
        assertThat(project.compareTo(project), is(0));
    }

    @Test
    public void testCompareTo() {
        assertThat(project.compareTo(otherProject), is(0));
    }

    @Test
    public void testCompareToAnother() {
        assertThat(project.compareTo(diffProject), is(-1));
    }

    @Test
    public void testCompareToAnotherReversed() {
        assertThat(diffProject.compareTo(project), is(1));
    }
}