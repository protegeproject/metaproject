package edu.stanford.protege.metaproject.api;

import java.util.Optional;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface UserAuthenticationDetails {

    UserId getUserId();

    SaltedPassword getPassword();

    Optional<Salt> getSalt();

}
