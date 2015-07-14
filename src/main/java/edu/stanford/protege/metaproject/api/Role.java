package edu.stanford.protege.metaproject.api;

import java.util.Set;

/**
 * A role defines a group of allowed operations within some project
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Role {

    /**
     * Get the role identifier
     *
     * @return Role identifier
     */
    RoleId getId();

    /**
     * Get the role name
     * @return  Role name
     */
    RoleName getName();

    /**
     * Get the role description
     *
     * @return Role description
     */
    RoleDescription getDescription();

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
