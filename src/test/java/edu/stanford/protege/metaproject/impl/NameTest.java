package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.Name;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class NameTest {
    private static final String
            nameStr = "testName",
            diffNameStr = "diffTestName",
            toStringHead = Name.class.getSimpleName();

    private Name name, otherName, diffName;

    @Before
    public void setUp() {
        name = TestUtils.getName(nameStr);
        otherName = TestUtils.getName(nameStr);
        diffName = TestUtils.getName(diffNameStr);
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