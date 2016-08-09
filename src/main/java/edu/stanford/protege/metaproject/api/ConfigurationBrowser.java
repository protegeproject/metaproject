package edu.stanford.protege.metaproject.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import edu.stanford.protege.metaproject.api.exception.*;

import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface ConfigurationBrowser {

    /* users */

    /**
     * Get the user with the specified user identifier
     *
     * @param userId    User identifier
     * @return User instance
     * @throws UnknownUserIdException   User identifier does not exist in the configuration
     */
    User getUser(UserId userId) throws UnknownUserIdException;

    /**
     * Get the set of user in the configuration
     *
     * @return  Set of users
     */
    ImmutableSet<User> getUsers();

    /**
     * Get the user(s) registered with the specified name
     *
     * @param userName  User name instance
     * @return Set of users with given name
     */
    Set<User> getUsers(Name userName);

    /**
     * Get the user(s) registered with the specified email address
     *
     * @param emailAddress  Email address
     * @return Set of users
     */
    Set<User> getUsers(EmailAddress emailAddress);

    /**
     * Get the set of user identifiers that have some role in the specified project
     *
     * @param projectId    Project identifier
     * @return Set of user identifiers
     */
    Set<UserId> getUserIds(ProjectId projectId);

    /**
     * Check whether the configuration contains the given user
     *
     * @param user  User
     * @return true if configuration contains user, false otherwise
     */
    boolean containsUser(User user);

    /**
     * Check whether the configuration contains a user with the given user identifier
     *
     * @param userId    User identifier
     * @return true if configuration contains user, false otherwise
     */
    boolean containsUser(UserId userId);

    /**
     * Verify whether the email address of the given user is already being used by another user
     *
     * @param address   User address
     * @return true if email address is used by some other user, false otherwise
     */
    boolean isEmailAddressInUse(EmailAddress address);



    /* projects */

    /**
     * Get the project with the given identifier
     *
     * @param projectId Project identifier
     * @return Project
     * @throws UnknownProjectIdException    Project identifier does not exist in the configuration
     */
    Project getProject(ProjectId projectId) throws UnknownProjectIdException;

    /**
     * Get the set of projects in the configuration
     *
     * @return Set of projects
     */
    ImmutableSet<Project> getProjects();

    /**
     * Get the set of projects with the specified project name
     *
     * @param projectName   Project name
     * @return Set of projects
     */
    Set<Project> getProjects(Name projectName);

    /**
     * Get the set of projects that the user with the given identifier has some role assignments
     *
     * @param userId    User identifier
     * @return Set of projects
     */
    Set<Project> getProjects(UserId userId);

    /**
     * Get the set of project identifiers that the given user is assigned to
     *
     * @param userId    User identifier
     * @return Set of project identifiers the user works on
     */
    Set<ProjectId> getProjectIds(UserId userId);

    /**
     * Check whether the configuration contains the given project
     *
     * @param project   Project
     * @return true if configuration contains project, false otherwise
     */
    boolean containsProject(Project project);

    /**
     * Check whether the configuration contains a project with the given identifier
     *
     * @param projectId Project identifier
     * @return true if configuration contains project, false otherwise
     */
    boolean containsProject(ProjectId projectId);



    /* roles */

    /**
     * Get the role with the given identifier
     *
     * @param roleId    Role identifier
     * @return Role instance
     * @throws UnknownRoleIdException   Role identifier does not exist in the configuration
     */
    Role getRole(RoleId roleId) throws UnknownRoleIdException;

    /**
     * Get the set of roles in the configuration
     *
     * @return Set of roles
     */
    ImmutableSet<Role> getRoles();

    /**
     * Get the set roles for the user with the given user identifier, considering global permissions depending on the specified enumeration value
     *
     * @param userId    User identifier
     * @param globalPermissions Whether global permissions should be taken into account
     * @return Set of roles
     */
    Set<Role> getRoles(UserId userId, GlobalPermissions globalPermissions);

    /**
     * Get the set roles for the user with the given user identifier within the given project, considering global permissions depending on the
     * specified enumeration value
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @param globalPermissions Whether global permissions should be taken into account
     * @return Set of roles
     * @throws ProjectNotInPolicyException  Project with given identifier not found in the access control policy
     */
    Set<Role> getRoles(UserId userId, ProjectId projectId, GlobalPermissions globalPermissions) throws ProjectNotInPolicyException;

    /**
     * Get the set of role identifiers that a given user has assigned within the specified project
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @param globalPermissions Whether global permissions should be taken into account
     * @return Set of role identifiers
     * @throws ProjectNotInPolicyException  Project with given identifier not found in the access control policy
     */
    Set<RoleId> getRoleIds(UserId userId, ProjectId projectId, GlobalPermissions globalPermissions) throws ProjectNotInPolicyException;

    /**
     * Get the set of role identifiers that a given user has assigned
     *
     * @param userId    User identifier
     * @param globalPermissions Whether global permissions should be taken into account
     * @return Set of role identifiers
     */
    Set<RoleId> getRoleIds(UserId userId, GlobalPermissions globalPermissions);

    /**
     * Check whether the configuration contains the given role
     *
     * @param role  Role
     * @return true if configuration contains role, false otherwise
     */
    boolean containsRole(Role role);

    /**
     * Check whether the configuration contains a role with the given role identifier
     *
     * @param roleId    Role identifier
     * @return true if configuration contains role, false otherwise
     */
    boolean containsRole(RoleId roleId);



    /* operations */

    /**
     * Get the operation with the specified operation identifier
     *
     * @param operationId   Operation identifier
     * @return Operation instance
     * @throws UnknownOperationIdException  Operation identifier does not exist in the configuration
     */
    Operation getOperation(OperationId operationId) throws UnknownOperationIdException;

    /**
     * Get the set of operations in the configuration
     *
     * @return Set of operations
     */
    ImmutableSet<Operation> getOperations();

    /**
     * Get the set of operations that the user with the given identifier can perform
     *
     * @param userId    User identifier
     * @param globalPermissions Whether global permissions should be taken into account
     * @return Set of operations
     */
    Set<Operation> getOperations(UserId userId, GlobalPermissions globalPermissions);

    /**
     * Get the set of operations that the user with the given identifier can perform in the project with the given identifier
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @param globalPermissions Whether global permissions should be taken into account
     * @return Set of operations
     * @throws ProjectNotInPolicyException  Project with given identifier not found in the access control policy
     */
    Set<Operation> getOperations(UserId userId, ProjectId projectId, GlobalPermissions globalPermissions) throws ProjectNotInPolicyException;

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
     * Check whether the configuration contains the given operation
     *
     * @param operation Operation
     * @return true if configuration contains operation, false otherwise
     */
    boolean containsOperation(Operation operation);

    /**
     * Check whether the configuration contains an operation with the specified identifier
     *
     * @param operationId   Operation identifier
     * @return true if configuration contains operation, false otherwise
     */
    boolean containsOperation(OperationId operationId);


    /* authentication */

    /**
     * Get all entries of user authentication details
     *
     * @return Set of user authentication details
     */
    ImmutableSet<AuthenticationDetails> getAuthenticationDetails();

    /**
     * Get the authentication details for a user with the given identifier
     *
     * @param userId    User identifier
     * @return Authentication details
     * @throws UserNotRegisteredException   User is not registered
     */
    AuthenticationDetails getAuthenticationDetails(UserId userId) throws UserNotRegisteredException;

    /**
     * Get the cryptographic salt used for hashing the given user's password
     *
     * @param userId    User identifier
     * @return Salt
     * @throws UserNotRegisteredException   User is not registered
     */
    Salt getSalt(UserId userId) throws UserNotRegisteredException;

    /**
     * Verify whether the user with the given identifier exists in the registry
     *
     * @param userId    User identifier
     * @return true if the identifier corresponds to an existing, registered user, false otherwise
     */
    boolean isRegistered(UserId userId);

    /**
     * Verify whether the given user-password pair is a valid (registered) one
     *
     * @param userId    User identifier
     * @param password  Password
     * @return true if user and password are valid w.r.t. the authentication details, false otherwise
     * @throws UserNotRegisteredException   User is not registered
     */
    boolean hasValidCredentials(UserId userId, SaltedPasswordDigest password) throws UserNotRegisteredException;



    /* access control policy */

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
     * Check if a given user has the specified role
     *
     * @param userId  User unique identifier as used to login
     * @param roleId  Role identifier
     * @param projectId  Project identifier
     * @return true if user has specified role, false otherwise
     */
    boolean hasRole(UserId userId, ProjectId projectId, RoleId roleId);

    /**
     * Check whether the policy manager contains any role assignments for
     * the user with the given identifier in the specified project
     *
     * @param userId    User identifier
     * @param projectId Project identifier
     * @return true if user has role assignments in the specified project
     */
    boolean hasRole(UserId userId, ProjectId projectId);

    /**
     * Check whether the policy contains role assignments for the user with the given identifier in some project
     *
     * @param userId    User identifier
     * @return true if policy has role assignments for the user with the specified identifier, false otherwise
     */
    boolean hasRole(UserId userId);

    /**
     * Get a map of user identifiers to their project-roles assignments
     *
     * @return Map of users to their project-role mappings
     */
    ImmutableMap<UserId,Map<ProjectId,Set<RoleId>>> getPolicyMap();

    /**
     * Get a map of project identifiers to the roles that a specified user can play
     *
     * @param userId    User identifier
     * @return Map of projects to roles
     */
    Map<ProjectId, Set<RoleId>> getUserRoleMap(UserId userId);

}
