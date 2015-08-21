package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface PasswordValidator {

    boolean validatePassword(String password, String correctHash);

    boolean validatePassword(PlainPassword password, SaltedPassword correctHash);

}
