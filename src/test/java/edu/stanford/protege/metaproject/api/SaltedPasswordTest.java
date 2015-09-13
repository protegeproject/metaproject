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
public class SaltedPasswordTest {
    private static final String
            passwordStr = "testPassword1",
            diffPasswordStr = "testPassword2",
            toStringHead = "SaltedPassword";
    PasswordHandler passwordHandler = Utils.getPasswordMaster();
    private static final Salt salt = Utils.getSalt(), diffSalt = Utils.getSalt();

    private SaltedPassword password, otherPassword, diffPassword;

    @Before
    public void setUp() {
        password = Utils.getSaltedPassword(passwordStr, salt);
        otherPassword = Utils.getSaltedPassword(passwordStr, salt);
        diffPassword = Utils.getSaltedPassword(diffPasswordStr, diffSalt);
    }

    @Test
    public void testNotNull() {
        assertThat(password, is(not(equalTo(null))));
    }

    @Test
    public void testGetPassword() {
        assertThat(passwordHandler.validatePassword(Utils.getPlainPassword(passwordStr), password), is(true));
    }

    @Test
    public void testGetSalt() {
        assertThat(password.getSalt(), is(salt));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(password, is(password));
    }

    @Test
    public void testEquals() {
        assertThat(password, is(otherPassword));
    }

    @Test
    public void testNotEquals() {
        assertThat(password, is(not(diffPassword)));
    }

    @Test
    public void testHashCode() {
        assertThat(password.hashCode(), is(otherPassword.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(password.toString(), startsWith(toStringHead));
    }
}