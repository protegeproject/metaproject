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
public class DescriptionTest {
    private static final String
            descriptionStr = "testDescription",
            diffDescriptionStr = "diffTestDescription",
            toStringHead = Description.class.getSimpleName();

    private Description description, otherDescription, diffDescription;

    @Before
    public void setUp() {
        description = Utils.getDescription(descriptionStr);
        otherDescription = Utils.getDescription(descriptionStr);
        diffDescription = Utils.getDescription(diffDescriptionStr);
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