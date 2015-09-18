package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;

import edu.stanford.protege.metaproject.api.impl.Pbkdf2PasswordHasher;
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
    private static final int hashByteSize = 16, saltByteSize = 24, nrIterations = 1500;
    private static final SaltGenerator saltGenerator = Utils.getSaltGenerator(saltByteSize);

    @Mock private PlainPassword testPassword;

    private PasswordHasher passwordHasher, otherPasswordHasher, diffPasswordHasher;

    @Before
    public void setUp() {
        passwordHasher = Utils.getPasswordMaster(hashByteSize, nrIterations, saltGenerator);
        otherPasswordHasher = Utils.getPasswordMaster(hashByteSize, nrIterations, saltGenerator);
        diffPasswordHasher = Utils.getPasswordMaster();

        when(testPassword.getPassword()).thenReturn("testPassword");
    }

    @Test
    public void testNotNull() {
        assertThat(passwordHasher, is(not(equalTo(null))));
    }

    @Test
    public void testCreateHash() {
        Salt salt = saltGenerator.generate();
        SaltedPasswordDigest hashedPassword = passwordHasher.createHash(testPassword, salt);
        assertThat(hashedPassword, is(not(equalTo(null))));
        assertThat(hashedPassword.getPassword(), is(not(equalTo(null))));
        assertThat(hashedPassword, is(passwordHasher.createHash(testPassword, salt)));
    }

    @Test
    public void testHash() {
        String password = "testPassword";
        Salt salt = saltGenerator.generate();
        byte[] hash = passwordHasher.hash(password, salt.getBytes(), nrIterations, hashByteSize);
        assertThat(hash, is(not(equalTo(null))));
        assertThat(hash.length, is(not(equalTo(0))));
        assertThat(hash, is(passwordHasher.hash(password, salt.getBytes(), nrIterations, hashByteSize)));
    }

    @Test
    public void testGetHashByteSize() {
        assertThat(passwordHasher.getHashByteSize(), is(hashByteSize));
    }

    @Test
    public void testGetNumberOfIterations() {
        assertThat(passwordHasher.getNumberOfIterations(), is(nrIterations));
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