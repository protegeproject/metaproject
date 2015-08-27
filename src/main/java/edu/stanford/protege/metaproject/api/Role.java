package edu.stanford.protege.metaproject.api;

import java.util.Set;

/**
 * A role defines a set of allowed operations within a set of projects
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Role extends AccessControlObject, HasName, HasDescription, Comparable<Role> {

    /**
     * Get the role identifier
     *
     * @return Role identifier
     */
    RoleId getId();

    /**
     * Get all projects associated with this role
     *
     * @return Set of project identifiers
     */
    Set<ProjectId> getProjects();

    /**
     * Get the set of operations associated with this role
     *
     * @return Set of operations identifiers
     */
    Set<OperationId> getOperations();

}
