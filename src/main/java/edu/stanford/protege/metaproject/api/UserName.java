package edu.stanford.protege.metaproject.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A representation of a user's name that is used for displaying purposes
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserName implements Serializable, HasName {
    private final String userName;

    /**
     * Constructor
     *
     * @param userName  User name
     */
    public UserName(String userName) {
        this.userName = checkNotNull(userName);
    }

    @Override
    public String getName() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserName userName1 = (UserName) o;
        return Objects.equal(userName, userName1.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userName", userName)
                .toString();
    }
}
