package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.exception.UnknownAccessControlObjectIdException;
import edu.stanford.protege.metaproject.api.exception.EmailAddressAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserIdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AuthenticationManagerTest {
    private static final AuthenticationDetails auth1 = Utils.getAuthenticationDetails();
    private static final Set<AuthenticationDetails> authSet = Utils.getAuthenticationDetailsSet(auth1);
    private static final String toStringHead = "AuthenticationManager";
    private static final UserId userId1 = Utils.getUserId(), userId2 = Utils.getUserId(), userId3 = Utils.getUserId();
    private static final PlainPassword passwd1 = Utils.getPlainPassword(), passwd2 = Utils.getPlainPassword(), passwd3 = Utils.getPlainPassword();


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
    public void testAddAuthentication() throws EmailAddressAlreadyInUseException, UserIdAlreadyInUseException, UserNotRegisteredException {
        AuthenticationDetails authentication5 = Utils.getAuthenticationDetails();
        authManager.add(authentication5.getUserId(), Utils.getPlainPassword());
        assertThat(authManager.getAuthenticationDetails(authentication5.getUserId()), is(not(equalTo(null))));
    }

    @Test
    public void testChangePassword() throws UserNotRegisteredException {
        PlainPassword password = Utils.getPlainPassword();
        authManager.changePassword(userId2, password);
        assertThat(authManager.hasValidCredentials(userId2, password), is(true));
    }

    @Test
    public void testChangePasswordBasedOnAuthDetails() throws UserNotRegisteredException {
        PlainPassword password = Utils.getPlainPassword();
        authManager.changePassword(authManager.getAuthenticationDetails(userId2), password);
        assertThat(authManager.hasValidCredentials(userId2, password), is(true));
    }

    @Test
    public void testHasValidCredentials() throws UserNotRegisteredException {
        assertThat(authManager.hasValidCredentials(userId1, passwd1), is(true));
        assertThat(authManager.hasValidCredentials(userId1, Utils.getPlainPassword()), is(false));
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
