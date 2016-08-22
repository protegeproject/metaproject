package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.EmailAddress;
import edu.stanford.protege.metaproject.api.Name;
import edu.stanford.protege.metaproject.api.User;
import edu.stanford.protege.metaproject.api.UserId;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class UserTest {
    private static final String
            userIdStr = "testUserId1",
            otherIdStr = "testUserId2",
            userNameStr = "testUserName",
            otherUserNameStr = "testUserName2",
            userEmailStr = "test@EmailAddress",
            toStringHead = User.class.getSimpleName();

    private static final UserId userId = TestUtils.getUserId(userIdStr), diffUserId = TestUtils.getUserId(otherIdStr);
    private static final Name userName = TestUtils.getName(userNameStr), diffUserName = TestUtils.getName(otherUserNameStr);
    private static final EmailAddress userEmail = TestUtils.getEmailAddress(userEmailStr);

    private User user, otherUser, diffUser;

    @Before
    public void setUp() {
        user = TestUtils.getUser(userId, userName, userEmail);
        otherUser = TestUtils.getUser(userId, userName, userEmail);
        diffUser = TestUtils.getUser(diffUserId, diffUserName, userEmail);
    }

    @Test
    public void testNotNull() {
        assertThat(user, is(not(equalTo(null))));
    }

    @Test
    public void testGetId() {
        assertThat(user.getId().get(), is(userIdStr));
    }

    @Test
    public void testGetName() {
        assertThat(user.getName().get(), is(userNameStr));
    }

    @Test
    public void testGetEmailAddress() {
        assertThat(user.getEmailAddress().get(), is(userEmailStr));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(user, is(user));
    }

    @Test
    public void testEquals() {
        assertThat(user, is(otherUser));
    }

    @Test
    public void testNotEquals() {
        assertThat(user, is(not(diffUser)));
    }

    @Test
    public void testHashCode() {
        assertThat(user.hashCode(), is(otherUser.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(user.toString(), startsWith(toStringHead));
    }

    @Test
    public void testCompareToSelf() {
        assertThat(user.compareTo(user), is(0));
    }

    @Test
    public void testCompareTo() {
        assertThat(user.compareTo(otherUser), is(0));
    }

    @Test
    public void testCompareToAnother() {
        assertThat(user.compareTo(diffUser), is(-1));
    }

    @Test
    public void testCompareToAnotherReversed() {
        assertThat(diffUser.compareTo(user), is(1));
    }
}