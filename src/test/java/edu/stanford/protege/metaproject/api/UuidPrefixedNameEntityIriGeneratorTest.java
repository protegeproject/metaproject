package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.impl.UuidPrefixedNameEntityIriGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UuidPrefixedNameEntityIriGeneratorTest {
    private static final String toStringHead = "UuidPrefixedNameEntityIriGenerator";
    private static final EntityIriPrefix iriPrefix = Utils.getEntityIriPrefix();
    private static final EntityNamePrefix
            classIdPrefix = Utils.getEntityNamePrefix(),
            objPropPrefix = Utils.getEntityNamePrefix(),
            dataPropPrefix = Utils.getEntityNamePrefix(),
            annPropPrefix = Utils.getEntityNamePrefix(),
            indPrefix = Utils.getEntityNamePrefix(),
            diffIndPrefix = Utils.getEntityNamePrefix("customIndividualPrefix");

    private UuidPrefixedNameEntityIriGenerator gen, otherGen, diffGen;

    @Before
    public void setUp() {
        gen = Utils.getUuidPrefixedNameEntityIriGenerator(iriPrefix, classIdPrefix, objPropPrefix, dataPropPrefix, annPropPrefix, indPrefix);
        otherGen = Utils.getUuidPrefixedNameEntityIriGenerator(iriPrefix, classIdPrefix, objPropPrefix, dataPropPrefix, annPropPrefix, indPrefix);
        diffGen = Utils.getUuidPrefixedNameEntityIriGenerator(iriPrefix, classIdPrefix, objPropPrefix, dataPropPrefix, annPropPrefix, diffIndPrefix);
    }

    @Test
    public void testNotNull() {
        assertThat(gen, is(not(equalTo(null))));
    }

    @Test
    public void testGetCurrentOntologyTermIdStatus() {
        EntityIriStatus expected = Utils.getEntityIriStatus(iriPrefix, classIdPrefix, objPropPrefix, dataPropPrefix, annPropPrefix, indPrefix, null, null, null, null, null);
        assertThat(gen.getEntityIriStatus(), is(Optional.of(expected)));
    }

    @Test
    public void testGetClassIdPrefix() {
        assertThat(gen.getClassNamePrefix(), is(classIdPrefix));
    }

    @Test
    public void testGetObjectPropertyIdPrefix() {
        assertThat(gen.getObjectPropertyNamePrefix(), is(objPropPrefix));
    }

    @Test
    public void testGetDataPropertyIdPrefix() {
        assertThat(gen.getDataPropertyNamePrefix(), is(dataPropPrefix));
    }

    @Test
    public void testGetAnnotationPropertyIdPrefix() {
        assertThat(gen.getAnnotationPropertyNamePrefix(), is(annPropPrefix));
    }

    @Test
    public void testGetIndividualIdPrefix() {
        assertThat(gen.getIndividualNamePrefix(), is(indPrefix));
    }

    @Test
    public void testGetNextClassIri() {
        EntityIri entityIri = gen.getNextClassIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getEntityName().getPrefix(), is(classIdPrefix));
        assertThat(entityIri.getEntityName(), is(not(equalTo(null))));
        assertThat(entityIri.getEntityName().get(), is(not(equalTo(""))));
    }

    @Test
    public void testGetNextObjectPropertyIri() {
        EntityIri entityIri = gen.getNextObjectPropertyIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getEntityName(), is(not(equalTo(null))));
        assertThat(entityIri.getEntityName().get(), is(not(equalTo(""))));
        assertThat(entityIri.getEntityName().getPrefix(), is(objPropPrefix));
    }

    @Test
    public void testGetNextDataPropertyIri() {
        EntityIri entityIri = gen.getNextDataPropertyIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getEntityName(), is(not(equalTo(null))));
        assertThat(entityIri.getEntityName().get(), is(not(equalTo(""))));
        assertThat(entityIri.getEntityName().getPrefix(), is(dataPropPrefix));
    }

    @Test
    public void testGetNextAnnotationPropertyIri() {
        EntityIri entityIri = gen.getNextAnnotationPropertyIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getEntityName(), is(not(equalTo(null))));
        assertThat(entityIri.getEntityName().get(), is(not(equalTo(""))));
        assertThat(entityIri.getEntityName().getPrefix(), is(annPropPrefix));
    }

    @Test
    public void testGetNextIndividualIri() {
        EntityIri entityIri = gen.getNextIndividualIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getEntityName(), is(not(equalTo(null))));
        assertThat(entityIri.getEntityName().get(), is(not(equalTo(""))));
        assertThat(entityIri.getEntityName().getPrefix(), is(indPrefix));
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