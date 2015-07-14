package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of an email address belonging to a user
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class EmailAddress implements Serializable {
    private final String emailAddress;

    /**
     * Constructor
     *
     * @param emailAddress  Email address
     */
    public EmailAddress(String emailAddress) {
        this.emailAddress = checkNotNull(emailAddress);
    }

    /**
     * Get the email address
     *
     * @return Email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddress that = (EmailAddress) o;
        return Objects.equal(emailAddress, that.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(emailAddress);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("emailAddress", emailAddress)
                .toString();
    }
}
