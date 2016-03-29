package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.*;
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
        UserId termId = gen.getUserUuid();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.get(), is(not(equalTo(""))));
    }

    @Test
    public void testGetOperationId() {
        OperationId termId = gen.getOperationUuid();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.get(), is(not(equalTo(""))));
    }

    @Test
    public void testGetProjectId() {
        ProjectId termId = gen.getProjectUuid();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.get(), is(not(equalTo(""))));
    }

    @Test
    public void testGetRoleId() {
        RoleId termId = gen.getRoleUuid();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.get(), is(not(equalTo(""))));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(gen, is(gen));
    }
}
