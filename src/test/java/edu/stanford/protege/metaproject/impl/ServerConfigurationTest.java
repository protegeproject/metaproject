package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.ConfigurationManager;
import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.UnknownUserIdException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ServerConfigurationTest {
    private static final String toStringHead = ServerConfiguration.class.getSimpleName();
    private final PolicyFactory factory = ConfigurationManager.getFactory();
    private final Host host = TestUtils.getHost();
    private final File root = TestUtils.getFile();
    private final Set<User> users = TestUtils.getUserSet();
    private final Set<Project> projects = TestUtils.getProjectSet();
    private final Set<Operation> operations = TestUtils.getOperationSet();
    private final Set<Role> roles = TestUtils.getRoleSet(operations);
    private final Set<AuthenticationDetails> authDetails = TestUtils.getAuthenticationDetailsSet(users);
    private final Map<UserId, Map<ProjectId, Set<RoleId>>> policyMap = TestUtils.getPolicyMap(users, projects, roles);
    private final Map<String,String> propertiesMap = TestUtils.getPropertyMap();

    private ServerConfiguration config, otherServerConfiguration, diffServerConfiguration;

    @Before
    public void setUp() {
        config = TestUtils.getServerConfiguration(host, root, policyMap, users, projects, roles, operations, authDetails, propertiesMap);
        otherServerConfiguration = TestUtils.getServerConfiguration(host, root, policyMap, users, projects, roles, operations, authDetails, propertiesMap);
        diffServerConfiguration = TestUtils.getServerConfiguration();
    }

    @Test
    public void testNotNull() {
        assertThat(config, is(not(equalTo(null))));
    }

    @Test
    public void testGetHost() {
        assertThat(config.getHost(), is(host));
    }

    @Test
    public void testGetServerRoot() throws Exception {
        assertThat(config.getServerRoot(), is(root));
    }

    @Test
    public void testGetProperties() {
        assertThat(config.getProperties(), is(propertiesMap));
    }

    @Test
    public void testGetProperty() throws Exception {
        String key = propertiesMap.keySet().iterator().next();
        assertThat(config.getProperty(key), is(propertiesMap.get(key)));
    }


    /* policy */

    @Test
    public void testGetPolicyMap() throws Exception {
        assertThat(config.getPolicyMap(), is(policyMap));
    }

    @Test
    public void testHasRole() throws Exception {
        UserId userId = policyMap.keySet().iterator().next();
        assertThat(config.hasRole(userId), is(true));
    }

    @Test
    public void testHasRoleInProject() throws Exception {
        UserId userId = policyMap.keySet().iterator().next();
        ProjectId projectId = policyMap.get(userId).keySet().iterator().next();
        assertThat(config.hasRole(userId, projectId), is(true));
    }

    @Test
    public void testHasSpecificRoleInProject() throws Exception {
        UserId userId = policyMap.keySet().iterator().next();
        Map<ProjectId,Set<RoleId>> map = policyMap.get(userId);
        ProjectId projectId = map.keySet().iterator().next();
        RoleId roleId = map.get(projectId).iterator().next();
        assertThat(config.hasRole(userId, projectId, roleId), is(true));
    }

    @Test
    public void testGetRoleIds() throws Exception {
        UserId userId = users.iterator().next().getId();
        Map<ProjectId, Set<RoleId>> map = policyMap.get(userId);
        Set<RoleId> output = new HashSet<>();
        Set<RoleId> outputInclGlobal = new HashSet<>();
        for(ProjectId pid : map.keySet()) {
            Set<RoleId> roleIds = map.get(pid);
            if(pid.equals(ConfigurationUtils.getUniversalProjectId())) {
                outputInclGlobal.addAll(roleIds);
            } else {
                output.addAll(roleIds);
            }
        }
        outputInclGlobal.addAll(output);
        assertThat(config.getRoleIds(userId, GlobalPermissions.EXCLUDED), is(output));
        assertThat(config.getRoleIds(userId, GlobalPermissions.INCLUDED), is(outputInclGlobal));
    }

    @Test
    public void testGetRoleIdsForProject() throws Exception {
        UserId userId = users.iterator().next().getId();
        ProjectId projectId = projects.iterator().next().getId();
        Set<RoleId> roleIds = policyMap.get(userId).get(projectId);
        Set<RoleId> globalRoleIds = new HashSet<>(policyMap.get(userId).get(ConfigurationUtils.getUniversalProjectId()));
        globalRoleIds.addAll(roleIds);
        assertThat(config.getRoleIds(userId, projectId, GlobalPermissions.EXCLUDED), is(roleIds));
        assertThat(config.getRoleIds(userId, projectId, GlobalPermissions.INCLUDED), is(globalRoleIds));
    }

    @Test
    public void testGetProjectIds() throws Exception {
        UserId userId = users.iterator().next().getId();
        Set<ProjectId> projectIds = new HashSet<>(policyMap.get(userId).keySet());
        assertThat(config.getProjectIds(userId), is(projectIds));
    }

    @Test
    public void testGetUserIds() throws Exception {
        Set<UserId> userIds = new HashSet<>();
        Project p = projects.iterator().next();
        for(UserId userId : policyMap.keySet()) {
            if(policyMap.get(userId).containsKey(p.getId())) {
                userIds.add(userId);
            }
        }
        assertThat(config.getUserIds(p.getId()), is(userIds));
    }

    @Test
    public void testIsOperationAllowed() throws Exception {
        UserId userId = users.iterator().next().getId();
        ProjectId pid = policyMap.get(userId).keySet().iterator().next();
        Set<RoleId> roleIds = policyMap.get(userId).get(pid);
        Set<OperationId> operationIds = config.getRole(roleIds.iterator().next()).getOperations();
        for(OperationId opid : operationIds) {
            assertThat(config.isOperationAllowed(opid, userId), is(true));
        }
    }

    @Test
    public void testIsOperationAllowedInProject() throws Exception {
        UserId userId = users.iterator().next().getId();
        ProjectId pid = policyMap.get(userId).keySet().iterator().next();
        Set<RoleId> roleIds = policyMap.get(userId).get(pid);
        Set<OperationId> operationIds = config.getRole(roleIds.iterator().next()).getOperations();
        for(OperationId opid : operationIds) {
            assertThat(config.isOperationAllowed(opid, pid, userId), is(true));
        }
    }

    @Test
    public void testGetUserRoleMap() throws Exception {
        UserId userId = policyMap.keySet().iterator().next();
        Map<ProjectId,Set<RoleId>> map = policyMap.get(userId);
        assertThat(config.getUserRoleMap(userId), is(map));
    }


    /* users */

    @Test
    public void testGetUsers() throws Exception {
        assertThat(config.getUsers(), is(users));
    }

    @Test
    public void testGetUser() throws Exception {
        User user1 = users.iterator().next();
        assertThat(config.getUser(user1.getId()), is(user1));
    }

    @Test(expected=UnknownUserIdException.class)
    public void testGetUserFail() throws Exception {
        assertThat(config.getUser(factory.getUserId("testGetUserFail")), is(not(equalTo(null))));
    }

    @Test
    public void testGetUsersByName() throws Exception {
        User user1 = users.iterator().next();
        assertThat(config.getUsers(user1.getName()).contains(user1), is(true));
    }

    @Test
    public void testGetUsersByEmail() throws Exception {
        User user1 = users.iterator().next();
        assertThat(config.getUsers(user1.getEmailAddress()).contains(user1), is(true));
    }

    @Test
    public void testContainsUser() throws Exception {
        User user1 = users.iterator().next();
        assertThat(config.containsUser(user1), is(true));
    }

    @Test
    public void testContainsUserById() throws Exception {
        User user1 = users.iterator().next();
        assertThat(config.containsUser(user1.getId()), is(true));
    }

    @Test
    public void testIsEmailAddressInUse() throws Exception {
        User user1 = users.iterator().next();
        assertThat(config.isEmailAddressInUse(user1.getEmailAddress()), is(true));
    }


    /* projects */

    @Test
    public void testGetProjects() throws Exception {
        assertThat(config.getProjects(), is(projects));
    }

    @Test
    public void testGetProject() throws Exception {
        Project project = projects.iterator().next();
        assertThat(config.getProject(project.getId()), is(project));
    }

    @Test
    public void testGetProjectsByName() throws Exception {
        Project project = projects.iterator().next();
        assertThat(config.getProjects(project.getName()).contains(project), is(true));
    }

    @Test
    public void testGetProjectsByUser() throws Exception {
        UserId userId = policyMap.keySet().iterator().next();
        ProjectId projectId = policyMap.get(userId).keySet().iterator().next();
        Project project = config.getProject(projectId);
        assertThat(config.getProjects(userId).contains(project), is(true));
    }

    @Test
    public void testContainsProject() throws Exception {
        Project project = projects.iterator().next();
        assertThat(config.containsProject(project), is(true));
    }

    @Test
    public void testContainsProjectById() throws Exception {
        Project project = projects.iterator().next();
        assertThat(config.containsProject(project.getId()), is(true));
    }


    /* roles */

    @Test
    public void testGetRoles() throws Exception {
        assertThat(config.getRoles(), is(roles));
    }

    @Test
    public void testGetRole() throws Exception {
        Role role = roles.iterator().next();
        assertThat(config.getRole(role.getId()), is(role));
    }

    @Test
    public void testGetRolesForUser() throws Exception {
        UserId userId = policyMap.keySet().iterator().next();
        Map<ProjectId,Set<RoleId>> map = policyMap.get(userId);
        ProjectId pid = null;
        for(ProjectId projectId : map.keySet()) {
            if(!projectId.equals(ConfigurationUtils.getUniversalProjectId())) {
                pid = projectId;
                break;
            }
        }
        Set<RoleId> roles = map.get(pid);
        for(RoleId rid : roles) {
            assertThat(config.getRoles(userId, GlobalPermissions.EXCLUDED).contains(config.getRole(rid)), is(true));
        }
        assertThat(config.getRoles(userId, GlobalPermissions.EXCLUDED).contains(ConfigurationUtils.getAdminRole()), is(false));
        assertThat(config.getRoles(userId, GlobalPermissions.INCLUDED).contains(ConfigurationUtils.getAdminRole()), is(true));
    }

    @Test
    public void testGetRolesForUserInProject() throws Exception {
        UserId userId = policyMap.keySet().iterator().next();
        Map<ProjectId,Set<RoleId>> map = policyMap.get(userId);
        Set<Role> roleSet = new HashSet<>();
        ProjectId project = null;
        for(ProjectId pid : map.keySet()) {
            if(!pid.equals(ConfigurationUtils.getUniversalProjectId())) {
                project = pid;
                Set<RoleId> roles = map.get(pid);
                for (RoleId rid : roles) {
                    Role role = config.getRole(rid);
                    assertThat(config.getRoles(userId, pid, GlobalPermissions.EXCLUDED).contains(role), is(true));
                    roleSet.add(role);
                }
                break;
            }
        }
        Set<RoleId> roleIdsInclGlobal = new HashSet<>(map.get(ConfigurationUtils.getUniversalProjectId()));
        Set<Role> rolesInclGlobal = new HashSet<>(roleSet);
        for(RoleId rid : roleIdsInclGlobal) {
            rolesInclGlobal.add(config.getRole(rid));
        }
        assertThat(config.getRoles(userId, project, GlobalPermissions.EXCLUDED).containsAll(roleSet), is(true));
        assertThat(config.getRoles(userId, project, GlobalPermissions.INCLUDED).containsAll(rolesInclGlobal), is(true));
        assertThat(config.getRoles(userId, project, GlobalPermissions.EXCLUDED).containsAll(rolesInclGlobal), is(false));
    }

    @Test
    public void testContainsRole() throws Exception {
        Role role = roles.iterator().next();
        assertThat(config.containsRole(role), is(true));
    }

    @Test
    public void testContainsRoleById() throws Exception {
        Role role = roles.iterator().next();
        assertThat(config.containsRole(role.getId()), is(true));
    }


    /* operations */

    @Test
    public void testGetOperations() throws Exception {
        assertThat(config.getOperations(), is(operations));
    }

    @Test
    public void testGetOperation() throws Exception {
        Operation operation = operations.iterator().next();
        assertThat(config.getOperation(operation.getId()), is(operation));
    }

    @Test
    public void testGetOperationsForUser() throws Exception {
        UserId userId = policyMap.keySet().iterator().next();
        Map<ProjectId,Set<RoleId>> map = policyMap.get(userId);
        Set<RoleId> roleIds = new HashSet<>(), roleIdsInclGlobal = new HashSet<>();
        for(ProjectId pid : map.keySet()) {
            if(!pid.equals(ConfigurationUtils.getUniversalProjectId())) {
                roleIds.addAll(map.get(pid));
            } else {
                roleIdsInclGlobal.addAll(map.get(pid));
            }
        }
        roleIdsInclGlobal.addAll(roleIds);
        Set<Role> roles = new HashSet<>();
        Set<Role> rolesInclGlobal = new HashSet<>();
        for(RoleId rid : roleIds) {
            roles.add(config.getRole(rid));
        }
        for(RoleId rid : roleIdsInclGlobal) {
            rolesInclGlobal.add(config.getRole(rid));
        }
        Set<Operation> operations = config.getOperations(roles);
        Set<Operation> operationsInclGlobal = config.getOperations(rolesInclGlobal);
        assertThat(config.getOperations(userId, GlobalPermissions.INCLUDED).containsAll(operationsInclGlobal), is(true));
        assertThat(config.getOperations(userId, GlobalPermissions.EXCLUDED).containsAll(operations), is(true));
        assertThat(config.getOperations(userId, GlobalPermissions.EXCLUDED).containsAll(operationsInclGlobal), is(false));
    }

    @Test
    public void testGetOperationsForUserInProject() throws Exception {
        UserId userId = policyMap.keySet().iterator().next();
        Map<ProjectId,Set<RoleId>> map = policyMap.get(userId);
        Set<RoleId> roleIds = new HashSet<>();
        ProjectId project = null;
        for(ProjectId pid : map.keySet()) {
            if(!pid.equals(ConfigurationUtils.getUniversalProjectId()) && project == null) {
                project = pid;
                roleIds.addAll(map.get(pid));
            }
        }
        Set<RoleId> roleIdsInclGlobal = new HashSet<>(map.get(ConfigurationUtils.getUniversalProjectId()));
        roleIdsInclGlobal.addAll(roleIds);

        Set<Role> roles = new HashSet<>();
        Set<Role> rolesInclGlobal = new HashSet<>();
        for(RoleId rid : roleIds) {
            roles.add(config.getRole(rid));
        }
        for(RoleId rid : roleIdsInclGlobal) {
            rolesInclGlobal.add(config.getRole(rid));
        }
        Set<Operation> operations = config.getOperations(roles);
        Set<Operation> operationsInclGlobal = config.getOperations(rolesInclGlobal);
        assertThat(config.getOperations(userId, project, GlobalPermissions.INCLUDED).containsAll(operationsInclGlobal), is(true));
        assertThat(config.getOperations(userId, project, GlobalPermissions.EXCLUDED).containsAll(operations), is(true));
        assertThat(config.getOperations(userId, project, GlobalPermissions.EXCLUDED).containsAll(operationsInclGlobal), is(false));
    }

    @Test
    public void testGetOperationsForRoles() throws Exception {
        Iterator<Role> iterator = roles.iterator();
        Role role1 = iterator.next();
        Role role2 = iterator.next();
        Set<OperationId> operationIds = new HashSet<>(role1.getOperations());
        operationIds.addAll(role2.getOperations());
        Set<Operation> operations = new HashSet<>();
        for(OperationId id : operationIds) {
            operations.add(config.getOperation(id));
        }
        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        roles.add(role2);
        assertThat(config.getOperations(roles), is(operations));
    }

    @Test
    public void testGetOperationsForRole() throws Exception {
        Role role = roles.iterator().next();
        Set<OperationId> operationIds = role.getOperations();
        Set<Operation> operations = new HashSet<>();
        for(OperationId id : operationIds) {
            operations.add(config.getOperation(id));
        }
        assertThat(config.getOperations(role), is(operations));
    }

    @Test
    public void testContainsOperation() throws Exception {
        Operation operation = operations.iterator().next();
        assertThat(config.containsOperation(operation), is(true));
    }

    @Test
    public void testContainsOperationById() throws Exception {
        Operation operation = operations.iterator().next();
        assertThat(config.containsOperation(operation.getId()), is(true));
    }


    /* authentication */

    @Test
    public void testGetAuthenticationDetails() throws Exception {
        assertThat(config.getAuthenticationDetails(), is(authDetails));
    }

    @Test
    public void testGetAuthenticationDetailsOfUser() throws Exception {
        AuthenticationDetails details = authDetails.iterator().next();
        assertThat(config.getAuthenticationDetails(details.getUserId()), is(details));
    }

    @Test
    public void testGetSalt() throws Exception {
        AuthenticationDetails details = authDetails.iterator().next();
        assertThat(config.getAuthenticationDetails(details.getUserId()), is(details));
    }

    @Test
    public void testIsRegistered() throws Exception {
        AuthenticationDetails details = authDetails.iterator().next();
        assertThat(config.isRegistered(details.getUserId()), is(true));
    }

    @Test
    public void testHasValidCredentials() throws Exception {
        User user = users.iterator().next();
        AuthenticationDetails details = config.getAuthenticationDetails(user.getId());
        assertThat(config.hasValidCredentials(user.getId(), details.getPassword()), is(true));
        assertThat(config.hasValidCredentials(user.getId(), factory.getSaltedPasswordDigest("testPwd", factory.getSalt("testSalt"))), is(false));
    }


    @Test
    public void testEqualToSelf() {
        assertThat(config, is(config));
    }

    @Test
    public void testEquals() {
        assertThat(config, is(otherServerConfiguration));
    }

    @Test
    public void testNotEquals() {
        assertThat(config, is(not(diffServerConfiguration)));
    }

    @Test
    public void testHashcode() {
        assertThat(config.hashCode(), is(otherServerConfiguration.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(config.toString(), startsWith(toStringHead));
    }
}
