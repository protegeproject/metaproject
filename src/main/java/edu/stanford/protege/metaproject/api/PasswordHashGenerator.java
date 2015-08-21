package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface PasswordHashGenerator {

    SaltedPassword createHash(String password);

    SaltedPassword createHash(PlainPassword password);

}
