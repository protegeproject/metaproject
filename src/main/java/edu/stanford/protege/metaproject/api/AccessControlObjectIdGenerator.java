package edu.stanford.protege.metaproject.api;

/**
 * A generator of identifiers for access control objects, such as users, projects, operations,...
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface AccessControlObjectIdGenerator {

    /**
     * Get a user identifier
     *
     * @return New user identifier
     */
    UserId createUserId();

    /**
     * Get a new role identifier
     *
     * @return New role identifier
     */
    RoleId createRoleId();

    /**
     * Get a new project identifier
     *
     * @return New project identifier
     */
    ProjectId createProjectId();

    /**
     * Get a new operation identifier
     *
     * @return New operation identifier
     */
    OperationId createOperationId();

}
