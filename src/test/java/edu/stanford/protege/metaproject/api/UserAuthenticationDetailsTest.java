package edu.stanford.protege.metaproject.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
@RunWith(MockitoJUnitRunner.class)
public class UserAuthenticationDetailsTest {
    private static final String
            userIdStr = "testUserId1",
            diffUserIdStr = "testUserId2",
            passwordStr = "testPassword",
            saltStr = "testSalt",
            toStringHead = "UserAuthenticationDetails";

    private static final UserId userId = TestUtils.getUserId(userIdStr), diffUserId = TestUtils.getUserId(diffUserIdStr);
    private static final Salt salt = TestUtils.getSalt(saltStr.getBytes());
    private static final SaltedPassword password = TestUtils.getSaltedPassword(passwordStr, salt);

    private UserAuthenticationDetails userDetails, otherUserDetails, diffUserDetails;

    @Before
    public void setUp() {
        userDetails = TestUtils.getUserAuthenticationDetails(userId, password, Optional.of(salt));
        otherUserDetails = TestUtils.getUserAuthenticationDetails(userId, password, Optional.of(salt));
        diffUserDetails = TestUtils.getUserAuthenticationDetails(diffUserId, password, Optional.of(salt));
    }

    @Test
    public void testNotNull() {
        assertThat(userDetails, is(not(equalTo(null))));
    }

    @Test
    public void testGetUserId() {
        assertThat(userDetails.getUserId().get(), is(userIdStr));
    }

    @Test
    public void testGetPassword() {
        assertThat(userDetails.getPassword(), is(password));
    }

    @Test
    public void testGetPasswordText() {
        assertThat(userDetails.getPassword().getPassword(), is(passwordStr));
    }

    @Test
    public void testGetSalt() {
        assertThat(userDetails.getSalt().get(), is(salt));
    }

    @Test
    public void testGetSaltText() {
        assertThat(userDetails.getSalt().get().getBytes(), is(saltStr.getBytes()));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(userDetails, is(equalTo(userDetails)));
    }

    @Test
    public void testEquals() {
        assertThat(userDetails, is(equalTo(otherUserDetails)));
    }

    @Test
    public void testNotEquals() {
        assertThat(userDetails, is(not(equalTo(diffUserDetails))));
    }

    @Test
    public void testHashCode() {
        assertThat(userDetails.hashCode(), is(otherUserDetails.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(userDetails.toString(), startsWith(toStringHead));
    }

    @Test
    public void testCompareToSelf() {
        assertThat(userDetails.compareTo(userDetails), is(0));
    }

    @Test
    public void testCompareTo() {
        assertThat(userDetails.compareTo(otherUserDetails), is(0));
    }

    @Test
    public void testCompareToAnother() {
        assertThat(userDetails.compareTo(diffUserDetails), is(-1));
    }

    @Test
    public void testCompareToAnotherReversed() {
        assertThat(diffUserDetails.compareTo(userDetails), is(1));
    }
}