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
public class SaltTest {
    private static final String
            saltStr = "testSalt1",
            diffSaltStr = "testSalt2",
            toStringHead = Salt.class.getSimpleName();

    private Salt salt, otherSalt, diffSalt;

    @Before
    public void setUp() {
        salt = Utils.getSalt(saltStr);
        otherSalt = Utils.getSalt(saltStr);
        diffSalt = Utils.getSalt(diffSaltStr);
    }

    @Test
    public void testNotNull() {
        assertThat(salt, is(not(equalTo(null))));
    }

    @Test
    public void testGetString() {
        assertThat(salt.getString(), is(saltStr));
    }

    @Test
    public void testGetBytes() {
        assertThat(salt.getBytes(), is(saltStr.getBytes()));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(salt, is(salt));
    }

    @Test
    public void testEquals() {
        assertThat(salt, is(otherSalt));
    }

    @Test
    public void testNotEquals() {
        assertThat(salt, is(not(diffSalt)));
    }

    @Test
    public void testHashCode() {
        assertThat(salt.hashCode(), is(otherSalt.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(salt.toString(), startsWith(toStringHead));
    }
}