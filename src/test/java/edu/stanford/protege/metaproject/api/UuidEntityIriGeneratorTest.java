package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.impl.UuidEntityIriGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UuidEntityIriGeneratorTest {
    private static final String toStringHead = UuidEntityIriGenerator.class.getSimpleName();
    private static final EntityIriPrefix iriPrefix = Utils.getEntityIriPrefix();
    private EntityIriGenerator gen, otherGen;

    @Before
    public void setUp() {
        gen = new UuidEntityIriGenerator(iriPrefix);
        otherGen = new UuidEntityIriGenerator(iriPrefix);
    }

    @Test
    public void testNotNull() {
        assertThat(gen, is(not(equalTo(null))));
    }

    @Test
    public void testGetNextClassIri() {
        EntityIri entityIri = gen.getNextClassIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getIriPrefix(), is(iriPrefix));
        assertThat(entityIri.getEntityName().get(), is(not("")));
    }

    @Test
    public void testGetNextObjectPropertyIri() {
        EntityIri entityIri = gen.getNextObjectPropertyIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getIriPrefix(), is(iriPrefix));
        assertThat(entityIri.getEntityName().get(), is(not("")));
    }

    @Test
    public void testGetNextDataPropertyIri() {
        EntityIri entityIri = gen.getNextDataPropertyIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getIriPrefix(), is(iriPrefix));
        assertThat(entityIri.getEntityName().get(), is(not("")));
    }

    @Test
    public void testGetNextAnnotationPropertyIri() {
        EntityIri entityIri = gen.getNextAnnotationPropertyIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getIriPrefix(), is(iriPrefix));
        assertThat(entityIri.getEntityName().get(), is(not("")));
    }

    @Test
    public void testGetNextIndivIriualIri() {
        EntityIri entityIri = gen.getNextIndividualIri();
        assertThat(entityIri, is(not(equalTo(null))));
        assertThat(entityIri.getIriPrefix(), is(iriPrefix));
        assertThat(entityIri.getEntityName().get(), is(not("")));
    }

    @Test
    public void testGetCurrentOntologyentityIriStatus() {
        assertThat(gen.getEntityIriStatus(), is(Optional.empty()));
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