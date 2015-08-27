package edu.stanford.protege.metaproject.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectTest {
    private static final String
            projectIdStr = "testProjectId1",
            otherIdStr = "testProjectId2",
            projectNameStr = "test project name",
            projectDescriptionStr = "test project description",
            toStringHead = "Project";

    private static final ProjectId projectId = TestUtils.getProjectId(projectIdStr), diffProjectId = TestUtils.getProjectId(otherIdStr);
    private static final Name projectName = TestUtils.getName(projectNameStr);
    private static final Description projectDescription = TestUtils.getDescription(projectDescriptionStr);
    private static final Address projectLocation = TestUtils.getAddress("/Users/test/folder/project.owl");
    private static final UserId ownerId = TestUtils.getUserId("owner");
    private static final UserId adminId = TestUtils.getUserId("admin1");
    private static Set<UserId> admins = TestUtils.getUserIdSet(adminId);

    private Project project, otherProject, diffProject;

    @Before
    public void setUp() {
        project = TestUtils.getProject(projectId, projectName, projectDescription, projectLocation, ownerId, admins);
        otherProject = TestUtils.getProject(projectId, projectName, projectDescription, projectLocation, ownerId, admins);
        diffProject = TestUtils.getProject(diffProjectId, projectName, projectDescription, projectLocation, ownerId, admins);
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
    public void testGetAddress() {
        assertThat(project.getAddress(), is(projectLocation));
    }

    @Test
    public void testGetOwner() {
        assertThat(project.getOwner(), is(ownerId));
    }

    @Test
    public void testGetAdministrators() {
        assertThat(project.getAdministrators(), is(admins));
    }

    @Test
    public void testHasAdministrator() {
        assertThat(project.hasAdministrator(adminId), is(true));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(project, is(equalTo(project)));
    }

    @Test
    public void testEquals() {
        assertThat(project, is(equalTo(otherProject)));
    }

    @Test
    public void testNotEquals() {
        assertThat(project, is(not(equalTo(diffProject))));
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