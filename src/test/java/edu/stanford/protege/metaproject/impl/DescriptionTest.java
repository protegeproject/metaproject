package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.Description;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class DescriptionTest {
    private static final String
            descriptionStr = "testDescription",
            diffDescriptionStr = "diffTestDescription",
            toStringHead = Description.class.getSimpleName();

    private Description description, otherDescription, diffDescription;

    @Before
    public void setUp() {
        description = TestUtils.getDescription(descriptionStr);
        otherDescription = TestUtils.getDescription(descriptionStr);
        diffDescription = TestUtils.getDescription(diffDescriptionStr);
    }

    @Test
    public void testNotNull() {
        assertThat(description, is(not(equalTo(null))));
    }

    @Test
    public void testGet() {
        assertThat(description.get(), is(descriptionStr));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(description, is(description));
    }

    @Test
    public void testEquals() {
        assertThat(description, is(otherDescription));
    }

    @Test
    public void testNotEquals() {
        assertThat(description, is(not(diffDescription)));
    }

    @Test
    public void testHashCode() {
        assertThat(description.hashCode(), is(otherDescription.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(description.toString(), startsWith(toStringHead));
    }
}