package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;

import edu.stanford.protege.metaproject.impl.Pbkdf2PasswordHasher;
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
public class Pbkdf2PasswordHasherTest {
    private static final String toStringHead = Pbkdf2PasswordHasher.class.getSimpleName();
    private static final int hashByteSize = 16, nrIterations = 1500;
    private static final SaltGenerator saltGenerator = Utils.getSaltGenerator();

    @Mock private PlainPassword testPassword;

    private PasswordHasher passwordHasher, otherPasswordHasher, diffPasswordHasher;

    @Before
    public void setUp() {
        passwordHasher = Utils.getPasswordHasher(hashByteSize, nrIterations);
        otherPasswordHasher = Utils.getPasswordHasher(hashByteSize, nrIterations);
        diffPasswordHasher = Utils.getPasswordHasher();

        when(testPassword.getPassword()).thenReturn("testPassword");
    }

    @Test
    public void testNotNull() {
        assertThat(passwordHasher, is(not(equalTo(null))));
    }

    @Test
    public void testHash() {
        Salt salt = saltGenerator.generate();
        SaltedPasswordDigest hashedPassword = passwordHasher.hash(testPassword, salt);
        assertThat(hashedPassword, is(not(equalTo(null))));
        assertThat(hashedPassword.getPassword(), is(not(equalTo(null))));
        assertThat(hashedPassword, is(passwordHasher.hash(testPassword, salt)));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(passwordHasher, is(passwordHasher));
    }

    @Test
    public void testEquals() {
        assertThat(passwordHasher, is(otherPasswordHasher));
    }

    @Test
    public void testNotEquals() {
        assertThat(passwordHasher, is(not(diffPasswordHasher)));
    }

    @Test
    public void testHashcode() {
        assertThat(passwordHasher.hashCode(), is(otherPasswordHasher.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(passwordHasher.toString(), startsWith(toStringHead));
    }
}
