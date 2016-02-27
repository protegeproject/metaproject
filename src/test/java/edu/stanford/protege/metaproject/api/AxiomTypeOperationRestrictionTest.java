package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.impl.AxiomTypeRestriction;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.AxiomType;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AxiomTypeOperationRestrictionTest {
    private static final String toStringHead = AxiomTypeRestriction.class.getSimpleName();
    private static final AxiomType axType = AxiomType.ANNOTATION_ASSERTION, diffAxType = AxiomType.CLASS_ASSERTION;
    private static final Modality mod = Modality.AxiomChange.REMOVAL;

    private OperationRestriction restriction, otherRestriction, diffRestriction;

    @Before
    public void setUp() {
        restriction = Utils.getOperationRestriction(axType, mod);
        otherRestriction = Utils.getOperationRestriction(axType, mod);
        diffRestriction = Utils.getOperationRestriction(diffAxType, mod);
    }

    @Test
    public void testNotNull() {
        assertThat(restriction, is(not(equalTo(null))));
    }

    @Test
    public void testGetRestriction() {
        assertThat(restriction.getRestriction(), is(axType));
    }

    @Test
    public void testGetModifier() {
        assertThat(restriction.getModality(), is(mod));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(restriction, is(restriction));
    }

    @Test
    public void testEquals() {
        assertThat(restriction, is(otherRestriction));
    }

    @Test
    public void testNotEquals() {
        assertThat(restriction, is(not(diffRestriction)));
    }

    @Test
    public void testHashCode() {
        assertThat(restriction.hashCode(), is(otherRestriction.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(restriction.toString(), startsWith(toStringHead));
    }
}