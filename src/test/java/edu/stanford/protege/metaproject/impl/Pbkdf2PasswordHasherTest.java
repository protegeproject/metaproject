package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.TestUtils;

import edu.stanford.protege.metaproject.api.*;
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
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@RunWith(MockitoJUnitRunner.class)
public class Pbkdf2PasswordHasherTest {
    private static final String toStringHead = Pbkdf2PasswordHasher.class.getSimpleName();
    private static final int hashByteSize = 16, nrIterations = 1500;
    private static final SaltGenerator saltGenerator = TestUtils.getSaltGenerator();

    @Mock private PlainPassword testPassword;

    private PasswordHasher passwordHasher, otherPasswordHasher, diffPasswordHasher;

    @Before
    public void setUp() {
        passwordHasher = TestUtils.getPasswordHasher(hashByteSize, nrIterations);
        otherPasswordHasher = TestUtils.getPasswordHasher(hashByteSize, nrIterations);
        diffPasswordHasher = TestUtils.getPasswordHasher();

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
