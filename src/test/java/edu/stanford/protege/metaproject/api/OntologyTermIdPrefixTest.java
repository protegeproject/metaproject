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
public class OntologyTermIdPrefixTest {
    private static final String
            idPrefixStr = "testIdPrefix",
            diffIdPrefixStr = "diffTestIdPrefix",
            toStringHead = "OntologyTermIdPrefix";

    private OntologyTermIdPrefix idPrefix, otherIdPrefix, diffIdPrefix;

    @Before
    public void setUp() {
        idPrefix = Utils.getOntologyTermIdPrefix(idPrefixStr);
        otherIdPrefix = Utils.getOntologyTermIdPrefix(idPrefixStr);
        diffIdPrefix = Utils.getOntologyTermIdPrefix(diffIdPrefixStr);
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
        assertThat(idPrefix, is(idPrefix));
    }

    @Test
    public void testEquals() {
        assertThat(idPrefix, is(otherIdPrefix));
    }

    @Test
    public void testNotEquals() {
        assertThat(idPrefix, is(not(diffIdPrefix)));
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