package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.api.*;
import org.semanticweb.owlapi.model.IRI;

import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AccessControlObjectFactoryImpl implements AccessControlObjectFactory {

    public AccessControlObjectFactoryImpl() { }

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
    public Operation createOperation(OperationId operationId, Name name, Description description, OperationType operationType, Optional<Set<OperationPrerequisite>> prerequisites) {
        checkNotNull(operationId, "Operation identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(description, "Description cannot be null");
        checkNotNull(operationType, "Operation type cannot be null");
        return new OperationImpl(operationId, name, description, operationType, prerequisites);
    }

    @Override
    public User createUser(UserId userId, Name name, EmailAddress emailAddress) {
        checkNotNull(userId, "User identifier cannot be null");
        checkNotNull(name, "Name cannot be null");
        checkNotNull(emailAddress, "Name cannot be null");
        return new UserImpl(userId, name, emailAddress);
    }

    @Override
    public OperationPrerequisite createOperationPrerequisite(IRI prerequisite, OperationPrerequisite.Modifier modifier) {
        checkNotNull(prerequisite, "Prerequisite IRI cannot be null");
        checkNotNull(modifier, "Operation prerequisite modifier cannot be null");
        return new OperationPrerequisiteImpl(prerequisite, modifier);
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
}