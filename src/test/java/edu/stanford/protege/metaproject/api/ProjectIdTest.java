package edu.stanford.protege.metaproject.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectIdTest {
    private static final String
            projectIdStr = "testProjectId1",
            diffIdStr = "testProjectId2",
            toStringHead = "ProjectId";

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
        assertThat(projectId, is(equalTo(projectId)));
    }

    @Test
    public void testEquals() {
        assertThat(projectId, is(equalTo(otherProjectId)));
    }

    @Test
    public void testNotEquals() {
        assertThat(projectId, is(not(equalTo(diffProjectId))));
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