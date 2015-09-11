package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.MetaprojectException;

import java.util.Set;

/**
 * A representation of an access control policy where users are associated with roles. Roles have
 * a defined set of operations the user is allowed to perform within a set of projects.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Metaproject {

    /**
     * Check whether the specified operation is allowed for the given user
     *
     * @param operationId Operation identifier
     * @param projectId   Project identifier
     * @param userId  User identifier
     * @return true if user is allowed to carry out the specified operation, false otherwise
     * @throws MetaprojectException Metaproject exception
     */
    boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId) throws MetaprojectException;

    /**
     * Get the set of operations allowed for a given user in a specific project
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @return Set of operations
     * @throws MetaprojectException Metaproject exception
     */
    Set<Operation> getOperationsInProject(UserId userId, ProjectId projectId) throws MetaprojectException;

    /**
     * Get the set of projects that a user plays some role in
     *
     * @param userId    User identifier
     * @return Set of projects
     * @throws MetaprojectException Metaproject exception
     */
    Set<Project> getProjects(UserId userId) throws MetaprojectException;

    /**
     * Get the set of roles that a given user has assigned
     *
     * @param userId    User identifier
     * @return Set of roles
     * @throws MetaprojectException Metaproject exception
     */
    Set<Role> getRoles(UserId userId) throws MetaprojectException;

    /**
     * Get the set of users that have some role in the specified project
     *
     * @param projectId    Project identifier
     * @return Set of users
     * @throws MetaprojectException Metaproject exception
     */
    Set<User> getUsers(ProjectId projectId) throws MetaprojectException;

    /**
     * Get the policy manager, which is responsible for handling the assignments
     * of users (by their identifiers) to roles (as a set of role identifiers).
     *
     * @return Policy manager
     */
    PolicyManager getPolicyManager();

    /**
     * Get the role manager
     *
     * @return Role manager
     */
    RoleManager getRoleManager();

    /**
     * Get the operation manager
     *
     * @return Operation manager
     */
    OperationManager getOperationManager();

    /**
     * Get the user manager
     *
     * @return User manager
     */
    UserManager getUserManager();

    /**
     * Get the project manager
     *
     * @return Project manager
     */
    ProjectManager getProjectManager();

}
