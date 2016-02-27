package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.impl.FactoryImpl;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class FactoryTest {
    private Factory gen;

    @Before
    public void setUp() {
        gen = new FactoryImpl();
    }

    @Test
    public void testNotNull() {
        assertThat(gen, is(not(equalTo(null))));
    }

    @Test
    public void testGetUserId() {
        AccessControlObjectId termId = gen.createUserUuid();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.get(), is(not(equalTo(""))));
    }

    @Test
    public void testGetOperationId() {
        AccessControlObjectId termId = gen.createOperationUuid();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.get(), is(not(equalTo(""))));
    }

    @Test
    public void testGetProjectId() {
        AccessControlObjectId termId = gen.createProjectUuid();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.get(), is(not(equalTo(""))));
    }

    @Test
    public void testGetRoleId() {
        AccessControlObjectId termId = gen.createRoleUuid();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.get(), is(not(equalTo(""))));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(gen, is(gen));
    }
}
