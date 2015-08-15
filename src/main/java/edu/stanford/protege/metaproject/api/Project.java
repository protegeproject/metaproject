package edu.stanford.protege.metaproject.api;

import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Project extends AccessControlObject, HasDescription, HasAddress {

    User getOwner();

    Set<User> getAdministrators();

    boolean hasAdministrator(User user);

}
