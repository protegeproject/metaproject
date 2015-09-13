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
public class EntityIriStatusTest {
    private static final EntityNamePrefix
            classPrefix = Utils.getEntityNamePrefix(),
            dataPropPrefix = Utils.getEntityNamePrefix(),
            annPropPrefix = Utils.getEntityNamePrefix(),
            indPrefix = Utils.getEntityNamePrefix();
    private static final EntityNameSuffix
            classSuffix = Utils.getEntityNameSuffix(),
            dataPropSuffix = Utils.getEntityNameSuffix(),
            annPropSuffix = Utils.getEntityNameSuffix();
    private static final EntityIriPrefix iriPrefix = Utils.getEntityIriPrefix();
    private static final String toStringHead = "EntityIriStatus";

    private EntityIriStatus termId, otherTermId, diffTermId;

    @Before
    public void setUp() {
        termId = Utils.getEntityIriStatus(iriPrefix, classPrefix, null, dataPropPrefix, annPropPrefix, indPrefix, classSuffix, null, dataPropSuffix, annPropSuffix, null);
        otherTermId = Utils.getEntityIriStatus(iriPrefix, classPrefix, null, dataPropPrefix, annPropPrefix, indPrefix, classSuffix, null, dataPropSuffix, annPropSuffix, null);
        diffTermId = Utils.getEntityIriStatus();
    }

    @Test
    public void testNotNull() {
        assertThat(termId, is(not(equalTo(null))));
    }

    @Test
    public void testGetClassIdPrefix() {
        assertThat(termId.getClassNamePrefix().get(), is(classPrefix));
    }

    @Test
    public void testGetClassIdSuffix() {
        assertThat(termId.getClassNameSuffix().get(), is(classSuffix));
    }

    @Test
    public void testGetObjectPropertyIdPrefix() {
        assertThat(termId.getObjectPropertyNamePrefix(), is(Optional.empty()));
    }

    @Test
    public void testGetObjectPropertyIdSuffix() {
        assertThat(termId.getObjectPropertyNameSuffix(), is(Optional.empty()));
    }

    @Test
    public void testGetDataPropertyIdPrefix() {
        assertThat(termId.getDataPropertyNamePrefix(), is(Optional.of(dataPropPrefix)));
    }

    @Test
    public void testGetDataPropertyIdSuffix() {
        assertThat(termId.getDataPropertyNameSuffix(), is(Optional.of(dataPropSuffix)));
    }

    @Test
    public void testGetAnnotationPropertyIdPrefix() {
        assertThat(termId.getAnnotationPropertyNamePrefix(), is(Optional.of(annPropPrefix)));
    }

    @Test
    public void testGetAnnotationPropertyIdSuffix() {
        assertThat(termId.getAnnotationPropertyNameSuffix(), is(Optional.of(annPropSuffix)));
    }

    @Test
    public void testGetIndividualIdPrefix() {
        assertThat(termId.getIndividualNamePrefix().get(), is(indPrefix));
    }

    @Test
    public void testGetIndividualIdSuffix() {
        assertThat(termId.getIndividualNameSuffix(), is(Optional.empty()));
    }

    @Test(expected=NoSuchElementException.class)
    public void testGetIndividualIdSuffixThrowsException() {
        termId.getIndividualNameSuffix().get();
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