package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.impl.SequentialPrefixedNameEntityIriGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class SequentialPrefixedNameEntityIriGeneratorTest {
    private static final String toStringHead = "SequentialPrefixedNameEntityIriGenerator";
    private static final EntityNamePrefix
            classNamePrefix = Utils.getEntityNamePrefix(),
            objPropPrefix = Utils.getEntityNamePrefix(),
            dataPropPrefix = Utils.getEntityNamePrefix(),
            annPropPrefix = Utils.getEntityNamePrefix(),
            indPrefix = Utils.getEntityNamePrefix(),
            diffIndPrefix = Utils.getEntityNamePrefix("customIndividualPrefix");
    private static final EntityNameSuffix
            classNameSuffix = Utils.getEntityNameSuffix(),
            objPropSuffix = Utils.getEntityNameSuffix(),
            dataPropSuffix = Utils.getEntityNameSuffix(),
            annPropSuffix = Utils.getEntityNameSuffix(),
            indSuffix = Utils.getEntityNameSuffix();
    private static final EntityIriPrefix iriPrefix = Utils.getEntityIriPrefix();
    private SequentialPrefixedNameEntityIriGenerator gen, otherGen, diffGen;

    @Before
    public void setUp() {
        gen = Utils.getSequentialPrefixedNameEntityIriGenerator(iriPrefix, classNamePrefix, objPropPrefix, dataPropPrefix, annPropPrefix, indPrefix,
                classNameSuffix, objPropSuffix, dataPropSuffix, annPropSuffix, indSuffix);
        otherGen = Utils.getSequentialPrefixedNameEntityIriGenerator(iriPrefix, classNamePrefix, objPropPrefix, dataPropPrefix, annPropPrefix, indPrefix,
                classNameSuffix, objPropSuffix, dataPropSuffix, annPropSuffix, indSuffix);
        diffGen = Utils.getSequentialPrefixedNameEntityIriGenerator(iriPrefix, classNamePrefix, objPropPrefix, dataPropPrefix, annPropPrefix, diffIndPrefix,
                classNameSuffix, objPropSuffix, dataPropSuffix, annPropSuffix, indSuffix);
    }

    @Test
    public void testNotNull() {
        assertThat(gen, is(not(equalTo(null))));
    }

    @Test
    public void testGetCurrentOntologyTermNameStatus() {
        assertThat(gen.getEntityIriStatus().isPresent(), is(true));
        assertThat(gen.getEntityIriStatus().get().getEntityIriPrefix(), is(iriPrefix));
        assertThat(gen.getEntityIriStatus().get().getClassNamePrefix(), is(Optional.of(classNamePrefix)));
        assertThat(gen.getEntityIriStatus().get().getObjectPropertyNamePrefix(), is(Optional.of(objPropPrefix)));
        assertThat(gen.getEntityIriStatus().get().getDataPropertyNamePrefix(), is(Optional.of(dataPropPrefix)));
        assertThat(gen.getEntityIriStatus().get().getAnnotationPropertyNamePrefix(), is(Optional.of(annPropPrefix)));
        assertThat(gen.getEntityIriStatus().get().getIndividualNamePrefix(), is(Optional.of(indPrefix)));
    }

    @Test
    public void testGetEntityIriPrefix() {
        assertThat(gen.getEntityIriPrefix(), is(iriPrefix));
    }

    @Test
    public void testGetClassNameSuffix() {
        assertThat(gen.getClassNameSuffix(), is(classNameSuffix));
    }

    @Test
    public void testGetObjectPropertyNameSuffix() {
        assertThat(gen.getObjectPropertyNameSuffix(), is(objPropSuffix));
    }

    @Test
    public void testGetDataPropertyNameSuffix() {
        assertThat(gen.getDataPropertyNameSuffix(), is(dataPropSuffix));
    }

    @Test
    public void testGetAnnotationPropertyNameSuffix() {
        assertThat(gen.getAnnotationPropertyNameSuffix(), is(annPropSuffix));
    }

    @Test
    public void testGetIndividualNameSuffix() {
        assertThat(gen.getIndividualNameSuffix(), is(indSuffix));
    }

    @Test
    public void testGetClassNamePrefix() {
        assertThat(gen.getClassNamePrefix(), is(classNamePrefix));
    }

    @Test
    public void testGetObjectPropertyNamePrefix() {
        assertThat(gen.getObjectPropertyNamePrefix(), is(objPropPrefix));
    }

    @Test
    public void testGetDataPropertyNamePrefix() {
        assertThat(gen.getDataPropertyNamePrefix(), is(dataPropPrefix));
    }

    @Test
    public void testGetAnnotationPropertyNamePrefix() {
        assertThat(gen.getAnnotationPropertyNamePrefix(), is(annPropPrefix));
    }

    @Test
    public void testGetIndividualNamePrefix() {
        assertThat(gen.getIndividualNamePrefix(), is(indPrefix));
    }

    private boolean isCorrectIncrement(EntityNameSuffix prevSuffix, EntityNameSuffix newSuffix) {
        int prev = Integer.parseInt(prevSuffix.get());
        return (++prev == Integer.parseInt(newSuffix.get()));
    }

    @Test
    public void testGetNextClassIri() {
        EntityIri entityIri = gen.getNextClassIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getEntityName(), is(not(equalTo(null))));
        assertThat(entityIri.getEntityName().get(), is(not(equalTo(""))));
        assertThat(entityIri.getEntityName().getPrefix(), is(classNamePrefix));
        assertThat(isCorrectIncrement(classNameSuffix, entityIri.getEntityName().getSuffix()), is(true));
    }

    @Test
    public void testGetNextObjectPropertyIri() {
        EntityIri entityIri = gen.getNextObjectPropertyIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getEntityName(), is(not(equalTo(null))));
        assertThat(entityIri.getEntityName().get(), is(not(equalTo(""))));
        assertThat(entityIri.getEntityName().getPrefix(), is(objPropPrefix));
        assertThat(isCorrectIncrement(objPropSuffix, entityIri.getEntityName().getSuffix()), is(true));
    }

    @Test
    public void testGetNextDataPropertyIri() {
        EntityIri entityIri = gen.getNextDataPropertyIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getEntityName(), is(not(equalTo(null))));
        assertThat(entityIri.getEntityName().get(), is(not(equalTo(""))));
        assertThat(entityIri.getEntityName().getPrefix(), is(dataPropPrefix));
        assertThat(isCorrectIncrement(dataPropSuffix, entityIri.getEntityName().getSuffix()), is(true));
    }

    @Test
    public void testGetNextAnnotationPropertyIri() {
        EntityIri entityIri = gen.getNextAnnotationPropertyIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getEntityName(), is(not(equalTo(null))));
        assertThat(entityIri.getEntityName().get(), is(not(equalTo(""))));
        assertThat(entityIri.getEntityName().getPrefix(), is(annPropPrefix));
        assertThat(isCorrectIncrement(annPropSuffix, entityIri.getEntityName().getSuffix()), is(true));
    }

    @Test
    public void testGetNextIndividualIri() {
        EntityIri entityIri = gen.getNextIndividualIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getEntityName(), is(not(equalTo(null))));
        assertThat(entityIri.getEntityName().get(), is(not(equalTo(""))));
        assertThat(entityIri.getEntityName().getPrefix(), is(indPrefix));
        assertThat(isCorrectIncrement(indSuffix, entityIri.getEntityName().getSuffix()), is(true));
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