package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.IRI;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
@RunWith(MockitoJUnitRunner.class)
public class EntityIriTest {
    private static final String toStringHead = EntityIri.class.getSimpleName();

    @Mock private EntityIriPrefix prefix1, prefix2;
    @Mock private EntityName suffix1, suffix2;

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
        when(prefix1.get()).thenReturn("testPrefix");
        when(suffix1.get()).thenReturn("testSuffix");
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