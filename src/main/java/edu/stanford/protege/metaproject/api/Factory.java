package edu.stanford.protege.metaproject.api;

import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Factory {

    /**
     * Create a new project
     *
     * @param projectId Project identifier
     * @param name  Project name
     * @param description   Project description
     * @param file   Project file
     * @param ownerId   Project owner user identifier
     * @param options   Project options
     * @return New Project instance
     */
    Project getProject(ProjectId projectId, Name name, Description description, File file, UserId ownerId, Optional<ProjectOptions> options);

    /**
     * Create a new role
     *
     * @param roleId    Role identifier
     * @param name  Role name
     * @param description   Role description
     * @param operations    Set of operations
     * @return New role instance
     */
    Role getRole(RoleId roleId, Name name, Description description, Set<OperationId> operations);

    /**
     * Create a new read operation
     *
     * @param operationId   Operation identifier
     * @param name  Operation name
     * @param description   Operation description
     * @param operationType Operation type
     * @return New operation instance
     */
    Operation getServerOperation(OperationId operationId, Name name, Description description, OperationType operationType);

    /**
     * Create a new write operation
     *
     * @param operationId   Operation identifier
     * @param name  Operation name
     * @param description   Operation description
     * @param operationType Operation type
     * @return New operation instance
     */
    Operation getOntologyOperation(OperationId operationId, Name name, Description description, OperationType operationType);

    /**
     * Create a new execute operation
     *
     * @param operationId   Operation identifier
     * @param name  Operation name
     * @param description   Operation description
     * @param operationType Operation type
     * @return New operation instance
     */
    Operation getMetaprojectOperation(OperationId operationId, Name name, Description description, OperationType operationType);

    /**
     * Create a user with the given user identifier, name and email address
     *
     * @param userId    User identifier
     * @param userName  User name
     * @param emailAddress  User email address
     * @return A user instance
     */
    User getUser(UserId userId, Name userName, EmailAddress emailAddress);

    /**
     * Create an instance of authentication details
     *
     * @param userId    User identifier
     * @param password  Salted password digest
     * @return Authentication details
     */
    AuthenticationDetails getAuthenticationDetails(UserId userId, SaltedPasswordDigest password);

    /**
     * Create an instance of crypto salt
     *
     * @param salt  Salt string
     * @return Salt instance
     */
    Salt getSalt(String salt);

    /**
     * Create an instance of plain password
     *
     * @param password  Password string
     * @return Plain password
     */
    PlainPassword getPlainPassword(String password);

    /**
     * Create an instance of salted password
     *
     * @param password  Password string
     * @param salt  Salt
     * @return Salted password digest
     */
    SaltedPasswordDigest getSaltedPasswordDigest(String password, Salt salt);

    /**
     * Create a name for an access control object
     *
     * @param name  Name
     * @return Name instance
     */
    Name getName(String name);

    /**
     * Create a description for an access control object
     *
     * @param description   Description
     * @return Description instance
     */
    Description getDescription(String description);

    /**
     * Create an email address name for an access control object
     *
     * @param emailAddress  Email address
     * @return Email address instance
     */
    EmailAddress getEmailAddress(String emailAddress);

    /**
     * Create a new identifier for a role
     *
     * @param roleId    Role identifier
     * @return Role identifier instance
     */
    RoleId getRoleId(String roleId);

    /**
     * Create a new identifier for a user
     *
     * @param userId    User identifier
     * @return User identifier instance
     */
    UserId getUserId(String userId);

    /**
     * Create a new identifier for a project
     *
     * @param projectId Project identifier
     * @return Project identifier instance
     */
    ProjectId getProjectId(String projectId);

    /**
     * Create a new identifier for an operation
     *
     * @param operationId   Operation identifier
     * @return Operation identifier instance
     */
    OperationId getOperationId(String operationId);

    /**
     * Get a user ID that is a randomly generated UUID
     *
     * @return New user UUID
     */
    UserId getUserUuid();

    /**
     * Get a new role ID that is a randomly generated UUID
     *
     * @return New role UUID
     */
    RoleId getRoleUuid();

    /**
     * Get a new project ID that is a randomly generated UUID
     *
     * @return New project ID that is a randomly generated UUID
     */
    ProjectId getProjectUuid();

    /**
     * Get a new operation ID that is a randomly generated UUID
     *
     * @return New operation UUID
     */
    OperationId getOperationUuid();

    /**
     * Create an instance of Port
     *
     * @param portNr    Port number
     * @return Port instance
     */
    Port getPort(Integer portNr);

    /**
     * Create an instance of a Host
     *
     * @param address   Host address
     * @param secondaryPort  Optional secondary port
     * @return Host instance
     */
    Host getHost(URI address, Optional<Port> secondaryPort);

    /**
     * Get a URI based on the given string
     *
     * @param uri   URI string
     * @return URI
     */
    URI getUri(String uri);

    /**
     * Create an instance of a salt generator
     *
     * @return Salt generator
     */
    SaltGenerator getSaltGenerator();

    /**
     * Create an instance of a password hasher
     *
     * @return Password hasher
     */
    PasswordHasher getPasswordHasher();

    /**
     * Create an instance of a password hasher with the given hash byte size
     * and number of key-stretching iterations.
     *
     * @param hashByteSize  Hash byte size
     * @param nrIterations  Number of key-stretching iterations
     * @return Password hasher
     */
    PasswordHasher getPasswordHasher(int hashByteSize, int nrIterations);

    /**
     * Create an instance of an empty user registry
     *
     * @return User registry
     */
    UserRegistry getUserRegistry();

    /**
     * Create an instance of a user registry with the given users
     *
     * @param users Set of users
     * @return User registry
     */
    UserRegistry getUserRegistry(Set<User> users);

    /**
     * Create an instance of an empty project registry
     *
     * @return Project registry
     */
    ProjectRegistry getProjectRegistry();

    /**
     * Create an instance of a project registry with the given projects
     *
     * @param projects  Set of projects
     * @return Project registry
     */
    ProjectRegistry getProjectRegistry(Set<Project> projects);

    /**
     * Create an instance of an operation registry that contains default operations
     *
     * @return Operation registry
     */
    OperationRegistry getOperationRegistry();

    /**
     * Create an instance of an operation registry with the given operations
     *
     * @param operations    Set of operations
     * @return Operation registry
     */
    OperationRegistry getOperationRegistry(Set<Operation> operations);

    /**
     * Create an instance of a role registry that contains the default server-admin user
     *
     * @return Role registry
     */
    RoleRegistry getRoleRegistry();

    /**
     * Create an instance of a role registry with the given roles
     *
     * @param roles Set of roles
     * @return Role registry
     */
    RoleRegistry getRoleRegistry(Set<Role> roles);

    /**
     * Create an instance of an empty authentication manager
     *
     * @return Authentication manager
     */
    AuthenticationRegistry getAuthenticationRegistry();

    /**
     * Create an instance of an authentication manager with the given authentication credentials
     *
     * @param authenticationDetails   Set of authentication details
     * @return Authentication manager
     */
    AuthenticationRegistry getAuthenticationRegistry(Set<AuthenticationDetails> authenticationDetails);

    /**
     * Create an instance of an empty policy
     *
     * @return Policy
     */
    Policy getPolicy();

    /**
     * Create an instance of a policy with the given map of users to role associations within projects
     *
     * @param userRoleMap   Map of users to maps of projects to assigned roles
     * @return Policy
     */
    Policy getPolicy(Map<UserId, Map<ProjectId, Set<RoleId>>> userRoleMap);

    /**
     * Create an instance of a policy agent
     *
     * @param policy    Policy
     * @param roleRegistry  Role registry
     * @return Policy agent
     */
    PolicyChecker getPolicyAgent(Policy policy, RoleRegistry roleRegistry);

    /**
     * Get an authentication token that reflects a successful authentication attempt
     *
     * @param user    User
     * @return Authentication token
     */
    AuthToken getAuthorizedUserToken(User user);

    /**
     * Get an authentication token that reflects an unsuccessful authentication attempt
     *
     * @param user    User
     * @return Authentication token
     */
    AuthToken getUnauthorizedUserToken(User user);

    /**
     * Create an instance of project options
     *
     * @param requiredAnnotationsMap    Map of entities to the sets of annotation properties that must
     *                                  accompany each entity's addition to the ontology signature
     * @param optionalAnnotationsMap    Map of entities to the sets of annotation properties that can
     *                                  (optionally) accompany each entity's addition to the ontology signature
     * @param complexAnnotations    Set of complex annotations
     * @param immutableAnnotations  Set of annotation properties whose value cannot be modified
     * @param requiredEntities  Map of entities to entities that are required for some operation
     * @param customProperties  Map of custom properties for the project
     * @return Project options
     */
    ProjectOptions getProjectOptions(Map<String,Set<String>> requiredAnnotationsMap, Map<String,Set<String>> optionalAnnotationsMap, Set<String> complexAnnotations,
                                     Set<String> immutableAnnotations, Map<String,Set<String>> requiredEntities, Map<String, String> customProperties);

}
