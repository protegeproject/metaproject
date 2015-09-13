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
public class EntityIriTest {
    private static final EntityIriPrefix prefix1 = Utils.getEntityIriPrefix(), prefix2 = Utils.getEntityIriPrefix();
    private static final EntityName suffix1 = Utils.getEntityName(), suffix2 = Utils.getEntityName();
    private static final String toStringHead = "EntityIri";

    private EntityIri entityIri, otherEntityIri, diffEntityIri;

    @Before
    public void setUp() {
        entityIri = Utils.getEntityIri(prefix1, suffix1);
        otherEntityIri = Utils.getEntityIri(prefix1, suffix1);
        diffEntityIri = Utils.getEntityIri(prefix2, suffix2);
    }

    @Test
    public void testNotNull() {
        assertThat(entityIri, is(not(equalTo(null))));
    }

    @Test
    public void testGetPrefix() {
        assertThat(entityIri.getIriPrefix(), is(prefix1));
    }

    @Test
    public void testGetSuffix() {
        assertThat(entityIri.getEntityName(), is(suffix1));
    }

    @Test
    public void testGet() {
        assertThat(entityIri.get(), is(IRI.create(prefix1.get().concat(suffix1.get()))));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(entityIri, is(entityIri));
    }

    @Test
    public void testEquals() {
        assertThat(entityIri, is(otherEntityIri));
    }

    @Test
    public void testNotEquals() {
        assertThat(entityIri, is(not(diffEntityIri)));
    }

    @Test
    public void testHashCode() {
        assertThat(entityIri.hashCode(), is(otherEntityIri.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(entityIri.toString(), startsWith(toStringHead));
    }
}