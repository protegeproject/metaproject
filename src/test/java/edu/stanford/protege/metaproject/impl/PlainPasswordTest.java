package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.PlainPassword;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class PlainPasswordTest {
    private static final String
            passwordStr = "testPassword1",
            diffPasswordStr = "testPassword2",
            toStringHead = PlainPassword.class.getSimpleName();

    private PlainPassword password, otherPassword, diffPassword;

    @Before
    public void setUp() {
        password = TestUtils.getPlainPassword(passwordStr);
        otherPassword = TestUtils.getPlainPassword(passwordStr);
        diffPassword = TestUtils.getPlainPassword(diffPasswordStr);
    }

    @Test
    public void testNotNull() {
        assertThat(password, is(not(equalTo(null))));
    }

    @Test
    public void testGetPassword() {
        assertThat(password.getPassword(), is(passwordStr));
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