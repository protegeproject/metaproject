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
public class UserIdTest {
    private static final String
            userIdStr = "testUserId1",
            diffIdStr = "testUserId2",
            toStringHead = "UserId";

    private UserId userId, otherUserId, diffUserId;

    @Before
    public void setUp() {
        userId = TestUtils.getUserId(userIdStr);
        otherUserId = TestUtils.getUserId(userIdStr);
        diffUserId = TestUtils.getUserId(diffIdStr);
    }

    @Test
    public void testNotNull() {
        assertThat(userId, is(not(equalTo(null))));
    }

    @Test
    public void testGet() {
        assertThat(userId.get(), is(userIdStr));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(userId, is(equalTo(userId)));
    }

    @Test
    public void testEquals() {
        assertThat(userId, is(equalTo(otherUserId)));
    }

    @Test
    public void testNotEquals() {
        assertThat(userId, is(not(equalTo(diffUserId))));
    }

    @Test
    public void testHashCode() {
        assertThat(userId.hashCode(), is(otherUserId.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(userId.toString(), startsWith(toStringHead));
    }
}