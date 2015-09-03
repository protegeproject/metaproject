package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OntologyTermIdStatusTest {
    private static final OntologyTermIdPrefix
            classPrefix = Utils.getOntologyTermIdPrefix(),
            dataPropPrefix = Utils.getOntologyTermIdPrefix(),
            annPropPrefix = Utils.getOntologyTermIdPrefix(),
            indPrefix = Utils.getOntologyTermIdPrefix();
    private static final OntologyTermIdSuffix
            classSuffix = Utils.getOntologyTermIdSuffix(),
            dataPropSuffix = Utils.getOntologyTermIdSuffix(),
            annPropSuffix = Utils.getOntologyTermIdSuffix();
    private static final String toStringHead = "OntologyTermIdStatus";

    private OntologyTermIdStatus termId, otherTermId, diffTermId;

    @Before
    public void setUp() {
        termId = Utils.getOntologyTermIdStatus(classPrefix, null, dataPropPrefix, annPropPrefix, indPrefix, classSuffix, null, dataPropSuffix, annPropSuffix, null);
        otherTermId = Utils.getOntologyTermIdStatus(classPrefix, null, dataPropPrefix, annPropPrefix, indPrefix, classSuffix, null, dataPropSuffix, annPropSuffix, null);
        diffTermId = Utils.getOntologyTermIdStatus();
    }

    @Test
    public void testNotNull() {
        assertThat(termId, is(not(equalTo(null))));
    }

    @Test
    public void testGetClassIdPrefix() {
        assertThat(termId.getClassIdPrefix().get(), is(classPrefix));
    }

    @Test
    public void testGetClassIdSuffix() {
        assertThat(termId.getClassIdSuffix().get(), is(classSuffix));
    }

    @Test
    public void testGetObjectPropertyIdPrefix() {
        assertThat(termId.getObjectPropertyIdPrefix(), is(Optional.empty()));
    }

    @Test
    public void testGetObjectPropertyIdSuffix() {
        assertThat(termId.getObjectPropertyIdSuffix(), is(Optional.empty()));
    }

    @Test
    public void testGetDataPropertyIdPrefix() {
        assertThat(termId.getDataPropertyIdPrefix(), is(Optional.of(dataPropPrefix)));
    }

    @Test
    public void testGetDataPropertyIdSuffix() {
        assertThat(termId.getDataPropertyIdSuffix(), is(Optional.of(dataPropSuffix)));
    }

    @Test
    public void testGetAnnotationPropertyIdPrefix() {
        assertThat(termId.getAnnotationPropertyIdPrefix(), is(Optional.of(annPropPrefix)));
    }

    @Test
    public void testGetAnnotationPropertyIdSuffix() {
        assertThat(termId.getAnnotationPropertyIdSuffix(), is(Optional.of(annPropSuffix)));
    }

    @Test
    public void testGetIndividualIdPrefix() {
        assertThat(termId.getIndividualIdPrefix().get(), is(indPrefix));
    }

    @Test
    public void testGetIndividualIdSuffix() {
        assertThat(termId.getIndividualIdSuffix(), is(Optional.empty()));
    }

    @Test(expected=NoSuchElementException.class)
    public void testGetIndividualIdSuffixThrowsException() {
        termId.getIndividualIdSuffix().get();
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