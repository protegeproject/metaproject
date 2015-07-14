package edu.stanford.protege.metaproject;

/**
 * A representation of an MD5-based authenticator
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface MD5Authenticator extends Authenticator {

    /**
     * Get the salt data used for the given user's password hashing
     *
     * @param userId  User unique identifier as used to login
     * @return Salt used in the given user's password hashing
     */
    Salt getSalt(UserId userId);

}