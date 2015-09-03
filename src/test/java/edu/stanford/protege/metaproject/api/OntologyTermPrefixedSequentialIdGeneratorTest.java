package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.impl.OntologyTermPrefixedSequentialIdGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OntologyTermPrefixedSequentialIdGeneratorTest {
    private static final String toStringHead = "OntologyTermPrefixedSequentialIdGenerator";
    private static final OntologyTermIdPrefix
            classIdPrefix = Utils.getOntologyTermIdPrefix(),
            objPropPrefix = Utils.getOntologyTermIdPrefix(),
            dataPropPrefix = Utils.getOntologyTermIdPrefix(),
            annPropPrefix = Utils.getOntologyTermIdPrefix(),
            indPrefix = Utils.getOntologyTermIdPrefix(),
            diffIndPrefix = Utils.getOntologyTermIdPrefix("customIndividualPrefix");
    private static final OntologyTermIdSuffix
            classIdSuffix = Utils.getOntologyTermIdSuffix(),
            objPropSuffix = Utils.getOntologyTermIdSuffix(),
            dataPropSuffix = Utils.getOntologyTermIdSuffix(),
            annPropSuffix = Utils.getOntologyTermIdSuffix(),
            indSuffix = Utils.getOntologyTermIdSuffix();

    private OntologyTermPrefixedSequentialIdGenerator gen, otherGen, diffGen;

    @Before
    public void setUp() {
        gen = Utils.getOntologyTermPrefixedSequentialIdGenerator(classIdPrefix, objPropPrefix, dataPropPrefix, annPropPrefix, indPrefix,
                classIdSuffix, objPropSuffix, dataPropSuffix, annPropSuffix, indSuffix);
        otherGen = Utils.getOntologyTermPrefixedSequentialIdGenerator(classIdPrefix, objPropPrefix, dataPropPrefix, annPropPrefix, indPrefix,
                classIdSuffix, objPropSuffix, dataPropSuffix, annPropSuffix, indSuffix);
        diffGen = Utils.getOntologyTermPrefixedSequentialIdGenerator(classIdPrefix, objPropPrefix, dataPropPrefix, annPropPrefix, diffIndPrefix,
                classIdSuffix, objPropSuffix, dataPropSuffix, annPropSuffix, indSuffix);
    }

    @Test
    public void testNotNull() {
        assertThat(gen, is(not(equalTo(null))));
    }

    @Test
    public void testGetCurrentOntologyTermIdStatus() {
        assertThat(gen.getCurrentOntologyTermIdStatus().isPresent(), is(true));
        assertThat(gen.getCurrentOntologyTermIdStatus().get().getClassIdPrefix(), is(Optional.of(classIdPrefix)));
    }

    @Test
    public void testGetClassIdSuffix() {
        assertThat(gen.getClassIdSuffix(), is(classIdSuffix));
    }

    @Test
    public void testGetObjectPropertyIdSuffix() {
        assertThat(gen.getObjectPropertyIdSuffix(), is(objPropSuffix));
    }

    @Test
    public void testGetDataPropertyIdSuffix() {
        assertThat(gen.getDataPropertyIdSuffix(), is(dataPropSuffix));
    }

    @Test
    public void testGetAnnotationPropertyIdSuffix() {
        assertThat(gen.getAnnotationPropertyIdSuffix(), is(annPropSuffix));
    }

    @Test
    public void testGetIndividualIdSuffix() {
        assertThat(gen.getIndividualIdSuffix(), is(indSuffix));
    }

    @Test
    public void testGetClassIdPrefix() {
        assertThat(gen.getClassIdPrefix(), is(classIdPrefix));
    }

    @Test
    public void testGetObjectPropertyIdPrefix() {
        assertThat(gen.getObjectPropertyIdPrefix(), is(objPropPrefix));
    }

    @Test
    public void testGetDataPropertyIdPrefix() {
        assertThat(gen.getDataPropertyIdPrefix(), is(dataPropPrefix));
    }

    @Test
    public void testGetAnnotationPropertyIdPrefix() {
        assertThat(gen.getAnnotationPropertyIdPrefix(), is(annPropPrefix));
    }

    @Test
    public void testGetIndividualIdPrefix() {
        assertThat(gen.getIndividualIdPrefix(), is(indPrefix));
    }

    @Test
    public void testGetNextClassId() {
        OntologyTermId termId = gen.getNextClassId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.getPrefix(), is(classIdPrefix));
        assertThat(termId.getSuffix(), is(not(equalTo(null))));
        assertThat(termId.getSuffix(), is(not(equalTo(""))));
    }

    @Test
    public void testGetNextObjectPropertyId() {
        OntologyTermId termId = gen.getNextObjectPropertyId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.getPrefix(), is(objPropPrefix));
        assertThat(termId.getSuffix(), is(not(equalTo(null))));
        assertThat(termId.getSuffix(), is(not(equalTo(""))));
    }

    @Test
    public void testGetNextDataPropertyId() {
        OntologyTermId termId = gen.getNextDataPropertyId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.getPrefix(), is(dataPropPrefix));
        assertThat(termId.getSuffix(), is(not(equalTo(null))));
        assertThat(termId.getSuffix(), is(not(equalTo(""))));
    }

    @Test
    public void testGetNextAnnotationPropertyId() {
        OntologyTermId termId = gen.getNextAnnotationPropertyId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.getPrefix(), is(annPropPrefix));
        assertThat(termId.getSuffix(), is(not(equalTo(null))));
        assertThat(termId.getSuffix(), is(not(equalTo(""))));
    }

    @Test
    public void testGetNextIndividualId() {
        OntologyTermId termId = gen.getNextIndividualId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.getPrefix(), is(indPrefix));
        assertThat(termId.getSuffix(), is(not(equalTo(null))));
        assertThat(termId.getSuffix(), is(not(equalTo(""))));
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
    public void testNotEquals() {
        assertThat(gen, is(not(diffGen)));
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