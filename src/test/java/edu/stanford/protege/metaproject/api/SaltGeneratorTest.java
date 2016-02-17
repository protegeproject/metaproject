package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.impl.SaltGeneratorImpl;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class SaltGeneratorTest {
    private static final String toStringHead = "SaltGenerator";
    private static final int nrBytes = 16;

    private SaltGenerator saltGenerator, otherSaltGenerator, diffSalt;

    @Before
    public void setUp() {
        saltGenerator = new SaltGeneratorImpl(nrBytes);
        otherSaltGenerator = new SaltGeneratorImpl(nrBytes);
        diffSalt = new SaltGeneratorImpl();
    }

    @Test
    public void testNotNull() {
        assertThat(saltGenerator, is(not(equalTo(null))));
    }

    @Test
    public void testGenerate() {
        assertThat(saltGenerator.generate(), is(not(equalTo(null))));
    }

    @Test
    public void testGeneration() {
        assertThat(saltGenerator.generate(), is(not(saltGenerator.generate())));
    }

    @Test
    public void testGetByteLength() {
        assertThat(saltGenerator.getByteLength(), is(nrBytes));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(saltGenerator, is(saltGenerator));
    }

    @Test
    public void testEquals() {
        assertThat(saltGenerator, is(otherSaltGenerator));
    }

    @Test
    public void testNotEquals() {
        assertThat(saltGenerator, is(not(diffSalt)));
    }

    @Test
    public void testHashCode() {
        assertThat(saltGenerator.hashCode(), is(otherSaltGenerator.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(saltGenerator.toString(), startsWith(toStringHead));
    }
}