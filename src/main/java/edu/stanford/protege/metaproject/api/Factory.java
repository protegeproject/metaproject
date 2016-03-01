package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UnknownAccessControlObjectTypeException;
import org.semanticweb.owlapi.model.AxiomType;

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
     * @param address   Project location
     * @param ownerId   Project owner user identifier
     * @param admins    Set of user identifiers of administrators
     * @return New Project instance
     */
    Project createProject(ProjectId projectId, Name name, Description description, Address address, UserId ownerId, Set<UserId> admins);

    /**
     * Create a new role
     *
     * @param roleId    Role identifier
     * @param name  Role name
     * @param description   Role description
     * @param operations    Set of operations
     * @return New role instance
     */
    Role createRole(RoleId roleId, Name name, Description description, Set<OperationId> operations);

    /**
     * Create a new operation
     *
     * @param operationId   Operation identifier
     * @param name  Operation name
     * @param description   Operation description
     * @param operationType Operation type
     * @param restrictions Set of operation restrictions
     * @return New operation instance
     */
    Operation createOperation(OperationId operationId, Name name, Description description, OperationType operationType, Optional<Set<OperationRestriction>> restrictions);

    /**
     * Create a user with the given user identifier, name and email address
     *
     * @param userId    User identifier
     * @param userName  User name
     * @param emailAddress  User email address
     * @return A user instance
     */
    User createUser(UserId userId, Name userName, EmailAddress emailAddress);

    /**
     * Create an axiom type operation restriction given the axiom type and modality, that is, whether the
     * axiom type can be added or removed
     *
     * @param axiomType  Axiom type
     * @param modality  Restriction modality
     * @return Operation restriction instance
     */
    OperationRestriction createAxiomTypeOperationRestriction(AxiomType axiomType, ChangeModality modality);

    /**
     * Create an instance of authentication details
     *
     * @param userId    User identifier
     * @param password  Salted password digest
     * @return Authentication details
     */
    AuthenticationDetails createAuthenticationDetails(UserId userId, SaltedPasswordDigest password);

    /**
     * Create an instance of crypto salt
     *
     * @param salt  Salt string
     * @return Salt instance
     */
    Salt createSalt(String salt);

    /**
     * Create an instance of salted password
     *
     * @param password  Password string
     * @param salt  Salt
     * @return Salted password digest
     */
    SaltedPasswordDigest createSaltedPasswordDigest(String password, Salt salt);

    /**
     * Create a name for an access control object
     *
     * @param name  Name
     * @return Name instance
     */
    Name createName(String name);

    /**
     * Create a description for an access control object
     *
     * @param description   Description
     * @return Description instance
     */
    Description createDescription(String description);

    /**
     * Create an address for an access control object
     *
     * @param address   Address
     * @return Address instance
     */
    Address createAddress(String address);

    /**
     * Create an email address name for an access control object
     *
     * @param emailAddress  Email address
     * @return Email address instance
     */
    EmailAddress createEmailAddress(String emailAddress);

    /**
     * Create an entity IRI prefix
     *
     * @param entityIriPrefix   Entity IRI prefix
     * @return Entity IRI prefix
     */
    EntityIriPrefix createEntityIriPrefix(String entityIriPrefix);

    /**
     * Create a suffix for entity names
     *
     * @param suffix    Entity name suffix
     * @return Entity name suffix instance
     */
    EntityNameSuffix createEntityNameSuffix(String suffix);

    /**
     * Create a prefix for entity names
     *
     * @param prefix    Entity name prefix
     * @return Entity name prefix instance
     */
    EntityNamePrefix createEntityNamePrefix(String prefix);

    /**
     * Create an identifier for an access control object
     *
     * @param id    Access control object identifier
     * @param type  Access control object type
     * @return Access control object identifier instance
     * @throws UnknownAccessControlObjectTypeException  Unknown access control object type
     */
    AccessControlObjectId createAccessControlObjectId(String id, AccessControlObjectIdType type) throws UnknownAccessControlObjectTypeException;

    /**
     * Create a new identifier for a role
     *
     * @param roleId    Role identifier
     * @return Role identifier instance
     */
    RoleId createRoleId(String roleId);

    /**
     * Create a new identifier for a user
     *
     * @param userId    User identifier
     * @return User identifier instance
     */
    UserId createUserId(String userId);

    /**
     * Create a new identifier for a project
     *
     * @param projectId Project identifier
     * @return Project identifier instance
     */
    ProjectId createProjectId(String projectId);

    /**
     * Create a new identifier for an operation
     *
     * @param operationId   Operation identifier
     * @return Operation identifier instance
     */
    OperationId createOperationId(String operationId);

    /**
     * Get a user ID that is a randomly generated UUID
     *
     * @return New user UUID
     */
    UserId createUserUuid();

    /**
     * Get a new role ID that is a randomly generated UUID
     *
     * @return New role UUID
     */
    RoleId createRoleUuid();

    /**
     * Get a new project ID that is a randomly generated UUID
     *
     * @return New project ID that is a randomly generated UUID
     */
    ProjectId createProjectUuid();

    /**
     * Get a new operation ID that is a randomly generated UUID
     *
     * @return New operation UUID
     */
    OperationId createOperationUuid();

    /**
     * Create an instance of Port
     *
     * @param portNr    Port number
     * @return Port instance
     */
    Port createPort(Integer portNr);

    /**
     * Create an instance of an RMI registry port
     *
     * @param portNr    Port number
     * @return Registry Port instance
     */
    RegistryPort createRegistryPort(Integer portNr);

    /**
     * Create an instance of a Host
     *
     * @param address   Host address
     * @param port  Port
     * @param registryPort  Registry port
     * @return Host instance
     */
    Host createHost(Address address, Port port, RegistryPort registryPort);

}
