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
public class AddressTest {
    private static final String
            addressStr = "testAddress",
            diffAddressStr = "diffTestAddress",
            toStringHead = Address.class.getSimpleName();

    private Address address, otherAddress, diffAddress;

    @Before
    public void setUp() {
        address = Utils.getAddress(addressStr);
        otherAddress = Utils.getAddress(addressStr);
        diffAddress = Utils.getAddress(diffAddressStr);
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
        assertThat(address, is(address));
    }

    @Test
    public void testEquals() {
        assertThat(address, is(otherAddress));
    }

    @Test
    public void testNotEquals() {
        assertThat(address, is(not(diffAddress)));
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