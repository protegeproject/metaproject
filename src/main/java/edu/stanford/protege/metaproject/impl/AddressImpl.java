package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Address;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class AddressImpl implements Address, Serializable {
    private static final long serialVersionUID = 5957882695092904490L;
    private final String address;

    /**
     * Constructor
     *
     * @param address    Address
     */
    public AddressImpl(String address) {
        this.address = checkNotNull(address);
    }

    @Override
    public String get() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressImpl that = (AddressImpl) o;
        return Objects.equal(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("address", address)
                .toString();
    }
}
