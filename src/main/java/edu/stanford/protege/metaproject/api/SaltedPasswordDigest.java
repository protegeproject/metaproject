package edu.stanford.protege.metaproject.api;

/**
 * A representation of a salted password digest, consisting of the password and the salt used to hash it
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface SaltedPasswordDigest extends Password {

    /**
     * Get the salt used for hashing this password
     *
     * @return Salt used in hashing function
     */
    Salt getSalt();

}
