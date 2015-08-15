package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.Password;

import java.io.Serializable;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class SaltedPassword implements Password, Serializable {
    private static final long serialVersionUID = 3999269367644040407L;
    private final byte[] saltedPassword;

    /**
     * Constructor
     *
     * @param saltedPassword    Salted password bytes array
     */
    public SaltedPassword(byte[] saltedPassword) {
        this.saltedPassword = checkNotNull(saltedPassword);
    }

    /**
     * Get the salted password bytes
     *
     * @return Salted password bytes array
     */
    public byte[] getBytes() {
        return saltedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaltedPassword that = (SaltedPassword) o;
        return Arrays.equals(saltedPassword, that.saltedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(saltedPassword);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("saltedPassword", saltedPassword)
                .toString();
    }
}
