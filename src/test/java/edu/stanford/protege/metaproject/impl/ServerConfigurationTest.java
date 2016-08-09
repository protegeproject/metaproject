//package edu.stanford.protege.metaproject.impl;
//
//import edu.stanford.protege.metaproject.Manager;
//import edu.stanford.protege.metaproject.Utils;
//import edu.stanford.protege.metaproject.api.*;
//import edu.stanford.protege.metaproject.api.exception.UnknownUserIdException;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.File;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.MatcherAssert.assertThat;
//
///**
// * @author Rafael Gonçalves <br>
// * Center for Biomedical Informatics Research <br>
// * Stanford University
// */
//public class ServerConfigurationTest {
//    private static final String toStringHead = ServerConfiguration.class.getSimpleName();
//    private PolicyFactory factory = Manager.getFactory();
//    private Host host = Utils.getHost();
//    private File root = Utils.getFile();
//    private Map<UserId, Map<ProjectId, Set<RoleId>>> policyMap = Utils.getPolicyMap();
//    private Set<User> users = Utils.getUserSet();
//    private Set<Project> projects = Utils.getProjectSet();
//    private Set<Role> roles = Utils.getRoleSet();
//    private Set<Operation> operations = Utils.getOperationSet();
//    private Set<AuthenticationDetails> authDetails = Utils.getAuthenticationDetailsSet();
//    private Map<String,String> propertiesMap = Utils.getPropertyMap();
//    private ConfigurationManager manager = Manager.getConfigurationManager();
//
//    private ServerConfiguration config, otherServerConfiguration, diffServerConfiguration;
//
//    @Before
//    public void setUp() {
//        config = Utils.getConfiguration(manager, host, root, policyMap, users, projects, roles, operations, authDetails, propertiesMap);
//        otherServerConfiguration = Utils.getConfiguration(manager, host, root, policyMap, users, projects, roles, operations, authDetails, propertiesMap);
//        diffServerConfiguration = Utils.getConfiguration();
//    }
//
//    @Test
//    public void testNotNull() {
//        assertThat(config, is(not(equalTo(null))));
//    }
//
//    @Test
//    public void testGetConfigurationManager() throws Exception {
//        assertThat(config.getConfigurationManager(), is(manager));
//    }
//
//    @Test
//    public void testGetHost() {
//        assertThat(config.getHost(), is(host));
//    }
//
//    @Test
//    public void testGetServerRoot() throws Exception {
//        assertThat(config.getServerRoot(), is(root));
//    }
//
//    @Test
//    public void testGetProperties() {
//        assertThat(config.getProperties(), is(propertiesMap));
//    }
//
//    @Test
//    public void testGetProperty() throws Exception {
//        String key = propertiesMap.keySet().iterator().next();
//        assertThat(config.getProperty(key), is(propertiesMap.get(key)));
//    }
//
//
//    /* policy */
//
//    @Test
//    public void testGetPolicyMap() throws Exception {
//        assertThat(config.getPolicyMap(), is(policyMap));
//    }
//
//    @Test
//    public void testHasRole() throws Exception {
//
//    }
//
//    @Test
//    public void testGetRoleIds() throws Exception {
//
//    }
//
//    @Test
//    public void testGetRoleIds1() throws Exception {
//
//    }
//
//    @Test
//    public void testGetProjectIds() throws Exception {
//
//    }
//
//    @Test
//    public void testIsOperationAllowed() throws Exception {
//
//    }
//
//    @Test
//    public void testIsOperationAllowed1() throws Exception {
//
//    }
//
//    @Test
//    public void testHasRole1() throws Exception {
//
//    }
//
//    @Test
//    public void testGetUserRoleMap() throws Exception {
//
//    }
//
//    @Test
//    public void testGetUserIds() throws Exception {
//
//    }
//
//    @Test
//    public void testHasRole2() throws Exception {
//
//    }
//
//
//    /* users */
//
//    @Test
//    public void testGetUsers() throws Exception {
//        assertThat(config.getUsers(), is(users));
//    }
//
//    @Test
//    public void testGetUser() throws Exception {
//        User user1 = users.iterator().next();
//        assertThat(config.getUser(user1.getId()), is(user1));
//    }
//
//    @Test(expected=UnknownUserIdException.class)
//    public void testGetUserFail() throws Exception {
//        assertThat(config.getUser(factory.getUserId("testGetUserFail")), is(not(equalTo(null))));
//    }
//
//    @Test
//    public void testGetUsersByName() throws Exception {
//        User user1 = users.iterator().next();
//        assertThat(config.getUsers(user1.getName()).contains(user1), is(true));
//    }
//
//    @Test
//    public void testGetUsersByEmail() throws Exception {
//        User user1 = users.iterator().next();
//        assertThat(config.getUsers(user1.getEmailAddress()).contains(user1), is(true));
//    }
//
//    @Test
//    public void testContainsUser() throws Exception {
//        User user1 = users.iterator().next();
//        assertThat(config.containsUser(user1), is(true));
//    }
//
//    @Test
//    public void testContainsUserById() throws Exception {
//        User user1 = users.iterator().next();
//        assertThat(config.containsUser(user1.getId()), is(true));
//    }
//
//    @Test
//    public void testIsEmailAddressInUse() throws Exception {
//        User user1 = users.iterator().next();
//        assertThat(config.isEmailAddressInUse(user1.getEmailAddress()), is(true));
//    }
//
//
//    /* projects */
//
//    @Test
//    public void testGetProjects() throws Exception {
//        assertThat(config.getProjects(), is(projects));
//    }
//
//    @Test
//    public void testGetProject() throws Exception {
//        Project project = projects.iterator().next();
//        assertThat(config.getProject(project.getId()), is(project));
//    }
//
//    @Test
//    public void testGetProjectsByName() throws Exception {
//        Project project = projects.iterator().next();
//        assertThat(config.getProjects(project.getName()).contains(project), is(true));
//    }
//
//    @Test
//    public void testGetProjectsByUser() throws Exception {
//        UserId userId = policyMap.keySet().iterator().next();
//        ProjectId projectId = policyMap.get(userId).keySet().iterator().next();
//        config.getProject(proj)
//        assertThat(config.getProjects(project.getName()).contains(project), is(true));
//    }
//
//    @Test
//    public void testContainsProject() throws Exception {
//        Project project = projects.iterator().next();
//        assertThat(config.containsProject(project), is(true));
//    }
//
//    @Test
//    public void testContainsProjectById() throws Exception {
//        Project project = projects.iterator().next();
//        assertThat(config.containsProject(project.getId()), is(true));
//    }
//
//
//    /* roles */
//
//    @Test
//    public void testGetRoles() throws Exception {
//        assertThat(config.getRoles(), is(roles));
//    }
//
//    @Test
//    public void testGetRole() throws Exception {
//        Role role = roles.iterator().next();
//        assertThat(config.getRole(role.getId()), is(role));
//    }
//
//    @Test
//    public void testGetRoles1() throws Exception {
//
//    }
//
//    @Test
//    public void testGetRoles2() throws Exception {
//
//    }
//
//    @Test
//    public void testContainsRole() throws Exception {
//        Role role = roles.iterator().next();
//        assertThat(config.containsRole(role), is(true));
//    }
//
//    @Test
//    public void testContainsRoleById() throws Exception {
//        Role role = roles.iterator().next();
//        assertThat(config.containsRole(role.getId()), is(true));
//    }
//
//
//    /* operations */
//
//    @Test
//    public void testGetOperations() throws Exception {
//        assertThat(config.getOperations(), is(operations));
//    }
//
//    @Test
//    public void testGetOperation() throws Exception {
//        Operation operation = operations.iterator().next();
//        assertThat(config.getOperation(operation.getId()), is(operation));
//    }
//
//    @Test
//    public void testGetOperationsForUser() throws Exception {
//
//    }
//
//    @Test
//    public void testGetOperationsForUserInProject() throws Exception {
//
//    }
//
//    @Test
//    public void testGetOperationsForRoles() throws Exception {
//        Iterator<Role> iterator = roles.iterator();
//        Role role1 = iterator.next();
//        Role role2 = iterator.next();
//        Set<OperationId> operationIds = role1.getOperations();
//        operationIds.addAll(role2.getOperations());
//        Set<Operation> operations = new HashSet<>();
//        for(OperationId id : operationIds) {
//            operations.add(config.getOperation(id));
//        }
//        Set<Role> roles = new HashSet<>();
//        roles.add(role1);
//        roles.add(role2);
//        assertThat(config.getOperations(roles), is(operations));
//    }
//
//    @Test
//    public void testGetOperationsForRole() throws Exception {
//        Role role = roles.iterator().next();
//        Set<OperationId> operationIds = role.getOperations();
//        Set<Operation> operations = new HashSet<>();
//        for(OperationId id : operationIds) {
//            operations.add(config.getOperation(id));
//        }
//        assertThat(config.getOperations(role), is(operations));
//    }
//
//    @Test
//    public void testContainsOperation() throws Exception {
//        Operation operation = operations.iterator().next();
//        assertThat(config.containsOperation(operation), is(true));
//    }
//
//    @Test
//    public void testContainsOperationById() throws Exception {
//        Operation operation = operations.iterator().next();
//        assertThat(config.containsOperation(operation.getId()), is(true));
//    }
//
//
//    /* authentication */
//
//    @Test
//    public void testGetAuthenticationDetails() throws Exception {
//        assertThat(config.getAuthenticationDetails(), is(authDetails));
//    }
//
//    @Test
//    public void testGetAuthenticationDetails1() throws Exception {
//        AuthenticationDetails details = authDetails.iterator().next();
//        assertThat(config.getAuthenticationDetails(details.getUserId()), is(details));
//    }
//
//    @Test
//    public void testGetSalt() throws Exception {
//        AuthenticationDetails details = authDetails.iterator().next();
//        assertThat(config.getAuthenticationDetails(details.getUserId()), is(details));
//    }
//
//    @Test
//    public void testIsRegistered() throws Exception {
//        AuthenticationDetails details = authDetails.iterator().next();
//        assertThat(config.isRegistered(details.getUserId()), is(true));
//    }
//
//    @Test
//    public void testHasValidCredentials() throws Exception {
//
//    }
//
//
//    @Test
//    public void testEqualToSelf() {
//        assertThat(config, is(config));
//    }
//
//    @Test
//    public void testEquals() {
//        assertThat(config, is(otherServerConfiguration));
//    }
//
//    @Test
//    public void testNotEquals() {
//        assertThat(config, is(not(diffServerConfiguration)));
//    }
//
//    @Test
//    public void testHashcode() {
//        assertThat(config.hashCode(), is(otherServerConfiguration.hashCode()));
//    }
//
//    @Test
//    public void testToString() {
//        assertThat(config.toString(), startsWith(toStringHead));
//    }
//}
