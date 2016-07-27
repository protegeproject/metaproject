package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.IdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.ProjectNotInPolicyException;
import edu.stanford.protege.metaproject.api.exception.UserNotInPolicyException;

import java.util.Set;

/**
 * This interface provides an access point for high-level information contained in the registries. In particular, it allows one to verify
 * if a user is allowed to do some operation within a project, as well as to obtain sets of metaproject objects such as users, roles, etc.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface MetaprojectAgent {

    /**
     * Check whether the specified operation is allowed for the given user within the project
     *
     * @param operationId Operation identifier
     * @param projectId   Project identifier
     * @param userId  User identifier
     * @return true if user is allowed to carry out the specified operation within the project, false otherwise
     */
    boolean isOperationAllowed(OperationId operationId, ProjectId projectId, UserId userId);

    /**
     * Check whether the specified operation is allowed for the given user
     *
     * @param operationId Operation identifier
     * @param userId  User identifier
     * @return true if user is allowed to carry out the specified operation, false otherwise
     */
    boolean isOperationAllowed(OperationId operationId, UserId userId);

    /**
     * Get the set of projects that the user with the given identifier has some role assignments
     *
     * @param userId    User identifier
     * @return Set of projects
     * @throws UserNotInPolicyException User with given identifier not found in the access control policy
     */
    Set<Project> getProjects(UserId userId) throws UserNotInPolicyException;

    /**
     * Get the set of roles that the user with the given identifier has been assigned
     *
     * @param userId    User identifier
     * @param globalPermissions Whether global permissions should be taken into account
     * @return Set of roles
     * @throws UserNotInPolicyException User with given identifier not found in the access control policy
     */
    Set<Role> getRoles(UserId userId, GlobalPermissions globalPermissions) throws UserNotInPolicyException;

    /**
     * Get the set of roles that the user with the given identifier has been assigned in the project with the given identifier
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @param globalPermissions Whether global permissions should be taken into account
     * @return Set of roles
     * @throws UserNotInPolicyException User with given identifier not found in the access control policy
     * @throws ProjectNotInPolicyException  Project with given identifier not found in the access control policy
     */
    Set<Role> getRoles(UserId userId, ProjectId projectId, GlobalPermissions globalPermissions) throws UserNotInPolicyException, ProjectNotInPolicyException;

    /**
     * Get the set of operations that the user with the given identifier can perform
     *
     * @param userId    User identifier
     * @param globalPermissions Whether global permissions should be taken into account
     * @return Set of operations
     * @throws UserNotInPolicyException User with given identifier not found in the access control policy
     */
    Set<Operation> getOperations(UserId userId, GlobalPermissions globalPermissions) throws UserNotInPolicyException;

    /**
     * Get the set of operations that the user with the given identifier can perform in the project with the given identifier
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @param globalPermissions Whether global permissions should be taken into account
     * @return Set of operations
     * @throws UserNotInPolicyException User with given identifier not found in the access control policy
     * @throws ProjectNotInPolicyException  Project with given identifier not found in the access control policy
     */
    Set<Operation> getOperations(UserId userId, ProjectId projectId, GlobalPermissions globalPermissions) throws UserNotInPolicyException, ProjectNotInPolicyException;

    /**
     * Get the set of operations allowed by the given role set
     *
     * @param roles Set of roles
     * @return Set of operations
     */
    Set<Operation> getOperations(Set<Role> roles);

    /**
     * Get the set of operations allowed by the given role
     *
     * @param role  Role
     * @return Set of operations
     */
    Set<Operation> getOperations(Role role);

    /**
     * Add the given metaproject object (such as user, role, project, operation) to the appropriate registry
     *
     * @param obj   Metaproject object to add
     * @param <T>   Type of object
     * @throws IdAlreadyInUseException  Object identifier is already used by an object of the same type
     */
    <T extends MetaprojectObject> void add(T obj) throws IdAlreadyInUseException;

    /**
     * Remove the given metaproject object (such as user, role, project, operation) from the appropriate registry
     *
     * @param obj   Metaproject object to remove
     * @param <T>   Type of object
     */
    <T extends MetaprojectObject> void remove(T obj);

}
