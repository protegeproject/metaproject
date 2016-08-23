package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import edu.stanford.protege.metaproject.api.EmailAddress;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class EmailAddressImpl implements EmailAddress, Serializable {
    private static final long serialVersionUID = 4240354589977877664L;
    @Nonnull private final String email;

    /**
     * Constructor
     *
     * @param email    Email address
     */
    public EmailAddressImpl(@Nonnull String email) {
        this.email = checkNotNull(email);
    }

    @Override
    @Nonnull
    public String get() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailAddress)) {
            return false;
        }
        EmailAddress that = (EmailAddress) o;
        return Objects.equal(email, that.get());
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
