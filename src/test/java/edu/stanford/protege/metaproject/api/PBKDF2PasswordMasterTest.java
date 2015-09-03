package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.PasswordMaster;
import edu.stanford.protege.metaproject.api.PlainPassword;
import edu.stanford.protege.metaproject.api.SaltGenerator;
import edu.stanford.protege.metaproject.api.SaltedPassword;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PBKDF2PasswordMasterTest {
    private static final String toStringHead = "PBKDF2PasswordMaster";
    private static final int hashByteSize = 16, saltByteSize = 24, nrPBKDF2Iterations = 1500;
    private static final SaltGenerator saltGenerator = Utils.getSaltGenerator(saltByteSize);

    private PasswordMaster passwordMaster, otherPasswordManager, diffPasswordManager;

    @Before
    public void setUp() {
        passwordMaster = Utils.getPasswordMaster(hashByteSize, nrPBKDF2Iterations, saltGenerator);
        otherPasswordManager = Utils.getPasswordMaster(hashByteSize, nrPBKDF2Iterations, saltGenerator);
        diffPasswordManager = Utils.getPasswordMaster();
    }

    @Test
    public void testNotNull() {
        assertThat(passwordMaster, is(not(equalTo(null))));
    }

    @Test
    public void testCreateHash() {
        String testPassword = "testPassword";
        SaltedPassword saltedPassword = passwordMaster.createHash(testPassword);
        assertThat(saltedPassword, is(not(equalTo(null))));
        assertThat(saltedPassword.getPassword(), is(not(equalTo(null))));
        assertThat(passwordMaster.validatePassword(testPassword, saltedPassword), is(true));
    }

    @Test
    public void testCreateHashWithPlainPassword() {
        PlainPassword testPassword = Utils.getPlainPassword();
        SaltedPassword saltedPassword = passwordMaster.createHash(testPassword);
        assertThat(saltedPassword, is(not(equalTo(null))));
        assertThat(saltedPassword.getPassword(), is(not(equalTo(null))));
        assertThat(passwordMaster.validatePassword(testPassword, saltedPassword), is(true));
    }

    @Test
    public void testValidatePassword() {
        String testPassword = "testPassword", diffPassword = "diffTestPassword";
        SaltedPassword saltedPassword = passwordMaster.createHash(testPassword);
        SaltedPassword diffSaltedPassword = passwordMaster.createHash(diffPassword);
        assertThat(passwordMaster.validatePassword(testPassword, saltedPassword), is(true));
        assertThat(passwordMaster.validatePassword(testPassword, diffSaltedPassword), is(false));
    }

    @Test
    public void testValidatePasswordWithPlainPassword() {
        PlainPassword testPassword = Utils.getPlainPassword(), diffPassword = Utils.getPlainPassword();
        SaltedPassword saltedPassword = passwordMaster.createHash(testPassword);
        SaltedPassword diffSaltedPassword = passwordMaster.createHash(diffPassword);
        assertThat(passwordMaster.validatePassword(testPassword, saltedPassword), is(true));
        assertThat(passwordMaster.validatePassword(testPassword, diffSaltedPassword), is(false));
    }

    @Test
    public void testGetSaltGenerator() {
        assertThat(passwordMaster.getSaltGenerator(), is(saltGenerator));
    }

    @Test
    public void testGetHashByteSize() {
        assertThat(passwordMaster.getHashByteSize(), is(hashByteSize));
    }

    @Test
    public void testGetNumberOfIterations() {
        assertThat(passwordMaster.getNumberOfIterations(), is(Optional.of(nrPBKDF2Iterations)));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(passwordMaster, is(passwordMaster));
    }

    @Test
    public void testEquals() {
        assertThat(passwordMaster, is(otherPasswordManager));
    }

    @Test
    public void testNotEquals() {
        assertThat(passwordMaster, is(not(diffPasswordManager)));
    }

    @Test
    public void testHashcode() {
        assertThat(passwordMaster.hashCode(), is(otherPasswordManager.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(passwordMaster.toString(), startsWith(toStringHead));
    }
}