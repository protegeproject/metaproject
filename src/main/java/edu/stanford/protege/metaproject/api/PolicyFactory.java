package edu.stanford.protege.metaproject.api;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface PolicyFactory {

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
     * Create a new custom operation
     *
     * @param operationId   Operation identifier
     * @param name  Operation name
     * @param description   Operation description
     * @param operationType Operation type
     * @param scope Operation scope
     * @return New operation instance
     */
    Operation getCustomOperation(OperationId operationId, Name name, Description description, OperationType operationType, Operation.Scope scope);

    /**
     * Create a new system operation
     *
     * @param operationId   Operation identifier
     * @param name  Operation name
     * @param description   Operation description
     * @param operationType Operation type
     * @param scope Operation scope
     * @return New operation instance
     */
    Operation getSystemOperation(OperationId operationId, Name name, Description description, OperationType operationType, Operation.Scope scope);

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
     * Create an instance of salted password given a string that represents the hash of a password, and the salt used to hash it
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
     * @throws URISyntaxException   URI syntax exception
     */
    URI getUri(String uri) throws URISyntaxException;

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
     * @param options  Options map
     * @return Project options
     */
    ProjectOptions getProjectOptions(Map<String,Set<String>> options);

}
