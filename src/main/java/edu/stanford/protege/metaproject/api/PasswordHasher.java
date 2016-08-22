package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * A representation of a salt-based password hashing function
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface PasswordHasher {

    /**
     * Create a salted hash of the password with the given salt
     *
     * @param password  Plain password to hash
     * @param salt  Salt
     * @return Salted hash of the password
     */
    @Nonnull
    SaltedPasswordDigest hash(@Nonnull PlainPassword password, @Nonnull Salt salt);

}
