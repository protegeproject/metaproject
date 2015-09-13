package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PBKDF2PasswordHandlerTest {
    private static final String toStringHead = "PBKDF2PasswordHandler";
    private static final int hashByteSize = 16, saltByteSize = 24, nrPBKDF2Iterations = 1500;
    private static final SaltGenerator saltGenerator = Utils.getSaltGenerator(saltByteSize);

    private PasswordHandler passwordHandler, otherPasswordManager, diffPasswordManager;

    @Before
    public void setUp() {
        passwordHandler = Utils.getPasswordMaster(hashByteSize, nrPBKDF2Iterations, saltGenerator);
        otherPasswordManager = Utils.getPasswordMaster(hashByteSize, nrPBKDF2Iterations, saltGenerator);
        diffPasswordManager = Utils.getPasswordMaster();
    }

    @Test
    public void testNotNull() {
        assertThat(passwordHandler, is(not(equalTo(null))));
    }

    @Test
    public void testCreateHash() {
        PlainPassword testPassword = Utils.getPlainPassword("testPassword");
        SaltedPassword saltedPassword = passwordHandler.createHash(testPassword);
        assertThat(saltedPassword, is(not(equalTo(null))));
        assertThat(saltedPassword.getPassword(), is(not(equalTo(null))));
        assertThat(passwordHandler.validatePassword(testPassword, saltedPassword), is(true));
    }

    @Test
    public void testCreateHashWithPlainPassword() {
        PlainPassword testPassword = Utils.getPlainPassword();
        SaltedPassword saltedPassword = passwordHandler.createHash(testPassword);
        assertThat(saltedPassword, is(not(equalTo(null))));
        assertThat(saltedPassword.getPassword(), is(not(equalTo(null))));
        assertThat(passwordHandler.validatePassword(testPassword, saltedPassword), is(true));
    }

    @Test
    public void testValidatePassword() {
        PlainPassword testPassword = Utils.getPlainPassword("testPassword"), diffPassword = Utils.getPlainPassword("diffTestPassword");
        SaltedPassword saltedPassword = passwordHandler.createHash(testPassword);
        SaltedPassword diffSaltedPassword = passwordHandler.createHash(diffPassword);
        assertThat(passwordHandler.validatePassword(testPassword, saltedPassword), is(true));
        assertThat(passwordHandler.validatePassword(testPassword, diffSaltedPassword), is(false));
    }

    @Test
    public void testValidatePasswordWithPlainPassword() {
        PlainPassword testPassword = Utils.getPlainPassword(), diffPassword = Utils.getPlainPassword();
        SaltedPassword saltedPassword = passwordHandler.createHash(testPassword);
        SaltedPassword diffSaltedPassword = passwordHandler.createHash(diffPassword);
        assertThat(passwordHandler.validatePassword(testPassword, saltedPassword), is(true));
        assertThat(passwordHandler.validatePassword(testPassword, diffSaltedPassword), is(false));
    }

    @Test
    public void testGetSaltGenerator() {
        assertThat(passwordHandler.getSaltGenerator(), is(saltGenerator));
    }

    @Test
    public void testGetHashByteSize() {
        assertThat(passwordHandler.getHashByteSize(), is(hashByteSize));
    }

    @Test
    public void testGetNumberOfIterations() {
        assertThat(passwordHandler.getNumberOfIterations(), is(Optional.of(nrPBKDF2Iterations)));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(passwordHandler, is(passwordHandler));
    }

    @Test
    public void testEquals() {
        assertThat(passwordHandler, is(otherPasswordManager));
    }

    @Test
    public void testNotEquals() {
        assertThat(passwordHandler, is(not(diffPasswordManager)));
    }

    @Test
    public void testHashcode() {
        assertThat(passwordHandler.hashCode(), is(otherPasswordManager.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(passwordHandler.toString(), startsWith(toStringHead));
    }
}