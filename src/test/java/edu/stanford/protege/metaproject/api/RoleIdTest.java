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
public class RoleIdTest {
    private static final String
            roleIdStr = "testRoleId1",
            diffIdStr = "testRoleId2",
            toStringHead = "RoleId";

    private RoleId roleId, otherRoleId, diffRoleId;

    @Before
    public void setUp() {
        roleId = TestUtils.getRoleId(roleIdStr);
        otherRoleId = TestUtils.getRoleId(roleIdStr);
        diffRoleId = TestUtils.getRoleId(diffIdStr);
    }

    @Test
    public void testNotNull() {
        assertThat(roleId, is(not(equalTo(null))));
    }

    @Test
    public void testGet() {
        assertThat(roleId.get(), is(roleIdStr));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(roleId, is(equalTo(roleId)));
    }

    @Test
    public void testEquals() {
        assertThat(roleId, is(equalTo(otherRoleId)));
    }

    @Test
    public void testNotEquals() {
        assertThat(roleId, is(not(equalTo(diffRoleId))));
    }

    @Test
    public void testHashCode() {
        assertThat(roleId.hashCode(), is(otherRoleId.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(roleId.toString(), startsWith(toStringHead));
    }
}