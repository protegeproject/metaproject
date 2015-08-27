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
public class OntologyTermIdPrefixTest {
    private static final String
            idPrefixStr = "testIdPrefix",
            diffIdPrefixStr = "diffTestIdPrefix",
            toStringHead = "OntologyTermIdPrefix";

    private OntologyTermIdPrefix idPrefix, otherIdPrefix, diffIdPrefix;

    @Before
    public void setUp() {
        idPrefix = TestUtils.getOntologyTermIdPrefix(idPrefixStr);
        otherIdPrefix = TestUtils.getOntologyTermIdPrefix(idPrefixStr);
        diffIdPrefix = TestUtils.getOntologyTermIdPrefix(diffIdPrefixStr);
    }

    @Test
    public void testNotNull() {
        assertThat(idPrefix, is(not(equalTo(null))));
    }

    @Test
    public void testGet() {
        assertThat(idPrefix.get(), is(idPrefixStr));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(idPrefix, is(equalTo(idPrefix)));
    }

    @Test
    public void testEquals() {
        assertThat(idPrefix, is(equalTo(otherIdPrefix)));
    }

    @Test
    public void testNotEquals() {
        assertThat(idPrefix, is(not(equalTo(diffIdPrefix))));
    }

    @Test
    public void testHashCode() {
        assertThat(idPrefix.hashCode(), is(otherIdPrefix.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(idPrefix.toString(), startsWith(toStringHead));
    }
}