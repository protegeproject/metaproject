package edu.stanford.protege.metaproject;

import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.impl.*;
import org.semanticweb.owlapi.model.IRI;

import java.util.*;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class Utils {
    private static final int DEFAULT_SET_SIZE = 3;
    private static final OperationType DEFAULT_OPERATION_TYPE = OperationType.READ;
    private static final OperationPrerequisite.Modifier DEFAULT_MODIFIER = OperationPrerequisite.Modifier.PRESENT;
    private static final String iriBase = "http://protege.stanford.edu/test/";
    private static final Random random = new Random();
    
    /*   access control object identifiers   */

    public static UserId getUserId() {
        return getUserId("userId" + newUUID());
    }

    public static UserId getUserId(String userId) {
        return new UserIdImpl(userId);
    }

    public static RoleId getRoleId() {
        return getRoleId("roleId" + newUUID());
    }

    public static RoleId getRoleId(String roleId) {
        return new RoleIdImpl(roleId);
    }

    public static ProjectId getProjectId() {
        return getProjectId("projectId" + newUUID());
    }

    public static ProjectId getProjectId(String projectId) {
        return new ProjectIdImpl(projectId);
    }

    public static OperationId getOperationId() {
        return getOperationId("operationId" + newUUID());
    }

    public static OperationId getOperationId(String operationId) {
        return new OperationIdImpl(operationId);
    }


    /*   ontology term identifiers   */

    public static OntologyTermIdPrefix getOntologyTermIdPrefix() {
        return getOntologyTermIdPrefix("prefix" + newUUID());
    }

    public static OntologyTermIdPrefix getOntologyTermIdPrefix(String prefix) {
        return new OntologyTermIdPrefixImpl(prefix);
    }

    public static OntologyTermIdSuffix getOntologyTermIdSuffix() {
        return getOntologyTermIdSuffix("" + random.nextInt(Integer.SIZE - 1));
    }

    public static OntologyTermIdSuffix getOntologyTermIdSuffix(String suffix) {
        return new OntologyTermIdSuffixImpl(suffix);
    }

    public static OntologyTermId getOntologyTermId() {
        return getOntologyTermId(Utils.getOntologyTermIdPrefix(), Utils.getOntologyTermIdSuffix());
    }

    public static OntologyTermId getOntologyTermId(OntologyTermIdPrefix prefix, OntologyTermIdSuffix suffix) {
        return new OntologyTermIdImpl(prefix, suffix);
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
        return getAddress("address " + newUUID());
    }

    public static Address getAddress(String address) {
        return new AddressImpl(address);
    }

    public static OperationPrerequisite getOperationPrerequisite() {
        return getOperationPrerequisite(DEFAULT_MODIFIER);
    }

    public static OperationPrerequisite getOperationPrerequisite(OperationPrerequisite.Modifier modifier) {
        return getOperationPrerequisite(getIRI(), modifier);
    }

    public static OperationPrerequisite getOperationPrerequisite(IRI iri, OperationPrerequisite.Modifier modifier) {
        return new OperationPrerequisiteImpl(iri, modifier);
    }

    public static IRI getIRI() {
        return getIRI(iriBase + newUUID());
    }

    public static IRI getIRI(String iri) {
        return IRI.create(iri);
    }

    public static String newUUID() {
        return UUID.randomUUID().toString();
    }


    /*   authentication   */

    public static SaltedPassword getSaltedPassword() {
        return getSaltedPassword("testPassword" + random.nextInt(), getSalt());
    }

    public static SaltedPassword getSaltedPassword(String password, Salt salt) {
        return new SaltedPasswordImpl(password, salt);
    }

    public static PlainPassword getPlainPassword() {
        return getPlainPassword("testPassword" + newUUID());
    }

    public static PlainPassword getPlainPassword(String password) {
        return new PlainPasswordImpl(password);
    }

    public static Salt getSalt() {
        return getSalt("salt" + newUUID());
    }

    public static Salt getSalt(String s) {
        return new SaltImpl(s.getBytes());
    }

    public static AuthenticationDetails getAuthenticationDetails() {
        return getAuthenticationDetails(getUserId(), getSaltedPassword());
    }

    public static AuthenticationDetails getAuthenticationDetails(UserId userId, SaltedPassword password) {
        return new AuthenticationDetailsImpl(userId, password);
    }

    public static SaltGenerator getSaltGenerator() {
        return new SaltGeneratorImpl();
    }

    public static SaltGenerator getSaltGenerator(int byteSize) {
        return new SaltGeneratorImpl(byteSize);
    }

    public static PasswordMaster getPasswordMaster() {
        return new PBKDF2PasswordMaster.Builder().createPasswordMaster();
    }

    public static PasswordMaster getPasswordMaster(int hashByteSize, int nrPBKDF2Iterations, SaltGenerator saltGenerator) {
        return new PBKDF2PasswordMaster.Builder()
                .setHashByteSize(hashByteSize)
                .setNrPBKDF2Iterations(nrPBKDF2Iterations)
                .setSaltGenerator(saltGenerator)
                .createPasswordMaster();
    }


    /*   access control policy managers   */

    public static UserManager getUserManager() {
        return new UserManagerImpl();
    }

    public static UserManager getUserManager(Set<User> users) {
        return new UserManagerImpl(users);
    }

    public static RoleManager getRoleManager() {
        return new RoleManagerImpl();
    }

    public static RoleManager getRoleManager(Set<Role> roles) {
        return new RoleManagerImpl(roles);
    }

    public static OperationManager getOperationManager() {
        return new OperationManagerImpl();
    }

    public static OperationManager getOperationManager(Set<Operation> operations) {
        return new OperationManagerImpl(operations);
    }

    public static ProjectManager getProjectManager() {
        return new ProjectManagerImpl();
    }

    public static ProjectManager getProjectManager(Set<Project> projects) {
        return new ProjectManagerImpl(projects);
    }

    public static AuthenticationManager getAuthenticationManager() {
        return new AuthenticationManagerImpl();
    }

    public static AuthenticationManager getAuthenticationManager(Set<AuthenticationDetails> authDetails) {
        return new AuthenticationManagerImpl(authDetails);
    }


    /*   policy and server/client configurations   */

    public static Server getServer(ServerConfiguration configuration, OntologyTermIdGenerator idGenerator) {
        return new ServerImpl(configuration, idGenerator);
    }

    public static Client getClient(ClientConfiguration configuration) {
        return new ClientImpl(configuration);
    }

    public static ServerConfiguration getServerConfiguration() {
        return getServerConfiguration(Utils.getHost(), Utils.getPolicy(), Utils.getStringPropertyMap(), Utils.getOntologyTermIdStatus());
    }

    public static ServerConfiguration getServerConfiguration(Host host, Policy policy, Map<String, String> properties, OntologyTermIdStatus idStatus) {
        return new ServerConfigurationImpl.Builder()
                .setHost(host)
                .setPolicy(policy)
                .setPropertyMap(properties)
                .setOntologyTermIdStatus(idStatus)
                .createServerConfiguration();
    }

    public static ClientConfiguration getClientConfiguration() {
        return getClientConfiguration(Utils.getPolicy(), random.nextInt(), Utils.getGUIRestrictionSet(), Utils.getStringPropertyMap());
    }

    public static ClientConfiguration getClientConfiguration(Policy policy, int syncDelay, Set<GUIRestriction> disabledUIComponents, Map<String,String> propertyMap) {
        return new ClientConfigurationImpl(policy, syncDelay, disabledUIComponents, propertyMap);
    }

    public static Host getHost() {
        return getHost(Utils.getAddress(), random.nextInt());
    }

    public static Host getHost(Address address, int port) {
        return new HostImpl(address, port);
    }

    public static Policy getPolicySample() {
        return new TestPolicy().getPolicy();
    }

    public static Policy getPolicy() {
        return getPolicy(Utils.getUserRoleMap(), Utils.getUserManager(), Utils.getRoleManager(), Utils.getOperationManager(), Utils.getProjectManager());
    }

    public static Policy getPolicy(Map<UserId,Set<RoleId>> userRoleMap, UserManager userManager, RoleManager roleManager, OperationManager operationManager, ProjectManager projectManager) {
        return new PolicyImpl.Builder()
                .setUserRoleMap(userRoleMap)
                .setUserManager(userManager)
                .setRoleManager(roleManager)
                .setOperationManager(operationManager)
                .setProjectManager(projectManager)
                .createAccessControlPolicy();
    }

    public static Map<UserId,Set<RoleId>> getUserRoleMap() {
        Map<UserId,Set<RoleId>> map = new HashMap<>();
        for(int i = 0; i < DEFAULT_SET_SIZE; i++) {
            map.put(Utils.getUserId(), Utils.getRoleIdSet());
        }
        return map;
    }

    public static Map<String,String> getStringPropertyMap() {
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
        return getOperation(getOperationId(), getName(), getDescription(), DEFAULT_OPERATION_TYPE, Optional.of(getOperationPrerequisiteSet(getOperationPrerequisite())));
    }

    public static Operation getOperation(OperationId id, Name operationName, Description description, OperationType type, Optional<Set<OperationPrerequisite>> prerequisites) {
        return new OperationImpl(id, operationName, description, type, prerequisites);
    }

    public static Role getRole() {
        return getRole(getRoleId(), getName(), getDescription(), getProjectIdSet(DEFAULT_SET_SIZE), getOperationIdSet(DEFAULT_SET_SIZE));
    }

    public static Role getRole(ProjectId project, OperationId operation) {
        Set<ProjectId> projects = new HashSet<>();
        projects.add(project);

        Set<OperationId> operations = new HashSet<>();
        operations.add(operation);

        return getRole(getRoleId(), getName(), getDescription(), projects, operations);
    }

    public static Role getRole(RoleId id, Name name, Description description, Set<ProjectId> projects, Set<OperationId> operations) {
        return new RoleImpl(id, name, description, projects, operations);
    }

    public static User getUser() {
        return getUser(getUserId(), getName(), getAddress());
    }

    public static User getUser(UserId userId, Name name, Address emailAddress) {
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

    public static Set<OperationPrerequisite> getOperationPrerequisiteSet(OperationPrerequisite... operationPrerequisites) {
        Set<OperationPrerequisite> operationPrerequisiteSet = new HashSet<>();
        Collections.addAll(operationPrerequisiteSet, operationPrerequisites);
        return operationPrerequisiteSet;
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

    public static Set<GUIRestriction> getGUIRestrictionSet() {
        Set<GUIRestriction> set = new HashSet<>();
        for(int i = 0; i < DEFAULT_SET_SIZE; i++) {
            set.add(Utils.getGUIRestriction());
        }
        return set;
    }

    public static GUIRestriction getGUIRestriction() {
        return getGUIRestriction("button" + newUUID(), GUIRestriction.Visibility.VISIBLE);
    }

    public static GUIRestriction getGUIRestriction(String componentName, GUIRestriction.Visibility visibility) {
        return new GUIRestrictionImpl(componentName, visibility);
    }


    /*   ontology term identifier generation   */

    public static OntologyTermPrefixedUUIDGenerator getOntologyTermPrefixedUUIDGenerator(
            OntologyTermIdPrefix classIdPrefix, OntologyTermIdPrefix objectPropertyIdPrefix, OntologyTermIdPrefix dataPropertyIdPrefix,
            OntologyTermIdPrefix annotationPropertyIdPrefix, OntologyTermIdPrefix individualIdPrefix)
    {
        return new OntologyTermPrefixedUUIDGenerator.Builder()
                .setClassIdPrefix(classIdPrefix)
                .setObjectPropertyIdPrefix(objectPropertyIdPrefix)
                .setDataPropertyIdPrefix(dataPropertyIdPrefix)
                .setAnnotationPropertyIdPrefix(annotationPropertyIdPrefix)
                .setIndividualIdPrefix(individualIdPrefix)
                .createPrefixedUUIDGenerator();
    }

    public static OntologyTermPrefixedSequentialIdGenerator getOntologyTermPrefixedSequentialIdGenerator(
            OntologyTermIdPrefix classIdPrefix, OntologyTermIdPrefix objectPropertyIdPrefix, OntologyTermIdPrefix dataPropertyIdPrefix,
            OntologyTermIdPrefix annotationPropertyIdPrefix, OntologyTermIdPrefix individualIdPrefix,
            OntologyTermIdSuffix classIdSuffix, OntologyTermIdSuffix objPropSuffix, OntologyTermIdSuffix dataPropSuffix,
            OntologyTermIdSuffix annPropSuffix, OntologyTermIdSuffix indSuffix)
    {
        return new OntologyTermPrefixedSequentialIdGenerator.Builder()
                .setClassIdPrefix(classIdPrefix)
                .setObjectPropertyIdPrefix(objectPropertyIdPrefix)
                .setDataPropertyIdPrefix(dataPropertyIdPrefix)
                .setAnnotationPropertyIdPrefix(annotationPropertyIdPrefix)
                .setIndividualIdPrefix(individualIdPrefix)
                .setClassIdSuffix(classIdSuffix)
                .setObjectPropertyIdSuffix(objPropSuffix)
                .setDataPropertyIdSuffix(dataPropSuffix)
                .setAnnotationPropertyIdSuffix(annPropSuffix)
                .setIndividualIdSuffix(indSuffix)
                .createSequentialPrefixedIdGenerator();
    }

    public static OntologyTermUUIDGenerator getOntologyTermUUIDGenerator() {
        return OntologyTermUUIDGenerator.getInstance();
    }

    public static OntologyTermIdStatus getOntologyTermIdStatus() {
        return getOntologyTermIdStatus(
                Utils.getOntologyTermIdSuffix("" + random.nextInt()),
                Utils.getOntologyTermIdSuffix("" + random.nextInt()),
                Utils.getOntologyTermIdSuffix("" + random.nextInt()),
                Utils.getOntologyTermIdSuffix("" + random.nextInt()),
                Utils.getOntologyTermIdSuffix("" + random.nextInt()));
    }

    public static OntologyTermIdStatus getOntologyTermIdStatus(OntologyTermIdSuffix classId, OntologyTermIdSuffix objPropId,
                                                               OntologyTermIdSuffix dataPropId, OntologyTermIdSuffix annPropId, OntologyTermIdSuffix indId)
    {
        return new OntologyTermIdStatusImpl.Builder()
                .setClassIdPrefix(Utils.getOntologyTermIdPrefix("class"))
                .setObjectPropertyIdPrefix(Utils.getOntologyTermIdPrefix("objprop"))
                .setDataPropertyIdPrefix(Utils.getOntologyTermIdPrefix("dataprop"))
                .setClassIdSuffix(classId)
                .setObjectPropertyIdSuffix(objPropId)
                .setDataPropertyIdSuffix(dataPropId)
                .setAnnotationPropertyIdSuffix(annPropId)
                .setIndividualIdSuffix(indId)
                .createOntologyTermIdStatus();
    }

    public static OntologyTermIdStatus getOntologyTermIdStatus(OntologyTermIdPrefix classPrefix, OntologyTermIdPrefix objPropPrefix, OntologyTermIdPrefix dataPropPrefix,
                                                               OntologyTermIdPrefix annPropPrefix, OntologyTermIdPrefix indPrefix, OntologyTermIdSuffix classId, OntologyTermIdSuffix objPropId,
                                                               OntologyTermIdSuffix dataPropId, OntologyTermIdSuffix annPropId, OntologyTermIdSuffix indId)
    {
        return new OntologyTermIdStatusImpl.Builder()
                .setClassIdPrefix(classPrefix)
                .setObjectPropertyIdPrefix(objPropPrefix)
                .setDataPropertyIdPrefix(dataPropPrefix)
                .setAnnotationPropertyIdPrefix(annPropPrefix)
                .setIndividualIdPrefix(indPrefix)
                .setClassIdSuffix(classId)
                .setObjectPropertyIdSuffix(objPropId)
                .setDataPropertyIdSuffix(dataPropId)
                .setAnnotationPropertyIdSuffix(annPropId)
                .setIndividualIdSuffix(indId)
                .createOntologyTermIdStatus();
    }
}
