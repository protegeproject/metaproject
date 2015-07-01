package edu.stanford.protege.metaproject.role;

import edu.stanford.protege.metaproject.operation.*;
import edu.stanford.protege.metaproject.project.Project;

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
     * @return Role identifier instance
     */
    RoleId getId();

    /**
     * Get the role description
     *
     * @return Role description instance
     */
    RoleDescription getDescription();

    /**
     * Get all projects associated with this role
     *
     * @return Set of projects
     */
    Set<Project> getProjects();

    /**
     * Get the set of allowed operations
     *
     * @return Set of operations
     */
    Set<Operation> getOperations();

}
