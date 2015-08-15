package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface UserAuthenticationDetails extends HasId {

    Password getPassword();

    Salt getSalt();

}
