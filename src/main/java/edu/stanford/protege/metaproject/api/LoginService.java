package edu.stanford.protege.metaproject.api;

/**
 * A representation of login service that verifies user access through credential
 * checking. This credential is usually represented as a combination of user name
 * and password.
 *
 * @author Josef Hardi <johardi@stanford.edu> <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface LoginService {

    /**
     * A concrete login mechanism where user's credentials are being checked. This method
     * will return an authentication token {@code AuthToken} that will be functioned as
     * a security access ID.
     *
     * @param username  User identifier
     * @param password  User password
     * @return An authentication token
     * @throws Exception
     */
    AuthToken login(UserId username, SaltedPasswordDigest password) throws Exception;
}
