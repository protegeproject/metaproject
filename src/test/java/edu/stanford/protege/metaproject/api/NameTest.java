package edu.stanford.protege.metaproject.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
@RunWith(MockitoJUnitRunner.class)
public class NameTest {
    private static final String
            nameStr = "testName",
            diffNameStr = "diffTestName",
            toStringHead = "Name";

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
        assertThat(name, is(equalTo(name)));
    }

    @Test
    public void testEquals() {
        assertThat(name, is(equalTo(otherName)));
    }

    @Test
    public void testNotEquals() {
        assertThat(name, is(not(equalTo(diffName))));
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