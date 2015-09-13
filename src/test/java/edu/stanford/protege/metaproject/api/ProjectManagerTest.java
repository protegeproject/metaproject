package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.exception.UnknownAccessControlObjectIdException;
import edu.stanford.protege.metaproject.api.exception.UnknownProjectIdException;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectManagerTest {
    private static final Project project1 = Utils.getProject(), project2 = Utils.getProject(), project3 = Utils.getProject(), project4 = Utils.getProject();
    private static final Set<Project> projectSet = Utils.getProjectSet(project1, project2, project3, project4);
    private static final String toStringHead = "ProjectManager";

    private ProjectManager projectManager, otherProjectManager, diffProjectManager;

    @Before
    public void setUp() {
        projectManager = Utils.getProjectManager(projectSet);
        otherProjectManager = Utils.getProjectManager(projectSet);
        diffProjectManager = Utils.getProjectManager();
    }

    @Test
    public void testNotNull() {
        assertThat(projectManager, is(not(equalTo(null))));
    }

    @Test
    public void testGetProjects() {
        assertThat(projectManager.getProjects(), is(projectSet));
    }

    @Test
    public void testGetProjectById() throws UnknownProjectIdException {
        assertThat(projectManager.getProject(project1.getId()), is(project1));
    }

    @Test
    public void testRemoveProject() throws UnknownAccessControlObjectIdException {
        assertThat(projectManager.getProjects().contains(project4), is(true));
        projectManager.remove(project4);
        assertThat(projectManager.getProjects().contains(project4), is(false));
    }

    @Test
    public void testAddProject() throws UnknownProjectIdException {
        Project project5 = Utils.getProject();
        assertThat(projectManager.getProjects().contains(project5), is(false));

        projectManager.add(project5);
        assertThat(projectManager.getProjects().contains(project5), is(true));
        assertThat(projectManager.getProject(project5.getId()), is(project5));
    }

    @Test
    public void testChangeDescription() throws UnknownProjectIdException {
        Description newDescription = Utils.getDescription("new test description");
        projectManager.changeDescription(project2.getId(), newDescription);
        assertThat(projectManager.getProject(project2.getId()).getDescription(), is(newDescription));
    }

    @Test
    public void testChangeName() throws UnknownProjectIdException {
        Name newName = Utils.getName("new test name");
        projectManager.changeName(project2.getId(), newName);
        assertThat(projectManager.getProject(project2.getId()).getName(), is(newName));
    }

    @Test
    public void testChangeAddress() throws UnknownProjectIdException {
        Address newAddress = Utils.getAddress("new test address");
        projectManager.changeAddress(project2.getId(), newAddress);
        assertThat(projectManager.getProject(project2.getId()).getAddress(), is(newAddress));
    }

    @Test
    public void testChangeOwner() throws UnknownProjectIdException {
        UserId newOwner = Utils.getUserId();
        projectManager.changeOwner(project2.getId(), newOwner);
        assertThat(projectManager.getProject(project2.getId()).getOwner(), is(newOwner));
    }

    @Test
    public void testExists() {
        ProjectId projectId = Utils.getProjectId("newTestProjectId");
        assertThat(projectManager.contains(project1.getId()), is(true));
        assertThat(projectManager.contains(projectId), is(false));
    }

    @Test
    public void testAddAdministrator() throws UnknownProjectIdException {
        UserId newAdmin = Utils.getUserId();
        assertThat(projectManager.getProject(project2.getId()).getAdministrators().contains(newAdmin), is(false));

        projectManager.addAdministrator(project2.getId(), newAdmin);
        assertThat(projectManager.getProject(project2.getId()).getAdministrators().contains(newAdmin), is(true));
    }

    @Test
    public void testRemoveAdministrator() throws UnknownProjectIdException {
        UserId userId = project2.getAdministrators().iterator().next();
        projectManager.removeAdministrator(project2.getId(), userId);
        assertThat(projectManager.getProject(project2.getId()).getAdministrators().contains(userId), is(false));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(projectManager, is(projectManager));
    }

    @Test
    public void testEquals() {
        assertThat(projectManager, is(otherProjectManager));
    }

    @Test
    public void testNotEquals() {
        assertThat(projectManager, is(not(diffProjectManager)));
    }

    @Test
    public void testHashCode() {
        assertThat(projectManager.hashCode(), is(otherProjectManager.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(projectManager.toString(), startsWith(toStringHead));
    }
}
