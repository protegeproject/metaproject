package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.impl.AccessControlObjectUuidGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AccessControlObjectUuidGeneratorTest {
    private static final String toStringHead = "AccessControlObjectUuidGenerator";

    private AccessControlObjectUuidGenerator gen;

    @Before
    public void setUp() {
        gen = new AccessControlObjectUuidGenerator();
    }

    @Test
    public void testNotNull() {
        assertThat(gen, is(not(equalTo(null))));
    }

    @Test
    public void testGetUserId() {
        AccessControlObjectId termId = gen.createUserId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.get(), is(not(equalTo(""))));
    }

    @Test
    public void testGetOperationId() {
        AccessControlObjectId termId = gen.createOperationId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.get(), is(not(equalTo(""))));
    }

    @Test
    public void testGetProjectId() {
        AccessControlObjectId termId = gen.createProjectId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.get(), is(not(equalTo(""))));
    }

    @Test
    public void testGetRoleId() {
        AccessControlObjectId termId = gen.createRoleId();
        assertThat(termId, is(not(equalTo(null))));
        assertThat(termId.get(), is(not(equalTo(""))));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(gen, is(gen));
    }

    @Test
    public void testToString() {
        assertThat(gen.toString(), startsWith(toStringHead));
    }
}
