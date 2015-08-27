package edu.stanford.protege.metaproject.api;

/**
 * A representation of a password "master" that handles password hashing as well as password validation
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface PasswordMaster extends PasswordHashGenerator, PasswordValidator {

    /**
     * Get the salt generator used by the password handler
     *
     * @return Salt generator
     */
    SaltGenerator getSaltGenerator();

}
