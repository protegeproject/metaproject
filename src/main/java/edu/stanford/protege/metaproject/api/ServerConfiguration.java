package edu.stanford.protege.metaproject.api;

import com.google.common.collect.ImmutableMap;
import java.util.Set;
import edu.stanford.protege.metaproject.api.exception.UnknownOperationIdException;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;
import edu.stanford.protege.metaproject.api.exception.UnknownRoleIdException;
import edu.stanford.protege.metaproject.api.exception.UnknownUserIdException;
import com.google.common.collect.ImmutableSet;
import edu.stanford.protege.metaproject.api.exception.UnknownProjectIdException;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A representation of a server configuration, composed of host information, the access control policy, the root directory of the
 * server (where project files are located), the authentication registry, and optional additional configuration properties.
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface ServerConfiguration {

	/**
	 * Get the server host
	 *
	 * @return Server host
	 */
	@Nonnull
	Host getHost();

	/**
	 * Get the root directory of the server, where (ontology) project files are located
	 *
	 * @return Server root directory
	 */
	@Nonnull
	String getServerRoot();

	/* users */

	/**
	 * Get the user with the specified user identifier
	 *
	 * @param userId    User identifier
	 * @return User instance
	 * @throws UnknownUserIdException   User identifier does not exist in the configuration
	 */
	@Nonnull
	User getUser(@Nonnull UserId userId) throws UnknownUserIdException;

	/**
	 * Get the set of user in the configuration
	 *
	 * @return  Set of users
	 */
	@Nonnull
	ImmutableSet<User> getUsers();

	/**
	 * Get the user(s) registered with the specified name
	 *
	 * @param userName  User name instance
	 * @return Set of users with given name
	 */
	@Nonnull
	Set<User> getUsers(@Nonnull Name userName);

	/**
	 * Get the user(s) registered with the specified email address
	 *
	 * @param emailAddress  Email address
	 * @return Set of users
	 */
	@Nonnull
	Set<User> getUsers(@Nonnull EmailAddress emailAddress);

	/**
	 * Get the set of user identifiers that have some role in the specified project
	 *
	 * @param projectId    Project identifier
	 * @return Set of user identifiers
	 */
	@Nonnull
	Set<UserId> getUserIds(@Nonnull ProjectId projectId);

	/**
	 * Check whether the configuration contains the given user
	 *
	 * @param user  User
	 * @return true if configuration contains user, false otherwise
	 */
	boolean containsUser(@Nonnull User user);

	/**
	 * Check whether the configuration contains a user with the given user identifier
	 *
	 * @param userId    User identifier
	 * @return true if configuration contains user, false otherwise
	 */
	boolean containsUser(@Nonnull UserId userId);

	/**
	 * Verify whether the email address of the given user is already being used by another user
	 *
	 * @param address   User address
	 * @return true if email address is used by some other user, false otherwise
	 */
	boolean isEmailAddressInUse(@Nonnull EmailAddress address);



	/* projects */

	/**
	 * Get the project with the given identifier
	 *
	 * @param projectId Project identifier
	 * @return Project
	 * @throws UnknownProjectIdException    Project identifier does not exist in the configuration
	 */
	@Nonnull
	Project getProject(@Nonnull ProjectId projectId) throws UnknownProjectIdException;

	/**
	 * Get the set of projects in the configuration
	 *
	 * @return Set of projects
	 */
	@Nonnull
	ImmutableSet<Project> getProjects();

	/**
	 * Get the set of projects with the specified project name
	 *
	 * @param projectName   Project name
	 * @return Set of projects
	 */
	@Nonnull
	Set<Project> getProjects(@Nonnull Name projectName);

	/**
	 * Get the set of projects that the user with the given identifier has some role assignments
	 *
	 * @param userId    User identifier
	 * @return Set of projects
	 */
	@Nonnull
	Set<Project> getProjects(@Nonnull UserId userId);

	/**
	 * Get the set of project identifiers that the given user is assigned to
	 *
	 * @param userId    User identifier
	 * @return Set of project identifiers the user works on
	 */
	@Nonnull
	Set<ProjectId> getProjectIds(@Nonnull UserId userId);

	/**
	 * Check whether the configuration contains the given project
	 *
	 * @param project   Project
	 * @return true if configuration contains project, false otherwise
	 */
	boolean containsProject(@Nonnull Project project);

	/**
	 * Check whether the configuration contains a project with the given identifier
	 *
	 * @param projectId Project identifier
	 * @return true if configuration contains project, false otherwise
	 */
	boolean containsProject(@Nonnull ProjectId projectId);



	/* roles */

	/**
	 * Get the role with the given identifier
	 *
	 * @param roleId    Role identifier
	 * @return Role instance
	 * @throws UnknownRoleIdException   Role identifier does not exist in the configuration
	 */
	@Nonnull
	Role getRole(@Nonnull RoleId roleId) throws UnknownRoleIdException;

	/**
	 * Get the set of roles in the configuration
	 *
	 * @return Set of roles
	 */
	@Nonnull
	ImmutableSet<Role> getRoles();

	/**
	 * Get the set roles for the user with the given user identifier, considering global permissions depending on the specified enumeration value
	 *
	 * @param userId    User identifier
	 * @param globalPermissions Whether global permissions should be taken into account
	 * @return Set of roles
	 */
	@Nonnull
	Set<Role> getRoles(@Nonnull UserId userId, @Nonnull GlobalPermissions globalPermissions);

	/**
	 * Get the set roles for the user with the given user identifier within the given project, considering global permissions depending on the
	 * specified enumeration value
	 *
	 * @param userId    User identifier
	 * @param projectId Project identifier
	 * @param globalPermissions Whether global permissions should be taken into account
	 * @return Set of roles
	 */
	@Nonnull
	Set<Role> getRoles(@Nonnull UserId userId, @Nonnull ProjectId projectId, @Nonnull GlobalPermissions globalPermissions);

	/**
	 * Get the set of role identifiers that a given user has assigned within the specified project
	 *
	 * @param userId    User identifier
	 * @param projectId Project identifier
	 * @param globalPermissions Whether global permissions should be taken into account
	 * @return Set of role identifiers
	 */
	@Nonnull
	Set<RoleId> getRoleIds(@Nonnull UserId userId, @Nonnull ProjectId projectId, @Nonnull GlobalPermissions globalPermissions);

	/**
	 * Get the set of role identifiers that a given user has assigned
	 *
	 * @param userId    User identifier
	 * @param globalPermissions Whether global permissions should be taken into account
	 * @return Set of role identifiers
	 */
	@Nonnull
	Set<RoleId> getRoleIds(@Nonnull UserId userId, @Nonnull GlobalPermissions globalPermissions);

	/**
	 * Check whether the configuration contains the given role
	 *
	 * @param role  Role
	 * @return true if configuration contains role, false otherwise
	 */
	boolean containsRole(@Nonnull Role role);

	/**
	 * Check whether the configuration contains a role with the given role identifier
	 *
	 * @param roleId    Role identifier
	 * @return true if configuration contains role, false otherwise
	 */
	boolean containsRole(@Nonnull RoleId roleId);



	/* operations */

	/**
	 * Get the operation with the specified operation identifier
	 *
	 * @param operationId   Operation identifier
	 * @return Operation instance
	 * @throws UnknownOperationIdException  Operation identifier does not exist in the configuration
	 */
	@Nonnull
	Operation getOperation(@Nonnull OperationId operationId) throws UnknownOperationIdException;

	/**
	 * Get the set of operations in the configuration
	 *
	 * @return Set of operations
	 */
	@Nonnull
	ImmutableSet<Operation> getOperations();

	/**
	 * Get the set of operations that the user with the given identifier can perform
	 *
	 * @param userId    User identifier
	 * @param globalPermissions Whether global permissions should be taken into account
	 * @return Set of operations
	 */
	@Nonnull
	Set<Operation> getOperations(@Nonnull UserId userId, @Nonnull GlobalPermissions globalPermissions);

	/**
	 * Get the set of operations that the user with the given identifier can perform in the project with the given identifier
	 *
	 * @param userId    User identifier
	 * @param projectId Project identifier
	 * @param globalPermissions Whether global permissions should be taken into account
	 * @return Set of operations
	 */
	@Nonnull
	Set<Operation> getOperations(@Nonnull UserId userId, @Nonnull ProjectId projectId, @Nonnull GlobalPermissions globalPermissions);

	/**
	 * Get the set of operations allowed by the given role set
	 *
	 * @param roles Set of roles
	 * @return Set of operations
	 */
	@Nonnull
	Set<Operation> getOperations(@Nonnull Set<Role> roles);

	/**
	 * Get the set of operations allowed by the given role
	 *
	 * @param role  Role
	 * @return Set of operations
	 */
	@Nonnull
	Set<Operation> getOperations(@Nonnull Role role);

	/**
	 * Check whether the configuration contains the given operation
	 *
	 * @param operation Operation
	 * @return true if configuration contains operation, false otherwise
	 */
	boolean containsOperation(@Nonnull Operation operation);

	/**
	 * Check whether the configuration contains an operation with the specified identifier
	 *
	 * @param operationId   Operation identifier
	 * @return true if configuration contains operation, false otherwise
	 */
	boolean containsOperation(@Nonnull OperationId operationId);


	/* authentication */

	/**
	 * Get all entries of user authentication details
	 *
	 * @return Set of user authentication details
	 */
	@Nonnull
	ImmutableSet<AuthenticationDetails> getAuthenticationDetails();

	/**
	 * Get the authentication details for a user with the given identifier
	 *
	 * @param userId    User identifier
	 * @return Authentication details
	 * @throws UserNotRegisteredException   User is not registered
	 */
	@Nonnull
	AuthenticationDetails getAuthenticationDetails(@Nonnull UserId userId) throws UserNotRegisteredException;

	/**
	 * Get the cryptographic salt used for hashing the given user's password
	 *
	 * @param userId    User identifier
	 * @return Salt
	 * @throws UserNotRegisteredException   User is not registered
	 */
	@Nonnull
	Salt getSalt(@Nonnull UserId userId) throws UserNotRegisteredException;

	/**
	 * Verify whether the user with the given identifier exists in the registry
	 *
	 * @param userId    User identifier
	 * @return true if the identifier corresponds to an existing, registered user, false otherwise
	 */
	boolean isRegistered(@Nonnull UserId userId);

	/**
	 * Verify whether the given user-password pair is a valid (registered) one
	 *
	 * @param userId    User identifier
	 * @param password  Password
	 * @return true if user and password are valid w.r.t. the authentication details, false otherwise
	 */
	boolean hasValidCredentials(@Nonnull UserId userId, @Nonnull SaltedPasswordDigest password);



	/* access control policy */

	/**
	 * Check whether the specified operation is allowed for the given user within the project
	 *
	 * @param operationId Operation identifier
	 * @param projectId   Project identifier
	 * @param userId  User identifier
	 * @return true if user is allowed to carry out the specified operation within the project, false otherwise
	 */
	boolean isOperationAllowed(@Nonnull OperationId operationId, @Nonnull ProjectId projectId, @Nonnull UserId userId);

	/**
	 * Check whether the specified operation is allowed for the given user
	 *
	 * @param operationId Operation identifier
	 * @param userId  User identifier
	 * @return true if user is allowed to carry out the specified operation, false otherwise
	 */
	boolean isOperationAllowed(@Nonnull OperationId operationId, @Nonnull UserId userId);

	/**
	 * Check if a given user has the specified role
	 *
	 * @param userId  User unique identifier as used to login
	 * @param roleId  Role identifier
	 * @param projectId  Project identifier
	 * @return true if user has specified role, false otherwise
	 */
	boolean hasRole(@Nonnull UserId userId, @Nonnull ProjectId projectId, @Nonnull RoleId roleId);

	/**
	 * Check whether the policy manager contains any role assignments for
	 * the user with the given identifier in the specified project
	 *
	 * @param userId    User identifier
	 * @param projectId Project identifier
	 * @return true if user has role assignments in the specified project
	 */
	boolean hasRole(@Nonnull UserId userId, @Nonnull ProjectId projectId);

	/**
	 * Check whether the policy contains role assignments for the user with the given identifier in some project
	 *
	 * @param userId    User identifier
	 * @return true if policy has role assignments for the user with the specified identifier, false otherwise
	 */
	boolean hasRole(@Nonnull UserId userId);

	/**
	 * Get a map of user identifiers to their project-roles assignments
	 *
	 * @return Map of users to their project-role mappings
	 */
	@Nonnull
	ImmutableMap<UserId,Map<ProjectId,Set<RoleId>>> getPolicyMap();

	/**
	 * Get a map of project identifiers to the roles that a specified user can play
	 *
	 * @param userId    User identifier
	 * @return Map of projects to roles
	 */
	@Nonnull
	Map<ProjectId, Set<RoleId>> getUserRoleMap(@Nonnull UserId userId);


	/**
	 * Get the map of properties
	 *
	 * @return Map of string properties
	 */
	@Nonnull
	ImmutableMap<String,String> getProperties();

	/**
	 * Get the value for the given property key
	 *
	 * @param key   Property key
	 * @return Property value
	 */

	@Nullable
	String getProperty(@Nonnull String key);
}
