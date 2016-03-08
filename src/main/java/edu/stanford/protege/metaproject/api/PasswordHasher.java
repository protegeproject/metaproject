package edu.stanford.protege.metaproject.api;

/**
 * A representation of a salt-based password hashing function
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface PasswordHasher {

    /**
     * Create a salted hash of the password with the given salt
     *
     * @param password  Plain password to hash
     * @param salt  Salt
     * @return Salted hash of the password
     */
    SaltedPasswordDigest hash(PlainPassword password, Salt salt);

}
