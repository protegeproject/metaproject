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
public class OntologyTermIdTest {
    private static final OntologyTermIdPrefix prefix1 = Utils.getOntologyTermIdPrefix(), prefix2 = Utils.getOntologyTermIdPrefix();
    private static final OntologyTermIdSuffix suffix1 = Utils.getOntologyTermIdSuffix(), suffix2 = Utils.getOntologyTermIdSuffix();
    private static final String toStringHead = "OntologyTermId";

    private OntologyTermId termId, otherTermId, diffTermId;

    @Before
    public void setUp() {
        termId = Utils.getOntologyTermId(prefix1, suffix1);
        otherTermId = Utils.getOntologyTermId(prefix1, suffix1);
        diffTermId = Utils.getOntologyTermId(prefix2, suffix2);
    }

    @Test
    public void testNotNull() {
        assertThat(termId, is(not(equalTo(null))));
    }

    @Test
    public void testGetPrefix() {
        assertThat(termId.getPrefix(), is(prefix1));
    }

    @Test
    public void testGetSuffix() {
        assertThat(termId.getSuffix(), is(suffix1));
    }

    @Test
    public void testGet() {
        assertThat(termId.get(), is(prefix1.get().concat(suffix1.get())));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(termId, is(termId));
    }

    @Test
    public void testEquals() {
        assertThat(termId, is(otherTermId));
    }

    @Test
    public void testNotEquals() {
        assertThat(termId, is(not(diffTermId)));
    }

    @Test
    public void testHashCode() {
        assertThat(termId.hashCode(), is(otherTermId.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(termId.toString(), startsWith(toStringHead));
    }
}