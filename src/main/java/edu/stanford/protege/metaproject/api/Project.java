package edu.stanford.protege.metaproject.api;

import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Project extends AccessControlObject, HasName, HasDescription, HasAddress, Comparable<Project> {

    ProjectId getId();

    UserId getOwner();

    Set<UserId> getAdministrators();

    boolean hasAdministrator(UserId user);

}
