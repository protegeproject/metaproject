package edu.stanford.protege.metaproject.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
@RunWith(MockitoJUnitRunner.class)
public class AddressTest {
    private static final String
            addressStr = "testAddress",
            diffAddressStr = "diffTestAddress",
            toStringHead = "Address";

    private Address address, otherAddress, diffAddress;

    @Before
    public void setUp() {
        address = TestUtils.getAddress(addressStr);
        otherAddress = TestUtils.getAddress(addressStr);
        diffAddress = TestUtils.getAddress(diffAddressStr);
    }

    @Test
    public void testNotNull() {
        assertThat(address, is(not(equalTo(null))));
    }

    @Test
    public void testGet() {
        assertThat(address.get(), is(addressStr));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(address, is(equalTo(address)));
    }

    @Test
    public void testEquals() {
        assertThat(address, is(equalTo(otherAddress)));
    }

    @Test
    public void testNotEquals() {
        assertThat(address, is(not(equalTo(diffAddress))));
    }

    @Test
    public void testHashCode() {
        assertThat(address.hashCode(), is(otherAddress.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(address.toString(), startsWith(toStringHead));
    }
}