package edu.stanford.protege.metaproject.api;

/**
 * A representation of a password validator, that checks whether the given password is valid
 * with respect to the specified (correct) password
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface PasswordValidator {

    /**
     * Validates a password against the expected hash
     *
     * @param password  The password to check
     * @param correctHash   The hash of the valid password
     * @return true if the password is correct, false otherwise
     */
    boolean validatePassword(String password, String correctHash);

    /**
     * Validates a password using a hash
     *
     * @param password  Plain password instance to check
     * @param correctHash   The correct salted hashed password instance
     * @return true if the password is correct, false otherwise
     */
    boolean validatePassword(PlainPassword password, SaltedPassword correctHash);

}
