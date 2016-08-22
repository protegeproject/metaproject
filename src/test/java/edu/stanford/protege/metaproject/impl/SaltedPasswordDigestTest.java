package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.PasswordHasher;
import edu.stanford.protege.metaproject.api.PlainPassword;
import edu.stanford.protege.metaproject.api.Salt;
import edu.stanford.protege.metaproject.api.SaltedPasswordDigest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.security.SecureRandom;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@RunWith(MockitoJUnitRunner.class)
public class SaltedPasswordDigestTest {
    private static final String toStringHead = SaltedPasswordDigest.class.getSimpleName();

    private static final PasswordHasher passwordHasher = TestUtils.getPasswordHasher();

    @Mock private Salt salt, diffSalt;
    @Mock private PlainPassword plain, diffPlain;

    private SaltedPasswordDigest password, otherPassword, diffPassword;

    @Before
    public void setUp() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        when(salt.getBytes()).thenReturn(bytes);

        byte[] bytes2 = new byte[16];
        new SecureRandom().nextBytes(bytes2);
        when(diffSalt.getBytes()).thenReturn(bytes2);

        when(plain.getPassword()).thenReturn("testPassword");
        when(diffPlain.getPassword()).thenReturn("diffTestPassword");

        password = passwordHasher.hash(plain, salt);
        otherPassword = passwordHasher.hash(plain, salt);
        diffPassword = passwordHasher.hash(diffPlain, diffSalt);
    }

    @Test
    public void testNotNull() {
        assertThat(password, is(not(equalTo(null))));
    }

    @Test
    public void testGetPassword() {
        SaltedPasswordDigest origPassword = passwordHasher.hash(plain, salt);
        assertThat(password.getPassword().equals(origPassword.getPassword()), is(true));

        SaltedPasswordDigest origPasswordWrong = passwordHasher.hash(diffPlain, salt);
        assertThat(password.getPassword().equals(origPasswordWrong.getPassword()), is(false));
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