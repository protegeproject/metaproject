package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.UnknownAccessControlObjectTypeException;
import org.semanticweb.owlapi.model.AxiomType;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class FactoryImpl implements Factory {

    /**
     * No-args constructor
     */
    public FactoryImpl() { }

    @Override
    public Project createProject(ProjectId projectId, Name name, Description description, Address address, UserId ownerId, Set<UserId> admins) {
        checkNotNull(projectId, "Project identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(description, "Description cannot be null");
        checkNotNull(address, "Address cannot be null");
        checkNotNull(ownerId, "Owner user identifier cannot be null");
        checkNotNull(admins, "Administrator user identifier set cannot be null");
        return new ProjectImpl(projectId, name, description, address, ownerId, admins);
    }

    @Override
    public Role createRole(RoleId roleId, Name name, Description description, Set<OperationId> operations) {
        checkNotNull(roleId, "Role identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(description, "Description cannot be null");
        checkNotNull(operations, "Operation identifier set cannot be null");
        return new RoleImpl(roleId, name, description, operations);
    }

    @Override
    public Operation createOperation(OperationId operationId, Name name, Description description, OperationType operationType, Optional<Set<OperationRestriction>> restrictions) {
        checkNotNull(operationId, "Operation identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(description, "Description cannot be null");
        checkNotNull(operationType, "Operation type cannot be null");
        return new OperationImpl(operationId, name, description, operationType, restrictions);
    }

    @Override
    public User createUser(UserId userId, Name name, EmailAddress emailAddress) {
        checkNotNull(userId, "User identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(emailAddress, "Name cannot be null");
        return new UserImpl(userId, name, emailAddress);
    }

    @Override
    public OperationRestriction createAxiomTypeOperationRestriction(AxiomType axiomType, ChangeModality modality) {
        checkNotNull(axiomType, "Restriction type cannot be null");
        checkNotNull(modality, "Restriction modality cannot be null");
        return new AxiomTypeRestriction(axiomType, modality);
    }

    @Override
    public AuthenticationDetails createAuthenticationDetails(UserId userId, SaltedPasswordDigest password) {
        checkNotNull(userId, "User identifier cannot be null");
        checkNotNull(password, "Password cannot be null");
        return new AuthenticationDetailsImpl(userId, password);
    }

    @Override
    public Salt createSalt(String salt) {
        checkNotNull(salt, "Salt must not be null");
        return new SaltImpl(salt);
    }

    @Override
    public SaltedPasswordDigest createSaltedPasswordDigest(String password, Salt salt) {
        return new SaltedPasswordDigestImpl(password, salt);
    }

    @Override
    public Name createName(String name) {
        checkNotNull(name, "Name cannot be null");
        return new NameImpl(name);
    }

    @Override
    public Description createDescription(String description) {
        checkNotNull(description, "Description cannot be null");
        return new DescriptionImpl(description);
    }

    @Override
    public Address createAddress(String address) {
        checkNotNull(address, "Address cannot be null");
        return new AddressImpl(address);
    }

    @Override
    public EmailAddress createEmailAddress(String emailAddress) {
        checkNotNull(emailAddress, "Email address cannot be null");
        return new EmailAddressImpl(emailAddress);
    }

    @Override
    public EntityIriPrefix createEntityIriPrefix(String entityIriPrefix) {
        checkNotNull(entityIriPrefix, "Entity IRI prefix cannot be null");
        return new EntityIriPrefixImpl(entityIriPrefix);
    }

    @Override
    public EntityNameSuffix createEntityNameSuffix(String entityNameSuffix) {
        checkNotNull(entityNameSuffix, "Entity name suffix cannot be null");
        return new EntityNameSuffixImpl(entityNameSuffix);
    }

    @Override
    public EntityNamePrefix createEntityNamePrefix(String entityNamePrefix) {
        checkNotNull(entityNamePrefix, "Entity name prefix cannot be null");
        return new EntityNamePrefixImpl(entityNamePrefix);
    }

    @Override
    public AccessControlObjectId createAccessControlObjectId(String id, AccessControlObjectIdType type) throws UnknownAccessControlObjectTypeException {
        checkNotNull(id, "Id cannot be null");
        checkNotNull(type, "Access control object type cannot be null");
        switch(type) {
            case ROLE: return createRoleId(id);
            case USER: return createUserId(id);
            case PROJECT: return createProjectId(id);
            case OPERATION: return createOperationId(id);
            default: throw new UnknownAccessControlObjectTypeException("The access control object type provided is not recognized (" + type + ")");
        }
    }

    @Override
    public RoleId createRoleId(String roleId) {
        checkNotNull(roleId, "Role identifier cannot be null");
        return new RoleIdImpl(roleId);
    }

    @Override
    public UserId createUserId(String userId) {
        checkNotNull(userId, "User identifier cannot be null");
        return new UserIdImpl(userId);
    }

    @Override
    public ProjectId createProjectId(String projectId) {
        checkNotNull(projectId, "Project identifier cannot be null");
        return new ProjectIdImpl(projectId);
    }

    @Override
    public OperationId createOperationId(String operationId) {
        checkNotNull(operationId, "Operation identifier cannot be null");
        return new OperationIdImpl(operationId);
    }

    @Override
    public UserId createUserUuid() {
        return new UserIdImpl(getNewUuid());
    }

    @Override
    public RoleId createRoleUuid() {
        return new RoleIdImpl(getNewUuid());
    }

    @Override
    public ProjectId createProjectUuid() {
        return new ProjectIdImpl(getNewUuid());
    }

    @Override
    public OperationId createOperationUuid() {
        return new OperationIdImpl(getNewUuid());
    }

    private String getNewUuid() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Port createPort(Integer portNr) {
        return new PortImpl(portNr);
    }

    @Override
    public RegistryPort createRegistryPort(Integer portNr) {
        return new RegistryPortImpl(portNr);
    }

    @Override
    public Host createHost(Address address, Port port, RegistryPort registryPort) {
        return new HostImpl(address, port, registryPort);
    }
}