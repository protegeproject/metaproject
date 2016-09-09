package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.*;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class PolicyFactoryImpl implements PolicyFactory {

    /**
     * No-args constructor
     */
    public PolicyFactoryImpl() { }

    @Nonnull
    @Override
    public Project getProject(@Nonnull ProjectId projectId, @Nonnull Name name, @Nonnull Description description, @Nonnull UserId ownerId, @Nonnull Optional<String> filePath, @Nonnull Optional<ProjectOptions> options) {
        checkNotNull(projectId, "Project identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(description, "Description cannot be null");
        checkNotNull(filePath, "Project file path cannot be null");
        checkNotNull(ownerId, "Owner user identifier cannot be null");
        checkNotNull(options, "Project options cannot be null");
        return new ProjectImpl(projectId, name, description, ownerId, filePath, options);
    }

    @Nonnull
    @Override
    public Role getRole(@Nonnull RoleId roleId, @Nonnull Name name, @Nonnull Description description, @Nonnull Set<OperationId> operations) {
        checkNotNull(roleId, "Role identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(description, "Description cannot be null");
        checkNotNull(operations, "Operation identifier set cannot be null");
        return new RoleImpl(roleId, name, description, operations);
    }

    @Nonnull
    @Override
    public Operation getCustomOperation(@Nonnull OperationId operationId, @Nonnull Name name, @Nonnull Description description, @Nonnull OperationType operationType, @Nonnull Operation.Scope scope) {
        checkOperationArguments(operationId, name, description, operationType, scope);
        return new CustomOperation(operationId, name, description, operationType, scope);
    }

    @Nonnull
    @Override
    public Operation getSystemOperation(@Nonnull OperationId operationId, @Nonnull Name name, @Nonnull Description description, @Nonnull OperationType operationType, @Nonnull Operation.Scope scope) {
        checkOperationArguments(operationId, name, description, operationType, scope);
        return new SystemOperation(operationId, name, description, operationType, scope);
    }

    private void checkOperationArguments(OperationId operationId, Name name, Description description, OperationType operationType, Operation.Scope scope) {
        checkNotNull(operationId, "Operation identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(description, "Description cannot be null");
        checkNotNull(operationType, "Operation type cannot be null");
        checkNotNull(scope, "Operation scope cannot be null");
    }

    @Nonnull
    @Override
    public User getUser(@Nonnull UserId userId, @Nonnull Name name, @Nonnull EmailAddress emailAddress) {
        checkNotNull(userId, "User identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(emailAddress, "Email address cannot be null");
        return new UserImpl(userId, name, emailAddress);
    }

    @Nonnull
    @Override
    public AuthenticationDetails getAuthenticationDetails(@Nonnull UserId userId, @Nonnull SaltedPasswordDigest password) {
        checkNotNull(userId, "User identifier cannot be null");
        checkNotNull(password, "Password cannot be null");
        return new AuthenticationDetailsImpl(userId, password);
    }

    @Nonnull
    @Override
    public Salt getSalt(@Nonnull String salt) {
        checkNotNull(salt, "Salt must not be null");
        return new SaltImpl(salt);
    }

    @Nonnull
    @Override
    public PlainPassword getPlainPassword(@Nonnull String password) {
        checkNotNull(password, "Password must not be null");
        return new PlainPasswordImpl(password);
    }

    @Nonnull
    @Override
    public SaltedPasswordDigest getSaltedPasswordDigest(@Nonnull String password, @Nonnull Salt salt) {
        checkNotNull(password, "Password must not be null");
        checkNotNull(salt, "Salt must not be null");
        return new SaltedPasswordDigestImpl(password, salt);
    }

    @Nonnull
    @Override
    public Name getName(@Nonnull String name) {
        checkNotNull(name, "Name cannot be null");
        return new NameImpl(name);
    }

    @Nonnull
    @Override
    public Description getDescription(@Nonnull String description) {
        checkNotNull(description, "Description cannot be null");
        return new DescriptionImpl(description);
    }

    @Nonnull
    @Override
    public EmailAddress getEmailAddress(@Nonnull String emailAddress) {
        checkNotNull(emailAddress, "Email address cannot be null");
        return new EmailAddressImpl(emailAddress);
    }

    @Nonnull
    @Override
    public RoleId getRoleId(@Nonnull String roleId) {
        checkNotNull(roleId, "Role identifier cannot be null");
        return new RoleIdImpl(roleId);
    }

    @Nonnull
    @Override
    public UserId getUserId(@Nonnull String userId) {
        checkNotNull(userId, "User identifier cannot be null");
        return new UserIdImpl(userId);
    }

    @Nonnull
    @Override
    public ProjectId getProjectId(@Nonnull String projectId) {
        checkNotNull(projectId, "Project identifier cannot be null");
        return new ProjectIdImpl(projectId);
    }

    @Nonnull
    @Override
    public OperationId getOperationId(@Nonnull String operationId) {
        checkNotNull(operationId, "Operation identifier cannot be null");
        return new OperationIdImpl(operationId);
    }

    @Nonnull
    @Override
    public UserId getUserUuid() {
        return new UserIdImpl(getNewUuid());
    }

    @Nonnull
    @Override
    public RoleId getRoleUuid() {
        return new RoleIdImpl(getNewUuid());
    }

    @Nonnull
    @Override
    public ProjectId getProjectUuid() {
        return new ProjectIdImpl(getNewUuid());
    }

    @Nonnull
    @Override
    public OperationId getOperationUuid() {
        return new OperationIdImpl(getNewUuid());
    }

    private String getNewUuid() {
        return UUID.randomUUID().toString();
    }

    @Nonnull
    @Override
    public Port getPort(@Nonnull Integer portNr) {
        checkNotNull(portNr, "Port number must not be null");
        return new PortImpl(portNr);
    }

    @Nonnull
    @Override
    public Host getHost(@Nonnull URI address, @Nonnull Optional<Port> secondaryPort) {
        checkNotNull(address, "Host URI must not be null");
        checkNotNull(secondaryPort, "Secondary port must not be null");
        return new HostImpl(address, secondaryPort);
    }

    @Nonnull
    @Override
    public URI getUri(@Nonnull String uri) throws URISyntaxException {
        URI newUri = new URI(uri);
        return checkNotNull(newUri);
    }

    @Nonnull
    @Override
    public SaltGenerator getSaltGenerator() {
        return new SaltGeneratorImpl();
    }

    @Nonnull
    @Override
    public PasswordHasher getPasswordHasher() {
        return getPasswordHasher(ConfigurationUtils.getHashByteSize(), ConfigurationUtils.getKeyStretchingIterations());
    }

    @Nonnull
    @Override
    public PasswordHasher getPasswordHasher(int hashByteSize, int nrIterations) {
        return new Pbkdf2PasswordHasher(hashByteSize, nrIterations);
    }

    @Nonnull
    @Override
    public AuthToken getAuthorizedUserToken(@Nonnull User user) {
        checkNotNull(user, "User must not be null");
        return new AuthorizedUserToken(user);
    }

    @Nonnull
    @Override
    public AuthToken getUnauthorizedUserToken(@Nonnull User user) {
        checkNotNull(user, "User must not be null");
        return new UnauthorizedUserToken(user);
    }

    @Nonnull
    @Override
    public ProjectOptions getProjectOptions(@Nonnull Map<String,Set<String>> options) {
        checkNotNull(options, "Options map must not be null");
        return new ProjectOptionsImpl(options);
    }
}