package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * A representation of a salted password digest, consisting of the password and the salt used to hash it
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface SaltedPasswordDigest extends Password {

    /**
     * Get the salt used for hashing this password
     *
     * @return Salt used in hashing function
     */
    @Nonnull
    Salt getSalt();

}
