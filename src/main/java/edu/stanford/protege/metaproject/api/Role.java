package edu.stanford.protege.metaproject.api;

import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Role extends AccessControlObject, HasName, HasDescription {

    RoleId getId();

    Set<ProjectId> getProjects();

    Set<OperationId> getOperations();

}
