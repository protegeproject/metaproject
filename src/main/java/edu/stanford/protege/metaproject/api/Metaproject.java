package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.ProjectNotInPolicyException;
import edu.stanford.protege.metaproject.api.exception.UnknownAccessControlObjectIdException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;

/**
 * A representation of a "metaproject" configuration, which contains a policy that defines what
 * users have what roles on which projects, and the role, user, operation, and project registries.
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
     * @throws UnknownAccessControlObjectIdException Unknown access control object exception
     * @throws UserNotInPolicyException UserId not registered in the access control policy
     * @throws ProjectNotInPolicyException  Project not registered in the access control policy
     */
    boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId)
            throws UnknownAccessControlObjectIdException, UserNotInPolicyException, ProjectNotInPolicyException;

    /**
     * Get the access control policy, where users get assigned roles within projects
     *
     * @return Policy
     */
    Policy getPolicy();

    /**
     * Get the role registry, which allows one to create, remove, or modify roles
     *
     * @return Role registry
     */
    RoleRegistry getRoleRegistry();

    /**
     * Get the operation registry
     *
     * @return Operation registry
     */
    OperationRegistry getOperationRegistry();

    /**
     * Get the user registry that is responsible for creating, removing or modifying users
     *
     * @return User registry
     */
    UserRegistry getUserRegistry();

    /**
     * Get the project registry
     *
     * @return Project registry
     */
    ProjectRegistry getProjectRegistry();

}
