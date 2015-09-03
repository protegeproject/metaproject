package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AuthenticationDetailsTest {
    private static final String
            userIdStr = "testUserId1",
            diffUserIdStr = "testUserId2",
            passwordStr = "testPassword",
            toStringHead = "AuthenticationDetails";

    private static final UserId userId = Utils.getUserId(userIdStr), diffUserId = Utils.getUserId(diffUserIdStr);
    private static final SaltedPassword password = Utils.getSaltedPassword(passwordStr, Utils.getSalt());

    private AuthenticationDetails userDetails, otherUserDetails, diffUserDetails;

    @Before
    public void setUp() {
        userDetails = Utils.getAuthenticationDetails(userId, password);
        otherUserDetails = Utils.getAuthenticationDetails(userId, password);
        diffUserDetails = Utils.getAuthenticationDetails(diffUserId, password);
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
    public void testEqualToSelf() {
        assertThat(userDetails, is(userDetails));
    }

    @Test
    public void testEquals() {
        assertThat(userDetails, is(otherUserDetails));
    }

    @Test
    public void testNotEquals() {
        assertThat(userDetails, is(not(diffUserDetails)));
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