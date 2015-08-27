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
public class UserTest {
    private static final String
            userIdStr = "testUserId1",
            otherIdStr = "testUserId2",
            userNameStr = "testUserName",
            userEmailStr = "test@EmailAddress",
            toStringHead = "User";

    private static final UserId userId = TestUtils.getUserId(userIdStr), diffUserId = TestUtils.getUserId(otherIdStr);
    private static final Name userName = TestUtils.getName(userNameStr);
    private static final Address userEmail = TestUtils.getAddress(userEmailStr);

    private User user, otherUser, diffUser;

    @Before
    public void setUp() {
        user = TestUtils.getUser(userId, userName, userEmail);
        otherUser = TestUtils.getUser(userId, userName, userEmail);
        diffUser = TestUtils.getUser(diffUserId, userName, userEmail);
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
        assertThat(user.getAddress().get(), is(userEmailStr));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(user, is(equalTo(user)));
    }

    @Test
    public void testEquals() {
        assertThat(user, is(equalTo(otherUser)));
    }

    @Test
    public void testNotEquals() {
        assertThat(user, is(not(equalTo(diffUser))));
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