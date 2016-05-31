package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class MetaprojectFactoryImpl implements MetaprojectFactory {

    /**
     * No-args constructor
     */
    public MetaprojectFactoryImpl() { }

    @Override
    public Project getProject(ProjectId projectId, Name name, Description description, File file, UserId ownerId, Optional<ProjectOptions> options) {
        checkNotNull(projectId, "Project identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(description, "Description cannot be null");
        checkNotNull(file, "File cannot be null");
        checkNotNull(ownerId, "Owner user identifier cannot be null");
        checkNotNull(options, "Project options cannot be null");
        return new ProjectImpl(projectId, name, description, file, ownerId, options);
    }

    @Override
    public Role getRole(RoleId roleId, Name name, Description description, Set<OperationId> operations) {
        checkNotNull(roleId, "Role identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(description, "Description cannot be null");
        checkNotNull(operations, "Operation identifier set cannot be null");
        return new RoleImpl(roleId, name, description, operations);
    }

    @Override
    public Operation getCustomOperation(OperationId operationId, Name name, Description description, OperationType operationType, Operation.Scope scope) {
        checkOperationArguments(operationId, name, description, operationType, scope);
        return new CustomOperation(operationId, name, description, operationType, scope);
    }

    @Override
    public Operation getSystemOperation(OperationId operationId, Name name, Description description, OperationType operationType, Operation.Scope scope) {
        checkOperationArguments(operationId, name, description, operationType, scope);
        return new SystemOperation(operationId, name, description, operationType, scope);
    }

    private void checkOperationArguments(OperationId operationId, Name name, Description description, OperationType operationType, Operation.Scope scope) {
        checkNotNull(operationId, "Operation identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(description, "Description cannot be null");
        checkNotNull(operationType, "Operation type cannot be null");
    }

    @Override
    public User getUser(UserId userId, Name name, EmailAddress emailAddress) {
        checkNotNull(userId, "User identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(emailAddress, "Email address cannot be null");
        return new UserImpl(userId, name, emailAddress);
    }

    @Override
    public AuthenticationDetails getAuthenticationDetails(UserId userId, SaltedPasswordDigest password) {
        checkNotNull(userId, "User identifier cannot be null");
        checkNotNull(password, "Password cannot be null");
        return new AuthenticationDetailsImpl(userId, password);
    }

    @Override
    public Salt getSalt(String salt) {
        checkNotNull(salt, "Salt must not be null");
        return new SaltImpl(salt);
    }

    @Override
    public PlainPassword getPlainPassword(String password) {
        checkNotNull(password, "Password must not be null");
        return new PlainPasswordImpl(password);
    }

    @Override
    public SaltedPasswordDigest getSaltedPasswordDigest(String password, Salt salt) {
        checkNotNull(password, "Password must not be null");
        checkNotNull(salt, "Salt must not be null");
        return new SaltedPasswordDigestImpl(password, salt);
    }

    @Override
    public Name getName(String name) {
        checkNotNull(name, "Name cannot be null");
        return new NameImpl(name);
    }

    @Override
    public Description getDescription(String description) {
        checkNotNull(description, "Description cannot be null");
        return new DescriptionImpl(description);
    }

    @Override
    public EmailAddress getEmailAddress(String emailAddress) {
        checkNotNull(emailAddress, "Email address cannot be null");
        return new EmailAddressImpl(emailAddress);
    }

    @Override
    public RoleId getRoleId(String roleId) {
        checkNotNull(roleId, "Role identifier cannot be null");
        return new RoleIdImpl(roleId);
    }

    @Override
    public UserId getUserId(String userId) {
        checkNotNull(userId, "User identifier cannot be null");
        return new UserIdImpl(userId);
    }

    @Override
    public ProjectId getProjectId(String projectId) {
        checkNotNull(projectId, "Project identifier cannot be null");
        return new ProjectIdImpl(projectId);
    }

    @Override
    public OperationId getOperationId(String operationId) {
        checkNotNull(operationId, "Operation identifier cannot be null");
        return new OperationIdImpl(operationId);
    }

    @Override
    public UserId getUserUuid() {
        return new UserIdImpl(getNewUuid());
    }

    @Override
    public RoleId getRoleUuid() {
        return new RoleIdImpl(getNewUuid());
    }

    @Override
    public ProjectId getProjectUuid() {
        return new ProjectIdImpl(getNewUuid());
    }

    @Override
    public OperationId getOperationUuid() {
        return new OperationIdImpl(getNewUuid());
    }

    private String getNewUuid() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Port getPort(Integer portNr) {
        checkNotNull(portNr, "Port number must not be null");
        return new PortImpl(portNr);
    }

    @Override
    public Host getHost(URI address, Optional<Port> secondaryPort) {
        checkNotNull(address, "Host URI must not be null");
        if(secondaryPort.isPresent()) {
            checkNotNull(secondaryPort.get(), "Secondary port must not be null");
        }
        return new HostImpl(address, secondaryPort);
    }

    @Override
    public URI getUri(String uri) throws URISyntaxException {
        URI newUri = new URI(uri);
        return checkNotNull(newUri);
    }

    @Override
    public SaltGenerator getSaltGenerator() {
        return new SaltGeneratorImpl();
    }

    @Override
    public PasswordHasher getPasswordHasher() {
        return getPasswordHasher(MetaprojectUtils.getHashByteSize(), MetaprojectUtils.getKeyStretchingIterations());
    }

    @Override
    public PasswordHasher getPasswordHasher(int hashByteSize, int nrIterations) {
        return new Pbkdf2PasswordHasher(hashByteSize, nrIterations);
    }

    @Override
    public UserRegistry getUserRegistry() {
        Set<User> users = new HashSet<>();
        users.add(MetaprojectUtils.getRootUser());
        users.add(MetaprojectUtils.getGuestUser());
        return getUserRegistry(users);
    }

    @Override
    public UserRegistry getUserRegistry(Set<User> users) {
        checkNotNull(users, "Set of users must not be null");
        return new UserRegistryImpl(users);
    }

    @Override
    public ProjectRegistry getProjectRegistry() {
        return getProjectRegistry(new HashSet<>());
    }

    @Override
    public ProjectRegistry getProjectRegistry(Set<Project> projects) {
        checkNotNull(projects, "Set of projects must not be null");
        return new ProjectRegistryImpl(projects);
    }

    @Override
    public OperationRegistry getOperationRegistry() {
        return getOperationRegistry(Operations.getDefaultOperations());
    }

    @Override
    public OperationRegistry getOperationRegistry(Set<Operation> operations) {
        checkNotNull(operations, "Set of operations must not be null");
        return new OperationRegistryImpl(operations);
    }

    @Override
    public RoleRegistry getRoleRegistry() {
        Set<Role> roles = new HashSet<>();
        roles.add(MetaprojectUtils.getAdminRole());
        roles.add(MetaprojectUtils.getGuestRole());
        roles.add(MetaprojectUtils.getProjectManagerRole());
        return getRoleRegistry(roles);
    }

    @Override
    public RoleRegistry getRoleRegistry(Set<Role> roles) {
        checkNotNull(roles, "Set of roles must not be null");
        return new RoleRegistryImpl(roles);
    }

    @Override
    public AuthenticationRegistry getAuthenticationRegistry() {
        Set<AuthenticationDetails> authDetails = new HashSet<>();
        authDetails.add(MetaprojectUtils.getRootUserCredentials());
        authDetails.add(MetaprojectUtils.getGuestUserCredentials());
        return getAuthenticationRegistry(authDetails);
    }

    @Override
    public AuthenticationRegistry getAuthenticationRegistry(Set<AuthenticationDetails> authenticationDetails) {
        checkNotNull(authenticationDetails, "Set of authentication details must not be null");
        return new AuthenticationRegistryImpl(authenticationDetails);
    }

    @Override
    public Policy getPolicy() {
        return getPolicy(new HashMap<>());
    }

    @Override
    public Policy getPolicy(Map<UserId, Map<ProjectId, Set<RoleId>>> userRoleMap) {
        checkNotNull(userRoleMap, "User-role map must not be null");
        return new PolicyImpl(userRoleMap);
    }

    @Override
    public MetaprojectAgent getMetaprojectAgent(Policy policy, RoleRegistry roleRegistry, OperationRegistry operationRegistry,
                                                UserRegistry userRegistry, ProjectRegistry projectRegistry) {
        checkNotNull(policy, "Policy must not be null");
        checkNotNull(roleRegistry, "Role registry must not be null");
        checkNotNull(operationRegistry, "Operation registry must not be null");
        checkNotNull(userRegistry, "User registry must not be null");
        checkNotNull(projectRegistry, "Project registry must not be null");
        return new MetaprojectAgentImpl(policy, roleRegistry, operationRegistry, userRegistry, projectRegistry);
    }

    @Override
    public AuthToken getAuthorizedUserToken(User user) {
        checkNotNull(user, "User must not be null");
        return new AuthorizedUserToken(user);
    }

    @Override
    public AuthToken getUnauthorizedUserToken(User user) {
        checkNotNull(user, "User must not be null");
        return new UnauthorizedUserToken(user);
    }

    @Override
    public ProjectOptions getProjectOptions(Map<String,Set<String>> options) {
        checkNotNull(options, "Options map must not be null");
        return new ProjectOptionsImpl(options);
    }
}