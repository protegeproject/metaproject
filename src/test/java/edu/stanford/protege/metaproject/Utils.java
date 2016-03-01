package edu.stanford.protege.metaproject;

import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.impl.*;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;

import java.util.*;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class Utils {
    private static final int DEFAULT_SET_SIZE = 3;
    private static final OperationType DEFAULT_OPERATION_TYPE = OperationType.METAPROJECT;
    private static final ChangeModality DEFAULT_MODIFIER = AxiomChangeModality.ADDITION;
    private static final String IRI_PREFIX = "http://protege.stanford.edu/test/";
    private static final Random random = new Random();
    
    /*   access control object identifiers   */

    public static UserId getUserId() {
        return getUserId("userId-" + newUUID());
    }

    public static UserId getUserId(String userId) {
        return new UserIdImpl(userId);
    }

    public static RoleId getRoleId() {
        return getRoleId("roleId-" + newUUID());
    }

    public static RoleId getRoleId(String roleId) {
        return new RoleIdImpl(roleId);
    }

    public static ProjectId getProjectId() {
        return getProjectId("projectId-" + newUUID());
    }

    public static ProjectId getProjectId(String projectId) {
        return new ProjectIdImpl(projectId);
    }

    public static OperationId getOperationId() {
        return getOperationId("operationId-" + newUUID());
    }

    public static OperationId getOperationId(String operationId) {
        return new OperationIdImpl(operationId);
    }


    /*   entity IRIs   */

    public static EntityIriPrefix getEntityIriPrefix() {
        return getEntityIriPrefix(IRI_PREFIX);
    }

    public static EntityIriPrefix getEntityIriPrefix(String prefix) {
        return new EntityIriPrefixImpl(prefix);
    }

    public static EntityNamePrefix getEntityNamePrefix() {
        return getEntityNamePrefix("prefix" + newUUID());
    }

    public static EntityNamePrefix getEntityNamePrefix(String prefix) {
        return new EntityNamePrefixImpl(prefix);
    }

    public static EntityNameSuffix getEntityNameSuffix() {
        return getEntityNameSuffix("" + random.nextInt(Integer.SIZE - 1));
    }

    public static EntityNameSuffix getEntityNameSuffix(String suffix) {
        return new EntityNameSuffixImpl(suffix);
    }

    public static EntityName getEntityName() {
        return getEntityName(Utils.getEntityNamePrefix(), Utils.getEntityNameSuffix());
    }

    public static EntityName getEntityName(EntityNamePrefix prefix, EntityNameSuffix suffix) {
        return new EntityNameImpl(prefix, suffix);
    }

    public static EntityIri getEntityIri() {
        return getEntityIri(Utils.getEntityIriPrefix(), Utils.getEntityName());
    }

    public static EntityIri getEntityIri(EntityIriPrefix prefix, EntityName suffix) {
        return new EntityIriImpl(prefix, suffix);
    }


    /*   basic elements   */

    public static Name getName() {
        return getName("name " + newUUID());
    }

    public static Name getName(String name) {
        return new NameImpl(name);
    }

    public static Description getDescription() {
        return getDescription("description " + newUUID());
    }

    public static Description getDescription(String description) {
        return new DescriptionImpl(description);
    }

    public static Address getAddress() {
        return getAddress("http://protege.stanford.edu/" + newUUID());
    }

    public static Address getAddress(String address) {
        return new AddressImpl(address);
    }

    public static EmailAddress getEmailAddress() {
        return getEmailAddress(newUUID() + "@email.com");
    }

    public static EmailAddress getEmailAddress(String address) {
        return new EmailAddressImpl(address);
    }

    public static OperationRestriction getOperationRestriction() {
        return getOperationRestriction(DEFAULT_MODIFIER);
    }

    public static OperationRestriction getOperationRestriction(ChangeModality modality) {
        return getOperationRestriction(AxiomType.ANNOTATION_ASSERTION, modality);
    }

    public static OperationRestriction getOperationRestriction(AxiomType type, ChangeModality modality) {
        return new AxiomTypeRestriction(type, modality);
    }

    public static IRI getIRI() {
        return getIRI(IRI_PREFIX + newUUID());
    }

    public static IRI getIRI(String iri) {
        return IRI.create(iri);
    }

    public static String newUUID() {
        return UUID.randomUUID().toString();
    }


    /*   authentication   */

    public static SaltedPasswordDigest getSaltedPassword() {
        return getSaltedPassword("testPassword" + random.nextInt(), getSalt());
    }

    public static SaltedPasswordDigest getSaltedPassword(String password, Salt salt) {
        return Utils.getPasswordMaster().createHash(new PlainPasswordImpl(password), salt);
    }

    public static PlainPassword getPlainPassword() {
        return getPlainPassword("testPassword" + newUUID());
    }

    public static PlainPassword getPlainPassword(String password) {
        return new PlainPasswordImpl(password);
    }

    public static Salt getSalt() {
        return getSaltGenerator().generate();
    }

    public static Salt getSalt(String s) {
        return new SaltImpl(s);
    }

    public static AuthenticationDetails getAuthenticationDetails() {
        return getAuthenticationDetails(getUserId(), getSaltedPassword());
    }

    public static AuthenticationDetails getAuthenticationDetails(UserId userId, SaltedPasswordDigest password) {
        return new AuthenticationDetailsImpl(userId, password);
    }

    public static SaltGenerator getSaltGenerator() {
        return new SaltGeneratorImpl();
    }

    public static SaltGenerator getSaltGenerator(int byteSize) {
        return new SaltGeneratorImpl(byteSize);
    }

    public static PasswordHasher getPasswordMaster() {
        return new Pbkdf2PasswordHasher.Builder().createPasswordHasher();
    }

    public static PasswordHasher getPasswordMaster(int hashByteSize, int nrPBKDF2Iterations, SaltGenerator saltGenerator) {
        return new Pbkdf2PasswordHasher.Builder()
                .setHashByteSize(hashByteSize)
                .setNumberOfIterations(nrPBKDF2Iterations)
                .setSaltGenerator(saltGenerator)
                .createPasswordHasher();
    }


    /*   access control policy managers   */

    public static Policy getPolicyManager() {
        return new PolicyImpl();
    }

    public static Policy getPolicyManager(Map<UserId, Map<ProjectId,Set<RoleId>>> map) {
        return new PolicyImpl(map);
    }

    public static UserRegistry getUserManager() {
        return new UserRegistryImpl();
    }

    public static UserRegistry getUserManager(Set<User> users) {
        return new UserRegistryImpl(users);
    }

    public static RoleRegistry getRoleManager() {
        return new RoleRegistryImpl();
    }

    public static RoleRegistry getRoleManager(Set<Role> roles) {
        return new RoleRegistryImpl(roles);
    }

    public static OperationRegistry getOperationManager() {
        return new OperationRegistryImpl();
    }

    public static OperationRegistry getOperationManager(Set<Operation> operations) {
        return new OperationRegistryImpl(operations);
    }

    public static ProjectRegistry getProjectManager() {
        return new ProjectRegistryImpl();
    }

    public static ProjectRegistry getProjectManager(Set<Project> projects) {
        return new ProjectRegistryImpl(projects);
    }

    public static AuthenticationManager getAuthenticationManager() {
        return new AuthenticationManagerImpl(Utils.getAuthenticationDetailsSet());
    }

    public static AuthenticationManager getAuthenticationManager(Set<AuthenticationDetails> authDetails) {
        return new AuthenticationManagerImpl(authDetails);
    }


    /*   policy and server/client configurations   */

    public static Server getServer(ServerConfiguration configuration, EntityIriGenerator idGenerator) {
        return new ServerImpl(configuration, idGenerator);
    }

    public static Client getClient(ClientConfiguration configuration) {
        return new ClientImpl(configuration);
    }

    public static ServerConfiguration getServerConfiguration() {
        return getServerConfiguration(Utils.getHost(), Utils.getMetaproject(), Utils.getAuthenticationManager(), Utils.getPropertyMap(), Utils.getEntityIriStatus());
    }

    public static ServerConfiguration getServerConfiguration(Host host, Metaproject metaproject, AuthenticationManager authenticationManager, Map<String, String> properties, EntityIriStatus idStatus) {
        return new ServerConfigurationImpl.Builder()
                .setHost(host)
                .setMetaproject(metaproject)
                .setAuthenticationManager(authenticationManager)
                .setPropertyMap(properties)
                .setEntityIriStatus(idStatus)
                .createServerConfiguration();
    }

    public static ClientConfiguration getClientConfiguration() {
        return getClientConfiguration(Utils.getMetaproject(), random.nextInt(), Utils.getGUIRestrictionSet(), Utils.getPropertyMap());
    }

    public static ClientConfiguration getClientConfiguration(Metaproject metaproject, int syncDelay, Set<GuiRestriction> disabledUIComponents, Map<String,String> propertyMap) {
        return new ClientConfigurationImpl(metaproject, syncDelay, disabledUIComponents, propertyMap);
    }

    public static Host getHost() {
        return getHost(Utils.getAddress(), getPort(random.nextInt()), getRegistryPort(random.nextInt()));
    }

    public static Host getHost(Address address, Port port, RegistryPort registryPort) {
        return new HostImpl(address, port, registryPort);
    }

    public static Port getPort(int port) {
        return new PortImpl(port);
    }

    public static RegistryPort getRegistryPort(int port) {
        return new RegistryPortImpl(port);
    }

    public static Metaproject getMetaproject() {
        return getMetaproject(Utils.getPolicyManager(Utils.getUserRoleMap()), Utils.getUserManager(Utils.getUserSet()),
                Utils.getRoleManager(Utils.getRoleSet()), Utils.getOperationManager(Utils.getOperationSet()), Utils.getProjectManager(Utils.getProjectSet()));
    }

    public static Metaproject getMetaproject(Policy policy, UserRegistry userRegistry, RoleRegistry roleRegistry, OperationRegistry operationRegistry, ProjectRegistry projectRegistry) {
        return new MetaprojectImpl.Builder()
                .setPolicy(policy)
                .setUserRegistry(userRegistry)
                .setRoleRegistry(roleRegistry)
                .setOperationRegistry(operationRegistry)
                .setProjectRegistry(projectRegistry)
                .createMetaproject();
    }

    public static Map<UserId,Map<ProjectId, Set<RoleId>>> getUserRoleMap() {
        Map<UserId, Map<ProjectId,Set<RoleId>>> map = new HashMap<>();
        for(int i = 0; i < DEFAULT_SET_SIZE; i++) {
            Map<ProjectId,Set<RoleId>> assignments = new HashMap<>();
            assignments.put(Utils.getProjectId(), Utils.getRoleIdSet());
            assignments.put(Utils.getProjectId(), Utils.getRoleIdSet());
            map.put(Utils.getUserId(), assignments);
        }
        return map;
    }

    public static Map<String,String> getPropertyMap() {
        Map<String,String> map = new HashMap<>();
        map.put("prop1", "value1");
        map.put("prop2", "value2");
        return map;
    }


    /*   access control policy objects   */

    public static Project getProject() {
        return getProject(getProjectId(), getName(), getDescription(), getAddress(), getUserId(), getUserIdSet());
    }

    public static Project getProject(ProjectId id, Name name, Description description, Address address, UserId owner, Set<UserId> administrators) {
        return new ProjectImpl(id, name, description, address, owner, administrators);
    }

    public static Operation getOperation() {
        return getOperation(getOperationId(), getName(), getDescription(), DEFAULT_OPERATION_TYPE, Optional.of(getOperationRestrictionSet(getOperationRestriction())));
    }

    public static Operation getOperation(OperationId id, Name operationName, Description description, OperationType type, Optional<Set<OperationRestriction>> restrictions) {
        return new OperationImpl(id, operationName, description, type, restrictions);
    }

    public static Role getRole() {
        return getRole(getRoleId(), getName(), getDescription(), getOperationIdSet(DEFAULT_SET_SIZE));
    }

    public static Role getRole(OperationId operation) {
        Set<OperationId> operations = new HashSet<>();
        operations.add(operation);

        return getRole(getRoleId(), getName(), getDescription(), operations);
    }

    public static Role getRole(RoleId id, Name name, Description description, Set<OperationId> operations) {
        return new RoleImpl(id, name, description, operations);
    }

    public static User getUser() {
        return getUser(getUserId(), getName(), getEmailAddress());
    }

    public static User getUser(UserId userId, Name name, EmailAddress emailAddress) {
        return new UserImpl(userId, name, emailAddress);
    }


    /*   sets of access control policy objects   */

    public static Set<User> getUserSet() {
        return getUserSet(DEFAULT_SET_SIZE);
    }

    public static Set<User> getUserSet(int size) {
        Set<User> users = new HashSet<>();
        for(int i = 0; i < size; i++) {
            users.add(getUser());
        }
        return users;
    }

    public static Set<User> getUserSet(User... users) {
        Set<User> userSet = new HashSet<>();
        Collections.addAll(userSet, users);
        return userSet;
    }

    public static Set<Role> getRoleSet() {
        return getRoleSet(DEFAULT_SET_SIZE);
    }

    public static Set<Role> getRoleSet(int size) {
        Set<Role> roles = new HashSet<>();
        for(int i = 0; i < size; i++) {
            roles.add(getRole());
        }
        return roles;
    }

    public static Set<Role> getRoleSet(Role... roles) {
        Set<Role> roleSet = new HashSet<>();
        Collections.addAll(roleSet, roles);
        return roleSet;
    }

    public static Set<Operation> getOperationSet() {
        return getOperationSet(DEFAULT_SET_SIZE);
    }

    public static Set<Operation> getOperationSet(int size) {
        Set<Operation> operations = new HashSet<>();
        for(int i = 0; i < size; i++) {
            operations.add(getOperation());
        }
        return operations;
    }

    public static Set<Operation> getOperationSet(Operation... operations) {
        Set<Operation> operationSet = new HashSet<>();
        Collections.addAll(operationSet, operations);
        return operationSet;
    }

    public static Set<Project> getProjectSet() {
        return getProjectSet(DEFAULT_SET_SIZE);
    }

    public static Set<Project> getProjectSet(int size) {
        Set<Project> projects = new HashSet<>();
        for(int i = 0; i < size; i++) {
            projects.add(getProject());
        }
        return projects;
    }

    public static Set<Project> getProjectSet(Project... projects) {
        Set<Project> projectSet = new HashSet<>();
        Collections.addAll(projectSet, projects);
        return projectSet;
    }


    /*   sets of identifiers   */

    public static Set<UserId> getUserIdSet() {
        return getUserIdSet(DEFAULT_SET_SIZE);
    }

    public static Set<UserId> getUserIdSet(int size) {
        Set<UserId> userIdSet = new HashSet<>();
        for(int i = 0; i < size; i++) {
            userIdSet.add(getUserId());
        }
        return userIdSet;
    }

    public static Set<UserId> getUserIdSet(String... userIds) {
        Set<UserId> userIdSet = new HashSet<>();
        for(String userId : userIds) {
            userIdSet.add(getUserId(userId));
        }
        return userIdSet;
    }

    public static Set<UserId> getUserIdSet(UserId... userIds) {
        Set<UserId> userIdSet = new HashSet<>();
        Collections.addAll(userIdSet, userIds);
        return userIdSet;
    }

    public static Set<OperationId> getOperationIdSet() {
        return getOperationIdSet(DEFAULT_SET_SIZE);
    }

    public static Set<OperationId> getOperationIdSet(int size) {
        Set<OperationId> operationIdSet = new HashSet<>();
        for(int i = 0; i < size; i++) {
            operationIdSet.add(getOperationId());
        }
        return operationIdSet;
    }

    public static Set<OperationId> getOperationIdSet(String... operationIds) {
        Set<OperationId> operationIdSet = new HashSet<>();
        for(String operationId : operationIds) {
            operationIdSet.add(getOperationId(operationId));
        }
        return operationIdSet;
    }

    public static Set<ProjectId> getProjectIdSet() {
        return getProjectIdSet(DEFAULT_SET_SIZE);
    }

    public static Set<ProjectId> getProjectIdSet(int size) {
        Set<ProjectId> projectIdSet = new HashSet<>();
        for(int i = 0; i < size; i++) {
            projectIdSet.add(getProjectId());
        }
        return projectIdSet;
    }

    public static Set<ProjectId> getProjectIdSet(String... projectIds) {
        Set<ProjectId> projectIdSet = new HashSet<>();
        for(String projectId : projectIds) {
            projectIdSet.add(getProjectId(projectId));
        }
        return projectIdSet;
    }

    public static Set<RoleId> getRoleIdSet(RoleId... roleIds) {
        Set<RoleId> roles = new HashSet<>();
        Collections.addAll(roles, roleIds);
        return roles;
    }

    public static Set<RoleId> getRoleIdSet() {
        return getRoleIdSet(DEFAULT_SET_SIZE);
    }

    public static Set<RoleId> getRoleIdSet(int size) {
        Set<RoleId> roleSet = new HashSet<>();
        for(int i = 0; i < size; i++) {
            roleSet.add(getRoleId());
        }
        return roleSet;
    }


    /*   sets of other things   */

    public static Set<OperationRestriction> getOperationRestrictionSet(OperationRestriction... operationRestrictions) {
        Set<OperationRestriction> operationRestrictionSet = new HashSet<>();
        Collections.addAll(operationRestrictionSet, operationRestrictions);
        return operationRestrictionSet;
    }

    public static Set<AuthenticationDetails> getAuthenticationDetailsSet() {
        return getAuthenticationDetailsSet(DEFAULT_SET_SIZE);
    }

    public static Set<AuthenticationDetails> getAuthenticationDetailsSet(int size) {
        Set<AuthenticationDetails> set = new HashSet<>();
        for(int i = 0; i < size; i++) {
            set.add(getAuthenticationDetails());
        }
        return set;
    }

    public static Set<AuthenticationDetails> getAuthenticationDetailsSet(AuthenticationDetails... details) {
        Set<AuthenticationDetails> set = new HashSet<>();
        Collections.addAll(set, details);
        return set;
    }

    public static Set<GuiRestriction> getGUIRestrictionSet() {
        Set<GuiRestriction> set = new HashSet<>();
        for(int i = 0; i < DEFAULT_SET_SIZE; i++) {
            set.add(Utils.getGUIRestriction());
        }
        return set;
    }

    public static GuiRestriction getGUIRestriction() {
        return getGUIRestriction("button" + newUUID(), GuiRestriction.Visibility.VISIBLE);
    }

    public static GuiRestriction getGUIRestriction(String componentName, GuiRestriction.Visibility visibility) {
        return new GuiRestrictionImpl(componentName, visibility);
    }


    /*   entity IRI generation   */

    public static UuidPrefixedNameEntityIriGenerator getUuidPrefixedNameEntityIriGenerator(EntityIriPrefix iriPrefix,
            EntityNamePrefix classIdPrefix, EntityNamePrefix objectPropertyIdPrefix, EntityNamePrefix dataPropertyIdPrefix,
            EntityNamePrefix annotationPropertyIdPrefix, EntityNamePrefix individualIdPrefix)
    {
        return new UuidPrefixedNameEntityIriGenerator.Builder()
                .setEntityIriPrefix(iriPrefix)
                .setClassNamePrefix(classIdPrefix)
                .setObjectPropertyNamePrefix(objectPropertyIdPrefix)
                .setDataPropertyNamePrefix(dataPropertyIdPrefix)
                .setAnnotationPropertyNamePrefix(annotationPropertyIdPrefix)
                .setIndividualNamePrefix(individualIdPrefix)
                .createPrefixedUUIDGenerator();
    }

    public static SequentialPrefixedNameEntityIriGenerator getSequentialPrefixedNameEntityIriGenerator(EntityIriPrefix iriPrefix,
            EntityNamePrefix classIdPrefix, EntityNamePrefix objectPropertyIdPrefix, EntityNamePrefix dataPropertyIdPrefix,
            EntityNamePrefix annotationPropertyIdPrefix, EntityNamePrefix individualIdPrefix,
            EntityNameSuffix classIdSuffix, EntityNameSuffix objPropSuffix, EntityNameSuffix dataPropSuffix,
            EntityNameSuffix annPropSuffix, EntityNameSuffix indSuffix)
    {
        return new SequentialPrefixedNameEntityIriGenerator.Builder()
                .setEntityIriPrefix(iriPrefix)
                .setClassNamePrefix(classIdPrefix)
                .setObjectPropertyNamePrefix(objectPropertyIdPrefix)
                .setDataPropertyNamePrefix(dataPropertyIdPrefix)
                .setAnnotationPropertyNamePrefix(annotationPropertyIdPrefix)
                .setIndividualNamePrefix(individualIdPrefix)
                .setClassNameSuffix(classIdSuffix)
                .setObjectPropertyNameSuffix(objPropSuffix)
                .setDataPropertyNameSuffix(dataPropSuffix)
                .setAnnotationPropertyNameSuffix(annPropSuffix)
                .setIndividualNameSuffix(indSuffix)
                .createSequentialPrefixedNameEntityIriGenerator();
    }

    public static UuidEntityIriGenerator getUuidEntityIriGenerator(EntityIriPrefix iriPrefix) {
        return new UuidEntityIriGenerator(iriPrefix);
    }

    public static EntityIriStatus getEntityIriStatus() {
        return getEntityIriStatus(Utils.getEntityIriPrefix(),
                Utils.getEntityNamePrefix(), Utils.getEntityNamePrefix(), Utils.getEntityNamePrefix(), Utils.getEntityNamePrefix(), Utils.getEntityNamePrefix(),
                Utils.getEntityNameSuffix(Integer.toString(random.nextInt())), Utils.getEntityNameSuffix(Integer.toString(random.nextInt())),
                Utils.getEntityNameSuffix(Integer.toString(random.nextInt())), Utils.getEntityNameSuffix(Integer.toString(random.nextInt())),
                Utils.getEntityNameSuffix(Integer.toString(random.nextInt())));
    }

    public static EntityIriStatus getEntityIriStatus(EntityIriPrefix iriPrefix, EntityNameSuffix classId, EntityNameSuffix objPropId,
                                                     EntityNameSuffix dataPropId, EntityNameSuffix annPropId, EntityNameSuffix indId)
    {
        return new EntityIriStatusImpl.Builder()
                .setEntityIriPrefix(iriPrefix)
                .setClassNamePrefix(Utils.getEntityNamePrefix("class"))
                .setObjectPropertyNamePrefix(Utils.getEntityNamePrefix("objprop"))
                .setDataPropertyNamePrefix(Utils.getEntityNamePrefix("dataprop"))
                .setClassNameSuffix(classId)
                .setObjectPropertyNameSuffix(objPropId)
                .setDataPropertyNameSuffix(dataPropId)
                .setAnnotationPropertyNameSuffix(annPropId)
                .setIndividualNameSuffix(indId)
                .createEntityIriStatus();
    }

    public static EntityIriStatus getEntityIriStatus(EntityIriPrefix iriPrefix, EntityNamePrefix classPrefix, EntityNamePrefix objPropPrefix, EntityNamePrefix dataPropPrefix,
                                                     EntityNamePrefix annPropPrefix, EntityNamePrefix indPrefix, EntityNameSuffix classId,
                                                     EntityNameSuffix objPropId, EntityNameSuffix dataPropId, EntityNameSuffix annPropId, EntityNameSuffix indId)
    {
        return new EntityIriStatusImpl.Builder()
                .setEntityIriPrefix(iriPrefix)
                .setClassNamePrefix(classPrefix)
                .setObjectPropertyNamePrefix(objPropPrefix)
                .setDataPropertyNamePrefix(dataPropPrefix)
                .setAnnotationPropertyNamePrefix(annPropPrefix)
                .setIndividualNamePrefix(indPrefix)
                .setClassNameSuffix(classId)
                .setObjectPropertyNameSuffix(objPropId)
                .setDataPropertyNameSuffix(dataPropId)
                .setAnnotationPropertyNameSuffix(annPropId)
                .setIndividualNameSuffix(indId)
                .createEntityIriStatus();
    }
}
