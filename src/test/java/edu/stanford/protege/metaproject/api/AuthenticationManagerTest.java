package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationManagerTest {
    private static final String toStringHead = AuthenticationManager.class.getSimpleName();

    private static final AuthenticationDetails auth1 = Utils.getAuthenticationDetails();
    private static final Set<AuthenticationDetails> authSet = Utils.getAuthenticationDetailsSet(auth1);

    @Mock private UserId userId1, userId2, userId3;
    @Mock private SaltedPasswordDigest passwd1, passwd2, passwd3;

    private AuthenticationManager authManager, otherAuthManager, diffAuthManager;

    @Before
    public void setUp() throws EmailAddressAlreadyInUseException, UserIdAlreadyInUseException {
        authManager = Utils.getAuthenticationManager();
        authManager.add(userId1, passwd1);
        authManager.add(userId2, passwd2);
        authManager.add(userId3, passwd3);

        otherAuthManager = Utils.getAuthenticationManager();
        otherAuthManager.add(userId1, passwd1);
        otherAuthManager.add(userId2, passwd2);
        otherAuthManager.add(userId3, passwd3);

        diffAuthManager = Utils.getAuthenticationManager(authSet);
    }

    @Test
    public void testNotNull() {
        assertThat(authManager, is(not(equalTo(null))));
    }

    @Test
    public void testGetAuthenticationDetails() {
        assertThat(diffAuthManager.getAuthenticationDetails(), is(authSet));
    }

    @Test
    public void testGetAuthenticationDetailsById() throws UserNotRegisteredException {
        assertThat(diffAuthManager.getAuthenticationDetails(auth1.getUserId()), is(auth1));
    }

    @Test
    public void testAddAuthentication() throws EmailAddressAlreadyInUseException, UserIdAlreadyInUseException, UserNotRegisteredException {
        AuthenticationDetails authentication5 = Utils.getAuthenticationDetails();
        authManager.add(authentication5.getUserId(), authentication5.getPassword());
        assertThat(authManager.getAuthenticationDetails(authentication5.getUserId()), is(authentication5));
    }

    @Test
    public void testRemoveAuthentication() throws UnknownAccessControlObjectIdException, UserNotRegisteredException {
        assertThat(authManager.contains(userId3), is(true));
        authManager.remove(userId3);
        assertThat(authManager.contains(userId3), is(false));
    }

    @Test(expected=UserNotRegisteredException.class)
    public void testGetAuthenticationException() throws EmailAddressAlreadyInUseException, UserIdAlreadyInUseException, UserNotRegisteredException {
        AuthenticationDetails authentication5 = Utils.getAuthenticationDetails();
        authManager.getAuthenticationDetails(authentication5.getUserId());
    }

    @Test
    public void testChangePassword() throws UserNotRegisteredException {
        SaltedPasswordDigest password = Utils.getSaltedPassword();
        authManager.changePassword(userId2, password);
        assertThat(authManager.hasValidCredentials(userId2, password), is(true));
    }

    @Test
    public void testHasValidCredentials() throws UserNotRegisteredException {
        when(passwd1.getPassword()).thenReturn("password");
        assertThat(authManager.hasValidCredentials(userId1, passwd1), is(true));

        when(passwd2.getPassword()).thenReturn("diffPassword");
        assertThat(authManager.hasValidCredentials(userId1, passwd2), is(false));
    }

    @Test
    public void testContains() {
        AuthenticationDetails details = Utils.getAuthenticationDetails();
        assertThat(authManager.contains(userId1), is(true));
        assertThat(authManager.contains(details.getUserId()), is(false));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(authManager, is(authManager));
    }

    @Test
    public void testNotEquals() {
        assertThat(authManager, is(not(diffAuthManager)));
        assertThat(authManager, is(not(otherAuthManager)));
    }

    @Test
    public void testToString() {
        assertThat(authManager.toString(), startsWith(toStringHead));
    }
}
