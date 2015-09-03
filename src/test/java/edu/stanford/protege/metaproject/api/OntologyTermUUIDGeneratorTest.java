package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.impl.OntologyTermUUIDGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OntologyTermUUIDGeneratorTest {
    private static final String toStringHead = "OntologyTermUUIDGenerator";

    private OntologyTermIdGenerator gen, otherGen;

    @Before
    public void setUp() {
        gen = OntologyTermUUIDGenerator.getInstance();
        otherGen = OntologyTermUUIDGenerator.getInstance();
    }

    @Test
    public void testNotNull() {
        assertThat(gen, is(not(equalTo(null))));
    }

    @Test
    public void testGetNextClassId() {
        OntologyTermId termId = gen.getNextClassId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.getPrefix().get(), is(""));
        assertThat(termId.getSuffix().get(), is(not("")));
    }

    @Test
    public void testGetNextObjectPropertyId() {
        OntologyTermId termId = gen.getNextObjectPropertyId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.getPrefix().get(), is(""));
        assertThat(termId.getSuffix().get(), is(not("")));
    }

    @Test
    public void testGetNextDataPropertyId() {
        OntologyTermId termId = gen.getNextDataPropertyId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.getPrefix().get(), is(""));
        assertThat(termId.getSuffix().get(), is(not("")));
    }

    @Test
    public void testGetNextAnnotationPropertyId() {
        OntologyTermId termId = gen.getNextAnnotationPropertyId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.getPrefix().get(), is(""));
        assertThat(termId.getSuffix().get(), is(not("")));
    }

    @Test
    public void testGetNextIndividualId() {
        OntologyTermId termId = gen.getNextIndividualId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.getPrefix().get(), is(""));
        assertThat(termId.getSuffix().get(), is(not("")));
    }

    @Test
    public void testGetCurrentOntologyTermIdStatus() {
        assertThat(gen.getCurrentOntologyTermIdStatus(), is(Optional.empty()));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(gen, is(gen));
    }

    @Test
    public void testEquals() {
        assertThat(gen, is(otherGen));
    }

    @Test
    public void testHashCode() {
        assertThat(gen.hashCode(), is(otherGen.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(gen.toString(), startsWith(toStringHead));
    }
}