package edu.stanford.protege.metaproject;

import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.impl.ConfigurationBuilder;
import edu.stanford.protege.metaproject.impl.ConfigurationUtils;
import edu.stanford.protege.metaproject.impl.ProjectOptionsImpl;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class TestUtils {
    private static final int DEFAULT_SET_SIZE = 5;
    private static final OperationType DEFAULT_OPERATION_TYPE = OperationType.WRITE;
    private static final Random random = new Random();
    private static final String rootDir = "target/server-distribution/server/root", uri = "http://localhost:8008";
    private static PolicyFactory f = ConfigurationManager.getFactory();
    
    /*   policy object identifiers   */

    public static UserId getUserId() {
        return getUserId("userId-" + newUUID());
    }

    public static UserId getUserId(String userId) {
        return f.getUserId(userId);
    }

    public static RoleId getRoleId() {
        return getRoleId("roleId-" + newUUID());
    }

    public static RoleId getRoleId(String roleId) {
        return f.getRoleId(roleId);
    }

    public static ProjectId getProjectId() {
        return getProjectId("projectId-" + newUUID());
    }

    public static ProjectId getProjectId(String projectId) {
        return f.getProjectId(projectId);
    }

    public static OperationId getOperationId() {
        return getOperationId("operationId-" + newUUID());
    }

    public static OperationId getOperationId(String operationId) {
        return f.getOperationId(operationId);
    }


    /*   basic elements   */

    public static Name getName() {
        return getName("name " + newUUID());
    }

    public static Name getName(String name) {
        return f.getName(name);
    }

    public static Description getDescription() {
        return getDescription("description " + newUUID());
    }

    public static Description getDescription(String description) {
        return f.getDescription(description);
    }

    public static EmailAddress getEmailAddress() {
        return getEmailAddress(newUUID() + "@email.com");
    }

    public static EmailAddress getEmailAddress(String address) {
        return f.getEmailAddress(address);
    }

    public static String newUUID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }


    /*   authentication   */

    public static SaltedPasswordDigest getSaltedPassword() {
        return getSaltedPassword("testPassword" + random.nextInt(), getSalt());
    }

    public static SaltedPasswordDigest getSaltedPassword(String password, Salt salt) {
        return TestUtils.getPasswordHasher().hash(getPlainPassword(password), salt);
    }

    public static PlainPassword getPlainPassword() {
        return getPlainPassword("testPassword" + newUUID());
    }

    public static PlainPassword getPlainPassword(String password) {
        return f.getPlainPassword(password);
    }

    public static Salt getSalt() {
        return getSaltGenerator().generate();
    }

    public static Salt getSalt(String s) {
        return f.getSalt(s);
    }

    public static AuthenticationDetails getAuthenticationDetails() {
        return getAuthenticationDetails(getUserId(), getSaltedPassword());
    }

    public static AuthenticationDetails getAuthenticationDetails(UserId userId) {
        return getAuthenticationDetails(userId, getSaltedPassword());
    }

    public static AuthenticationDetails getAuthenticationDetails(UserId userId, SaltedPasswordDigest password) {
        return f.getAuthenticationDetails(userId, password);
    }

    public static SaltGenerator getSaltGenerator() {
        return f.getSaltGenerator();
    }

    public static PasswordHasher getPasswordHasher() {
        return f.getPasswordHasher();
    }

    public static PasswordHasher getPasswordHasher(int hashByteSize, int nrPBKDF2Iterations) {
        return f.getPasswordHasher(hashByteSize, nrPBKDF2Iterations);
    }


    /*   policy and server/client configurations   */

    public static ServerConfiguration getServerConfiguration() {
        return getServerConfiguration(TestUtils.getHost(), TestUtils.getFile(), TestUtils.getPolicyMap(), TestUtils.getUserSet(),
                TestUtils.getProjectSet(), TestUtils.getRoleSet(), TestUtils.getOperationSet(), TestUtils.getAuthenticationDetailsSet(), TestUtils.getPropertyMap());
    }

    public static ServerConfiguration getServerConfiguration(
            Host host, File root, Map<UserId, Map<ProjectId, Set<RoleId>>> policyMap, Set<User> users,
            Set<Project> projects, Set<Role> roles, Set<Operation> operations, Set<AuthenticationDetails> authDetails, Map<String,String> properties) {
        return new ConfigurationBuilder()
                .setHost(host)
                .setServerRoot(root)
                .setPolicyMap(policyMap)
                .setUsers(users)
                .setProjects(projects)
                .setRoles(roles)
                .setOperations(operations)
                .setAuthenticationDetails(authDetails)
                .setProperties(properties)
                .createServerConfiguration();
    }

    public static Host getHost() {
        return getHost(TestUtils.getUri(), Optional.of(getPort(random.nextInt())));
    }

    public static Host getHost(URI address, Optional<Port> optionalPort) {
        return f.getHost(address, optionalPort);
    }

    public static File getFile() {
        return getFile(rootDir + "/" + newUUID() + ".history");
    }

    public static File getFile(String path) {
        return new File(path);
    }

    public static Port getPort(int port) {
        return f.getPort(port);
    }

    public static URI getUri() {
        return getUri(uri + "/" + newUUID());
    }

    public static URI getUri(String uri) {
        URI newUri = null;
        try {
            newUri = new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return checkNotNull(newUri);
    }

    public static Map<UserId,Map<ProjectId, Set<RoleId>>> getPolicyMap() {
        Map<UserId, Map<ProjectId,Set<RoleId>>> map = new HashMap<>();
        for(int i = 0; i < DEFAULT_SET_SIZE; i++) {
            Map<ProjectId,Set<RoleId>> assignments = new HashMap<>();
            assignments.put(TestUtils.getProjectId(), TestUtils.getRoleIdSet());
            assignments.put(TestUtils.getProjectId(), TestUtils.getRoleIdSet());
            map.put(TestUtils.getUserId(), assignments);
        }
        return map;
    }

    public static Map<UserId,Map<ProjectId, Set<RoleId>>> getPolicyMap(Set<User> users, Set<Project> projects, Set<Role> roles) {
        Map<UserId, Map<ProjectId,Set<RoleId>>> policy = new HashMap<>();
        for(User user : users) {
            Map<ProjectId,Set<RoleId>> roleMap = new HashMap<>();
            for(Project project : projects) {
                Set<RoleId> roleSet = new HashSet<>();
                roleSet.add(getRandomRole(roles).getId());
                roleSet.add(getRandomRole(roles).getId());
                roleMap.put(project.getId(), roleSet);
            }
            roleMap.put(ConfigurationUtils.getUniversalProjectId(), Collections.singleton(ConfigurationUtils.getAdminRole().getId()));
            policy.put(user.getId(), roleMap);
        }
        return policy;
    }

    private static Role getRandomRole(Set<Role> roles) {
        Random rand = new Random();
        List<Role> roleList = new ArrayList<>(roles);
        Role role = null;
        while(role == null) {
            Role r = roleList.get(rand.nextInt(roles.size()));
            if(!ConfigurationUtils.getDefaultRoles().contains(r)) {
                role = r;
            }
        }
        return role;
    }

    public static Map<String,String> getPropertyMap() {
        Map<String,String> map = new HashMap<>();
        map.put("testProp1", "testVal1");
        map.put("testProp2", "testVal2");
        return map;
    }


    /*   access control policy objects   */

    public static Project getProject() {
        return getProject(getProjectId(), getName(), getDescription(), getUserId(), Optional.of(getProjectOptions()));
    }

    public static Project getProject(ProjectId id, Name name, Description description, UserId owner, Optional<ProjectOptions> projectOptions) {
        return f.getProject(id, name, description, owner, projectOptions);
    }

    public static Operation getSystemOperation() {
        return getSystemOperation(getOperationId(), getName(), getDescription(), DEFAULT_OPERATION_TYPE, Operation.Scope.SERVER);
    }

    public static Operation getSystemOperation(OperationId id, Name operationName, Description description, OperationType type, Operation.Scope scope) {
        return f.getSystemOperation(id, operationName, description, type, scope);
    }

    public static Operation getCustomOperation() {
        return getCustomOperation(getOperationId(), getName(), getDescription(), DEFAULT_OPERATION_TYPE, Operation.Scope.ONTOLOGY);
    }

    public static Operation getCustomOperation(OperationId id, Name operationName, Description description, OperationType type, Operation.Scope scope) {
        return f.getCustomOperation(id, operationName, description, type, scope);
    }

    public static Role getRole() {
        return getRole(getRoleId(), getName(), getDescription(), getOperationIdSet(DEFAULT_SET_SIZE));
    }

    public static Role getRole(OperationId... operation) {
        Set<OperationId> operations = new HashSet<>();
        for(OperationId operationId : operation) {
            operations.add(operationId);
        }
        return getRole(getRoleId(), getName(), getDescription(), operations);
    }

    public static Role getRole(RoleId id, Name name, Description description, Set<OperationId> operations) {
        return f.getRole(id, name, description, operations);
    }

    public static User getUser() {
        return getUser(getUserId(), getName(), getEmailAddress());
    }

    public static User getUser(UserId userId, Name name, EmailAddress emailAddress) {
        return f.getUser(userId, name, emailAddress);
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

    public static Set<Role> getRoleSet(Set<Operation> operations) {
        Set<Role> roleSet = new HashSet<>();
        roleSet.addAll(ConfigurationUtils.getDefaultRoles());
        for(Operation op : operations) {
            roleSet.add(getRole(op.getId()));
        }
        return roleSet;
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
        operations.addAll(ConfigurationUtils.getDefaultOperations());
        for(int i = 0; i < size; i++) {
            operations.add(getSystemOperation());
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

    public static Set<AuthenticationDetails> getAuthenticationDetailsSet() {
        return getAuthenticationDetailsSet(DEFAULT_SET_SIZE);
    }

    public static Set<AuthenticationDetails> getAuthenticationDetailsSet(Set<User> users) {
        Set<AuthenticationDetails> auth = new HashSet<>();
        for(User user : users) {
            auth.add(getAuthenticationDetails(user.getId()));
        }
        return auth;
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

    public static Set<String> getStringSet() {
        Set<String> set = new HashSet<>();
        for(int i = 0; i < DEFAULT_SET_SIZE; i++) {
            set.add(newUUID());
        }
        return set;
    }

    /*  maps of things  */

    public static Map<String,String> getStringMap() {
        Map<String,String> map = new HashMap<>();
        for(int i = 0; i < DEFAULT_SET_SIZE; i++) {
            map.put(newUUID(), newUUID());
        }
        return map;
    }

    public static Map<String,Set<String>> getStringToStringSetMap() {
        Map<String,Set<String>> map = new HashMap<>();
        for(int i = 0; i < DEFAULT_SET_SIZE; i++) {
            map.put(newUUID(), getStringSet());
        }
        return map;
    }

    public static ProjectOptions getProjectOptions() {
        return getProjectOptions(getStringToStringSetMap());
    }

    public static ProjectOptions getProjectOptions(Map<String, Set<String>> options) {
        return new ProjectOptionsImpl(options);
    }
}
