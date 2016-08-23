package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.SaltGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class SaltGeneratorTest {
    private SaltGenerator saltGenerator, otherSaltGenerator, diffSalt;

    @Before
    public void setUp() {
        saltGenerator = new SaltGeneratorImpl();
        otherSaltGenerator = new SaltGeneratorImpl();
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
        assertThat(saltGenerator.getByteLength(), is(otherSaltGenerator.getByteLength()));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(saltGenerator, is(saltGenerator));
    }

    @Test
    public void testNotEquals() {
        assertThat(saltGenerator, is(not(diffSalt)));
    }
}