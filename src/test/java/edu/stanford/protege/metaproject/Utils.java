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
    private static final OperationType DEFAULT_OPERATION_TYPE = OperationType.READ;
    private static final OperationPrerequisite.Modifier DEFAULT_MODIFIER = OperationPrerequisite.Modifier.PRESENT;
    private static final int DEFAULT_SET_SIZE = 3;
    private static final Random random = new Random();

    /*   identifiers   */

    public static UserId getUserId() {
        return getUserId("userId" + random.nextInt());
    }

    public static UserId getUserId(String userId) {
        return new UserIdImpl(userId);
    }

    public static RoleId getRoleId() {
        return getRoleId("roleId" + random.nextInt());
    }

    public static RoleId getRoleId(String roleId) {
        return new RoleIdImpl(roleId);
    }

    public static ProjectId getProjectId() {
        return getProjectId("projectId" + random.nextInt());
    }

    public static ProjectId getProjectId(String projectId) {
        return new ProjectIdImpl(projectId);
    }

    public static OperationId getOperationId() {
        return getOperationId("operationId" + random.nextInt());
    }

    public static OperationId getOperationId(String operationId) {
        return new OperationIdImpl(operationId);
    }


    /*   basic elements   */

    public static Name getName() {
        return getName("name " + random.nextInt());
    }

    public static Name getName(String name) {
        return new NameImpl(name);
    }

    public static Description getDescription() {
        return getDescription("description " + random.nextInt());
    }

    public static Description getDescription(String description) {
        return new DescriptionImpl(description);
    }

    public static Address getAddress() {
        return getAddress("address " + random.nextInt());
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
        return getIRI("http://protege.stanford.edu/test/" + newUUID());
    }

    public static IRI getIRI(String iri) {
        return IRI.create(iri);
    }

    public static String newUUID() {
        return UUID.randomUUID().toString();
    }

    public static SaltedPassword getSaltedPassword(String password, Salt salt) {
        return new SaltedPasswordImpl(password, salt);
    }

    public static Salt getSalt(byte[] bytes) {
        return new SaltImpl(bytes);
    }

    public static UserAuthenticationDetails getUserAuthenticationDetails(UserId userId, SaltedPassword password, Optional<Salt> salt) {
        return new UserAuthenticationDetailsImpl(userId, password, salt);
    }

    public static OntologyTermIdPrefix getOntologyTermIdPrefix(String prefix) {
        return new OntologyTermIdPrefixImpl(prefix);
    }

    public static OntologyTermIdSuffix getOntologyTermIdSuffix(String suffix) {
        return new OntologyTermIdSuffixImpl(suffix);
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

    public static Role getRole(int setsSize) {
        return getRole(getRoleId(), getName(), getDescription(), getProjectIdSet(setsSize), getOperationIdSet(setsSize));
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

    /*   sets of other things   */

    public static Set<OperationPrerequisite> getOperationPrerequisiteSet(OperationPrerequisite... operationPrerequisites) {
        Set<OperationPrerequisite> operationPrerequisiteSet = new HashSet<>();
        Collections.addAll(operationPrerequisiteSet, operationPrerequisites);
        return operationPrerequisiteSet;
    }
}
