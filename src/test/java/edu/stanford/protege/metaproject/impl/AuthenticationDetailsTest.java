package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.AuthenticationDetails;
import edu.stanford.protege.metaproject.api.Salt;
import edu.stanford.protege.metaproject.api.SaltedPasswordDigest;
import edu.stanford.protege.metaproject.api.UserId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationDetailsTest {
    private static final String toStringHead = AuthenticationDetails.class.getSimpleName();

    @Mock private UserId userId, diffUserId;
    @Mock private Salt salt;
    @Mock private SaltedPasswordDigest password;

    private AuthenticationDetails userDetails, otherUserDetails, diffUserDetails;

    @Before
    public void setUp() {
        userDetails = Utils.getAuthenticationDetails(userId, password);
        otherUserDetails = Utils.getAuthenticationDetails(userId, password);
        diffUserDetails = Utils.getAuthenticationDetails(diffUserId, password);

        when(userId.get()).thenReturn("user1");
        when(diffUserId.get()).thenReturn("user2");
    }

    @Test
    public void testNotNull() {
        assertThat(userDetails, is(not(equalTo(null))));
    }

    @Test
    public void testGetUserId() {
        assertThat(userDetails.getUserId(), is(userId));
    }

    @Test
    public void testGetPassword() {
        assertThat(userDetails.getPassword(), is(password));
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