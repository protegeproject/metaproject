package edu.stanford.protege.metaproject.api;

/**
 * A representation of a plain password, i.e., one that is not hashed
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface PlainPassword {

    /**
     * Get the password
     *
     * @return Password string
     */
    String getPassword();

}
