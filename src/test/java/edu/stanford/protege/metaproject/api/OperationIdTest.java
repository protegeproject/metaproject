package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationIdTest {
    private static final String
            operationIdStr = "testOperationId1",
            diffIdStr = "testOperationId2",
            toStringHead = "OperationId";

    private OperationId operationId, otherOperationId, diffOperationId;

    @Before
    public void setUp() {
        operationId = Utils.getOperationId(operationIdStr);
        otherOperationId = Utils.getOperationId(operationIdStr);
        diffOperationId = Utils.getOperationId(diffIdStr);
    }

    @Test
    public void testNotNull() {
        assertThat(operationId, is(not(equalTo(null))));
    }

    @Test
    public void testGet() {
        assertThat(operationId.get(), is(operationIdStr));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(operationId, is(operationId));
    }

    @Test
    public void testEquals() {
        assertThat(operationId, is(otherOperationId));
    }

    @Test
    public void testNotEquals() {
        assertThat(operationId, is(not(diffOperationId)));
    }

    @Test
    public void testHashCode() {
        assertThat(operationId.hashCode(), is(otherOperationId.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(operationId.toString(), startsWith(toStringHead));
    }
}