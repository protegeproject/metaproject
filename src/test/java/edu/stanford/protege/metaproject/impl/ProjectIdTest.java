package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.ProjectId;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ProjectIdTest {
    private static final String
            projectIdStr = "testProjectId1",
            diffIdStr = "testProjectId2",
            toStringHead = ProjectId.class.getSimpleName();

    private ProjectId projectId, otherProjectId, diffProjectId;

    @Before
    public void setUp() {
        projectId = TestUtils.getProjectId(projectIdStr);
        otherProjectId = TestUtils.getProjectId(projectIdStr);
        diffProjectId = TestUtils.getProjectId(diffIdStr);
    }

    @Test
    public void testNotNull() {
        assertThat(projectId, is(not(equalTo(null))));
    }

    @Test
    public void testGet() {
        assertThat(projectId.get(), is(projectIdStr));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(projectId, is(projectId));
    }

    @Test
    public void testEquals() {
        assertThat(projectId, is(otherProjectId));
    }

    @Test
    public void testNotEquals() {
        assertThat(projectId, is(not(diffProjectId)));
    }

    @Test
    public void testHashCode() {
        assertThat(projectId.hashCode(), is(otherProjectId.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(projectId.toString(), startsWith(toStringHead));
    }
}