package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.EmailAddress;

import javax.annotation.Nonnull;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class EmailAddressImpl implements EmailAddress, Serializable {
    private static final long serialVersionUID = 4240354589977877664L;
    private final String email;

    /**
     * Constructor
     *
     * @param email    Email address
     */
    public EmailAddressImpl(String email) {
        this.email = checkNotNull(email);
    }

    @Override
    public String get() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddressImpl that = (EmailAddressImpl) o;
        return Objects.equal(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("email", email)
                .toString();
    }

    @Override
    public int compareTo(@Nonnull EmailAddress that) {
        return ComparisonChain.start()
                .compare(email, that.get())
                .result();
    }
}
