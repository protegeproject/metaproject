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
public class NameTest {
    private static final String
            nameStr = "testName",
            diffNameStr = "diffTestName",
            toStringHead = Name.class.getSimpleName();

    private Name name, otherName, diffName;

    @Before
    public void setUp() {
        name = Utils.getName(nameStr);
        otherName = Utils.getName(nameStr);
        diffName = Utils.getName(diffNameStr);
    }

    @Test
    public void testNotNull() {
        assertThat(name, is(not(equalTo(null))));
    }

    @Test
    public void testGet() {
        assertThat(name.get(), is(nameStr));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(name, is(name));
    }

    @Test
    public void testEquals() {
        assertThat(name, is(otherName));
    }

    @Test
    public void testNotEquals() {
        assertThat(name, is(not(diffName)));
    }

    @Test
    public void testHashCode() {
        assertThat(name.hashCode(), is(otherName.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(name.toString(), startsWith(toStringHead));
    }
}