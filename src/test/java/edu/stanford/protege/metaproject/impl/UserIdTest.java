package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.UserId;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserIdTest {
    private static final String
            userIdStr = "testUserId1",
            diffIdStr = "testUserId2",
            toStringHead = UserId.class.getSimpleName();

    private UserId userId, otherUserId, diffUserId;

    @Before
    public void setUp() {
        userId = Utils.getUserId(userIdStr);
        otherUserId = Utils.getUserId(userIdStr);
        diffUserId = Utils.getUserId(diffIdStr);
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
        assertThat(userId, is(userId));
    }

    @Test
    public void testEquals() {
        assertThat(userId, is(otherUserId));
    }

    @Test
    public void testNotEquals() {
        assertThat(userId, is(not(diffUserId)));
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