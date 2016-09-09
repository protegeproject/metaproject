package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;
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
     * @param filePath   Project file path
     * @param ownerId   Project owner user identifier
     * @param options   Project options
     * @return New Project instance
     */
    @Nonnull
    Project getProject(@Nonnull ProjectId projectId, @Nonnull Name name, @Nonnull Description description, @Nonnull UserId ownerId, @Nonnull Optional<String> filePath, @Nonnull Optional<ProjectOptions> options);

    /**
     * Create a new role
     *
     * @param roleId    Role identifier
     * @param name  Role name
     * @param description   Role description
     * @param operations    Set of operations
     * @return New role instance
     */
    @Nonnull
    Role getRole(@Nonnull RoleId roleId, @Nonnull Name name, @Nonnull Description description, @Nonnull Set<OperationId> operations);

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
    @Nonnull
    Operation getCustomOperation(@Nonnull OperationId operationId, @Nonnull Name name, @Nonnull Description description, @Nonnull OperationType operationType, @Nonnull Operation.Scope scope);

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
    @Nonnull
    Operation getSystemOperation(@Nonnull OperationId operationId, @Nonnull Name name, @Nonnull Description description, @Nonnull OperationType operationType, @Nonnull Operation.Scope scope);

    /**
     * Create a user with the given user identifier, name and email address
     *
     * @param userId    User identifier
     * @param userName  User name
     * @param emailAddress  User email address
     * @return A user instance
     */
    @Nonnull
    User getUser(@Nonnull UserId userId, @Nonnull Name userName, @Nonnull EmailAddress emailAddress);

    /**
     * Create an instance of authentication details
     *
     * @param userId    User identifier
     * @param password  Salted password digest
     * @return Authentication details
     */
    @Nonnull
    AuthenticationDetails getAuthenticationDetails(@Nonnull UserId userId, @Nonnull SaltedPasswordDigest password);

    /**
     * Create an instance of crypto salt
     *
     * @param salt  Salt string
     * @return Salt instance
     */
    @Nonnull
    Salt getSalt(@Nonnull String salt);

    /**
     * Create an instance of plain password
     *
     * @param password  Password string
     * @return Plain password
     */
    @Nonnull
    PlainPassword getPlainPassword(@Nonnull String password);

    /**
     * Create an instance of salted password given a string that represents the hash of a password, and the salt used to hash it
     *
     * @param password  Password string
     * @param salt  Salt
     * @return Salted password digest
     */
    @Nonnull
    SaltedPasswordDigest getSaltedPasswordDigest(@Nonnull String password, @Nonnull Salt salt);

    /**
     * Create a name for an access control object
     *
     * @param name  Name
     * @return Name instance
     */
    @Nonnull
    Name getName(@Nonnull String name);

    /**
     * Create a description for an access control object
     *
     * @param description   Description
     * @return Description instance
     */
    @Nonnull
    Description getDescription(@Nonnull String description);

    /**
     * Create an email address name for an access control object
     *
     * @param emailAddress  Email address
     * @return Email address instance
     */
    @Nonnull
    EmailAddress getEmailAddress(@Nonnull String emailAddress);

    /**
     * Create a new identifier for a role
     *
     * @param roleId    Role identifier
     * @return Role identifier instance
     */
    @Nonnull
    RoleId getRoleId(@Nonnull String roleId);

    /**
     * Create a new identifier for a user
     *
     * @param userId    User identifier
     * @return User identifier instance
     */
    @Nonnull
    UserId getUserId(@Nonnull String userId);

    /**
     * Create a new identifier for a project
     *
     * @param projectId Project identifier
     * @return Project identifier instance
     */
    @Nonnull
    ProjectId getProjectId(@Nonnull String projectId);

    /**
     * Create a new identifier for an operation
     *
     * @param operationId   Operation identifier
     * @return Operation identifier instance
     */
    @Nonnull
    OperationId getOperationId(@Nonnull String operationId);

    /**
     * Get a user ID that is a randomly generated UUID
     *
     * @return New user UUID
     */
    @Nonnull
    UserId getUserUuid();

    /**
     * Get a new role ID that is a randomly generated UUID
     *
     * @return New role UUID
     */
    @Nonnull
    RoleId getRoleUuid();

    /**
     * Get a new project ID that is a randomly generated UUID
     *
     * @return New project ID that is a randomly generated UUID
     */
    @Nonnull
    ProjectId getProjectUuid();

    /**
     * Get a new operation ID that is a randomly generated UUID
     *
     * @return New operation UUID
     */
    @Nonnull
    OperationId getOperationUuid();

    /**
     * Create an instance of Port
     *
     * @param portNr    Port number
     * @return Port instance
     */
    @Nonnull
    Port getPort(@Nonnull Integer portNr);

    /**
     * Create an instance of a Host
     *
     * @param address   Host address
     * @param secondaryPort  Optional secondary port
     * @return Host instance
     */
    @Nonnull
    Host getHost(@Nonnull URI address, @Nonnull Optional<Port> secondaryPort);

    /**
     * Get a URI based on the given string
     *
     * @param uri   URI string
     * @return URI
     * @throws URISyntaxException   URI syntax exception
     */
    @Nonnull
    URI getUri(@Nonnull String uri) throws URISyntaxException;

    /**
     * Create an instance of a salt generator
     *
     * @return Salt generator
     */
    @Nonnull
    SaltGenerator getSaltGenerator();

    /**
     * Create an instance of a password hasher
     *
     * @return Password hasher
     */
    @Nonnull
    PasswordHasher getPasswordHasher();

    /**
     * Create an instance of a password hasher with the given hash byte size
     * and number of key-stretching iterations.
     *
     * @param hashByteSize  Hash byte size
     * @param nrIterations  Number of key-stretching iterations
     * @return Password hasher
     */
    @Nonnull
    PasswordHasher getPasswordHasher(int hashByteSize, int nrIterations);

    /**
     * Get an authentication token that reflects a successful authentication attempt
     *
     * @param user    User
     * @return Authentication token
     */
    @Nonnull
    AuthToken getAuthorizedUserToken(@Nonnull User user);

    /**
     * Get an authentication token that reflects an unsuccessful authentication attempt
     *
     * @param user    User
     * @return Authentication token
     */
    @Nonnull
    AuthToken getUnauthorizedUserToken(@Nonnull User user);

    /**
     * Create an instance of project options
     *
     * @param options  Options map
     * @return Project options
     */
    @Nonnull
    ProjectOptions getProjectOptions(@Nonnull Map<String,Set<String>> options);

}
