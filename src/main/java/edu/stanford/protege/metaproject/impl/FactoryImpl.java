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
public final class FactoryImpl implements Factory {

    /**
     * No-args constructor
     */
    public FactoryImpl() { }

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
    public Operation getServerOperation(OperationId operationId, Name name, Description description, OperationType operationType) {
        checkOperationArguments(operationId, name, description, operationType);
        return new ServerOperation(operationId, name, description, operationType);
    }

    @Override
    public Operation getOntologyOperation(OperationId operationId, Name name, Description description, OperationType operationType) {
        checkOperationArguments(operationId, name, description, operationType);
        return new OntologyOperation(operationId, name, description, operationType);
    }

    @Override
    public Operation getMetaprojectOperation(OperationId operationId, Name name, Description description, OperationType operationType) {
        checkOperationArguments(operationId, name, description, operationType);
        return new MetaprojectOperation(operationId, name, description, operationType);
    }

    private void checkOperationArguments(OperationId operationId, Name name, Description description, OperationType operationType) {
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
    public URI getUri(String uri) {
        URI newUri = null;
        try {
            newUri = new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return checkNotNull(newUri);
    }

    @Override
    public GuiRestriction getGuiRestriction(String component, GuiRestriction.Visibility visibility) {
        return new GuiRestrictionImpl(component, visibility);
    }

    @Override
    public SaltGenerator getSaltGenerator() {
        return new SaltGeneratorImpl();
    }

    @Override
    public PasswordHasher getPasswordHasher() {
        int hashByteSize = 24, nrIterations = 10000;
        return getPasswordHasher(hashByteSize, nrIterations);
    }

    @Override
    public PasswordHasher getPasswordHasher(int hashByteSize, int nrIterations) {
        return new Pbkdf2PasswordHasher(hashByteSize, nrIterations);
    }

    @Override
    public UserRegistry getUserRegistry() {
        return getUserRegistry(new HashSet<>());
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
        return getRoleRegistry(new HashSet<>());
    }

    @Override
    public RoleRegistry getRoleRegistry(Set<Role> roles) {
        checkNotNull(roles, "Set of roles must not be null");
        return new RoleRegistryImpl(roles);
    }

    @Override
    public AuthenticationRegistry getAuthenticationRegistry() {
        // TODO should add root user
        return getAuthenticationRegistry(new HashSet<>());
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
    public PolicyAgent getPolicyAgent(Policy policy, RoleRegistry roleRegistry) {
        checkNotNull(policy, "Policy must not be null");
        checkNotNull(roleRegistry, "Role registry must not be null");
        return new PolicyAgentImpl(policy, roleRegistry);
    }

    @Override
    public AuthToken getAuthorizedUserToken(UserId userId) {
        checkNotNull(userId, "User identifier must not be null");
        return new AuthorizedUserToken(userId);
    }

    @Override
    public AuthToken getUnauthorizedUserToken(UserId userId) {
        checkNotNull(userId, "User identifier must not be null");
        return new UnauthorizedUserToken(userId);
    }

    @Override
    public ProjectOptions getProjectOptions(Map<String, Set<String>> requiredAnnotationsMap, Map<String, Set<String>> optionalAnnotationsMap, Set<String> complexAnnotations,
                                            Set<String> immutableAnnotations, Set<String> requiredEntities, Map<String, String> customProperties) {
        checkNotNull(requiredAnnotationsMap, "Map of required annotations must not be null");
        checkNotNull(optionalAnnotationsMap, "Map of optional annotations must not be null");
        checkNotNull(complexAnnotations, "Set of complex annotation properties must not be null");
        checkNotNull(immutableAnnotations, "Set of immutable annotation properties must not be null");
        checkNotNull(requiredEntities, "Set of required entities must not be null");
        checkNotNull(customProperties, "Map of custom properties must not be null");
        return new ProjectOptionsImpl(requiredAnnotationsMap, optionalAnnotationsMap, complexAnnotations,
                immutableAnnotations, requiredEntities, customProperties);
    }
}