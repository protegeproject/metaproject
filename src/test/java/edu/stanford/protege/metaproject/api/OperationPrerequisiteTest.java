package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationPrerequisiteTest {
    private static final String toStringHead = OperationPrerequisite.class.getSimpleName();
    private static final IRI iri = Utils.getIRI(), diffIri = Utils.getIRI();
    private static final OperationPrerequisite.Modifier mod = OperationPrerequisite.Modifier.ABSENT;

    private OperationPrerequisite prerequisite, otherPrerequisite, diffPrerequisite;

    @Before
    public void setUp() {
        prerequisite = Utils.getOperationPrerequisite(iri, mod);
        otherPrerequisite = Utils.getOperationPrerequisite(iri, mod);
        diffPrerequisite = Utils.getOperationPrerequisite(diffIri, mod);
    }

    @Test
    public void testNotNull() {
        assertThat(prerequisite, is(not(equalTo(null))));
    }

    @Test
    public void testGetPrerequisiteIRI() {
        assertThat(prerequisite.getPrerequisiteIri(), is(iri));
    }

    @Test
    public void testGetModifier() {
        assertThat(prerequisite.getModifier(), is(mod));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(prerequisite, is(prerequisite));
    }

    @Test
    public void testEquals() {
        assertThat(prerequisite, is(otherPrerequisite));
    }

    @Test
    public void testNotEquals() {
        assertThat(prerequisite, is(not(diffPrerequisite)));
    }

    @Test
    public void testHashCode() {
        assertThat(prerequisite.hashCode(), is(otherPrerequisite.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(prerequisite.toString(), startsWith(toStringHead));
    }
}