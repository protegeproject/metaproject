package edu.stanford.protege.metaproject.authentication;

import edu.stanford.protege.metaproject.user.User;

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
     * @param user  User instance
     * @return Salt used in the given user's password hashing
     */
    String getSalt(User user);

}
