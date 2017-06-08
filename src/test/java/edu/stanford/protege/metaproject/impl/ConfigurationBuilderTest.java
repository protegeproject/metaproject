package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.ConfigurationManager;
import edu.stanford.protege.metaproject.TestUtils;
import edu.stanford.protege.metaproject.api.*;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ConfigurationBuilderTest {
    private final PolicyFactory factory = ConfigurationManager.getFactory();

    @Test
    public void testCreateServerConfiguration() throws Exception {
        ServerConfiguration config = new ConfigurationBuilder().createServerConfiguration();
        assertThat(config, is(not(equalTo(null))));
    }

    @Test
    public void testSetHost() throws Exception {
        Host host = factory.getHost(URI.create("http://protege.stanford.edu"), Optional.empty());
        ServerConfiguration config = new ConfigurationBuilder().setHost(host).createServerConfiguration();
        assertThat(config.getHost(), is(host));
    }

    @Test
    public void testSetServerRoot() throws Exception {
        String root = "test";
        ServerConfiguration config = new ConfigurationBuilder().setServerRoot(root).createServerConfiguration();
        assertThat(config.getServerRoot(), is(root));
    }

    @Test
    public void testSetPolicyMap() throws Exception {
        Map<UserId, Map<ProjectId,Set<RoleId>>> policyMap = new HashMap<>();
        Map<ProjectId,Set<RoleId>> roles = new HashMap<>();
        roles.put(ConfigurationUtils.getUniversalProjectId(), Collections.singleton(ConfigurationUtils.getAdminRole().getId()));
        policyMap.put(ConfigurationUtils.getRootUser().getId(), roles);
        ServerConfiguration config = new ConfigurationBuilder().setPolicyMap(policyMap).createServerConfiguration();
        assertThat(config.getPolicyMap(), is(policyMap));
    }

    @Test
    public void testSetRoles() throws Exception {
        Set<Role> roles = TestUtils.getRoleSet();
        ServerConfiguration config = new ConfigurationBuilder().setRoles(roles).createServerConfiguration();
        assertThat(config.getRoles(), is(roles));
    }

    @Test
    public void testSetOperations() throws Exception {
        Set<Operation> operations = TestUtils.getOperationSet();
        ServerConfiguration config = new ConfigurationBuilder().setOperations(operations).createServerConfiguration();
        assertThat(config.getOperations(), is(operations));
    }

    @Test
    public void testSetUsers() throws Exception {
        Set<User> users = TestUtils.getUserSet();
        ServerConfiguration config = new ConfigurationBuilder().setUsers(users).createServerConfiguration();
        assertThat(config.getUsers(), is(users));
    }

    @Test
    public void testSetProjects() throws Exception {
        Set<Project> projects = TestUtils.getProjectSet();
        ServerConfiguration config = new ConfigurationBuilder().setProjects(projects).createServerConfiguration();
        assertThat(config.getProjects(), is(projects));
    }

    @Test
    public void testSetAuthenticationDetails() throws Exception {
        Set<AuthenticationDetails> authDetails = TestUtils.getAuthenticationDetailsSet();
        ServerConfiguration config = new ConfigurationBuilder().setAuthenticationDetails(authDetails).createServerConfiguration();
        assertThat(config.getAuthenticationDetails(), is(authDetails));
    }

    @Test
    public void testSetProperties() throws Exception {
        Map<String,String> map = TestUtils.getPropertyMap();
        ServerConfiguration config = new ConfigurationBuilder().setProperties(map).createServerConfiguration();
        assertThat(config.getProperties(), is(map));
    }

    @Test
    public void testAddProperty() throws Exception {
        String key = "key", value = "value";
        ServerConfiguration config = new ConfigurationBuilder().addProperty(key, value).createServerConfiguration();
        assertThat(config.getProperty(key), is(value));
        assertThat(config.getProperty("non-existent-key"), is(equalTo(null)));
    }

    @Test
    public void testRemoveProperty() throws Exception {
        String key = "key", value = "value";
        ServerConfiguration config = new ConfigurationBuilder().addProperty(key, value).createServerConfiguration();
        assertThat(config.getProperty(key), is(value));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).removeProperty(key).createServerConfiguration();
        assertThat(configAlt.getProperty(key), is(equalTo(null)));
    }

    @Test
    public void testAddUser() throws Exception {
        User user = TestUtils.getUser();
        ServerConfiguration config = new ConfigurationBuilder().addUser(user).createServerConfiguration();
        assertThat(config.containsUser(user), is(true));
    }

    @Test
    public void testRemoveUser() throws Exception {
        User user = TestUtils.getUser();
        ServerConfiguration config = new ConfigurationBuilder().addUser(user).createServerConfiguration();
        assertThat(config.containsUser(user), is(true));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).removeUser(user).createServerConfiguration();
        assertThat(configAlt.containsUser(user), is(false));
    }

    @Test
    public void testSetUser() throws Exception {
        User user = TestUtils.getUser();
        ServerConfiguration config = new ConfigurationBuilder().addUser(user).createServerConfiguration();
        assertThat(config.containsUser(user), is(true));

        User userAlt = factory.getUser(user.getId(), user.getName(), factory.getEmailAddress("some@email"));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setUser(user.getId(), userAlt).createServerConfiguration();
        assertThat(configAlt.containsUser(user), is(false));
        assertThat(configAlt.containsUser(userAlt), is(true));
        assertThat(configAlt.getUser(userAlt.getId()), is(userAlt));
    }

    @Test
    public void testSetUserName() throws Exception {
        User user = TestUtils.getUser();
        ServerConfiguration config = new ConfigurationBuilder().addUser(user).createServerConfiguration();
        assertThat(config.containsUser(user), is(true));
        Name name = factory.getName("testName");
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setUserName(user.getId(), name).createServerConfiguration();
        assertThat(configAlt.containsUser(user), is(false));
        assertThat(configAlt.getUser(user.getId()).getName(), is(name));
    }

    @Test
    public void testSetUserEmailAddress() throws Exception {
        User user = TestUtils.getUser();
        ServerConfiguration config = new ConfigurationBuilder().addUser(user).createServerConfiguration();
        assertThat(config.containsUser(user), is(true));
        EmailAddress email = factory.getEmailAddress("test@email");
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setUserEmailAddress(user.getId(), email).createServerConfiguration();
        assertThat(configAlt.containsUser(user), is(false));
        assertThat(configAlt.getUser(user.getId()).getEmailAddress(), is(email));
    }

    @Test
    public void testAddProject() throws Exception {
        Project project = TestUtils.getProject();
        ServerConfiguration config = new ConfigurationBuilder().addProject(project).createServerConfiguration();
        assertThat(config.containsProject(project), is(true));
        assertThat(config.hasRole(project.getOwner(), project.getId(), ConfigurationUtils.getProjectManagerRole().getId()), is(true));
    }

    @Test
    public void testRemoveProject() throws Exception {
        Project project = TestUtils.getProject();
        ServerConfiguration config = new ConfigurationBuilder().addProject(project).createServerConfiguration();
        assertThat(config.containsProject(project), is(true));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).removeProject(project).createServerConfiguration();
        assertThat(configAlt.containsProject(project), is(false));
    }

    @Test
    public void testSetProject() throws Exception {
        Project project = TestUtils.getProject();
        ServerConfiguration config = new ConfigurationBuilder().addProject(project).createServerConfiguration();
        assertThat(config.containsProject(project), is(true));

        Project projectAlt = factory.getProject(project.getId(), project.getName(), project.getDescription(),
                factory.getUserId("newOwner"), project.getOptions());
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setProject(project.getId(), projectAlt).createServerConfiguration();
        assertThat(configAlt.containsProject(project), is(false));
        assertThat(configAlt.containsProject(projectAlt), is(true));
        assertThat(configAlt.getProject(projectAlt.getId()), is(projectAlt));
    }

    @Test
    public void testSetProjectName() throws Exception {
        Project project = TestUtils.getProject();
        ServerConfiguration config = new ConfigurationBuilder().addProject(project).createServerConfiguration();
        assertThat(config.containsProject(project), is(true));
        Name name = factory.getName("testName");
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setProjectName(project.getId(), name).createServerConfiguration();
        assertThat(configAlt.containsProject(project), is(false));
        assertThat(configAlt.getProject(project.getId()).getName(), is(name));
    }

    @Test
    public void testSetProjectDescription() throws Exception {
        Project project = TestUtils.getProject();
        ServerConfiguration config = new ConfigurationBuilder().addProject(project).createServerConfiguration();
        assertThat(config.containsProject(project), is(true));
        Description description = factory.getDescription("testDescription");
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setProjectDescription(project.getId(), description).createServerConfiguration();
        assertThat(configAlt.containsProject(project), is(false));
        assertThat(configAlt.getProject(project.getId()).getDescription(), is(description));
    }

    @Test
    public void testSetProjectOwner() throws Exception {
        Project project = TestUtils.getProject();
        ServerConfiguration config = new ConfigurationBuilder().addProject(project).createServerConfiguration();
        assertThat(config.containsProject(project), is(true));
        UserId userId = factory.getUserId("testOwner");
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setProjectOwner(project.getId(), userId).createServerConfiguration();
        assertThat(configAlt.containsProject(project), is(false));
        assertThat(configAlt.getProject(project.getId()).getOwner(), is(userId));
    }

   

    @Test
    public void testSetProjectOptions() throws Exception {
        Project project = TestUtils.getProject();
        ServerConfiguration config = new ConfigurationBuilder().addProject(project).createServerConfiguration();
        assertThat(config.containsProject(project), is(true));
        ProjectOptions options = TestUtils.getProjectOptions();
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setProjectOptions(project.getId(), options).createServerConfiguration();
        assertThat(configAlt.containsProject(project), is(false));
        assertThat(configAlt.getProject(project.getId()).getOptions(), is(Optional.of(options)));
    }

    @Test
    public void testAddRole() throws Exception {
        Role role = TestUtils.getRole();
        ServerConfiguration config = new ConfigurationBuilder().addRole(role).createServerConfiguration();
        assertThat(config.containsRole(role), is(true));
    }

    @Test
    public void testRemoveRole() throws Exception {
        Role role = TestUtils.getRole();
        ServerConfiguration config = new ConfigurationBuilder().addRole(role).createServerConfiguration();
        assertThat(config.containsRole(role), is(true));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).removeRole(role).createServerConfiguration();
        assertThat(configAlt.containsRole(role), is(false));
    }

    @Test
    public void testSetRole() throws Exception {
        Role role = TestUtils.getRole();
        ServerConfiguration config = new ConfigurationBuilder().addRole(role).createServerConfiguration();
        assertThat(config.containsRole(role), is(true));
        Role roleAlt = factory.getRole(role.getId(), role.getName(), role.getDescription(), TestUtils.getOperationIdSet());
        assertThat(role, is(not(equalTo(roleAlt))));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setRole(role.getId(), roleAlt).createServerConfiguration();
        assertThat(configAlt.containsRole(role), is(false));
        assertThat(configAlt.containsRole(roleAlt), is(true));
        assertThat(configAlt.getRole(roleAlt.getId()), is(roleAlt));
    }

    @Test
    public void testSetRoleName() throws Exception {
        Role role = TestUtils.getRole();
        ServerConfiguration config = new ConfigurationBuilder().addRole(role).createServerConfiguration();
        assertThat(config.containsRole(role), is(true));
        Name name = factory.getName("testName");
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setRoleName(role.getId(), name).createServerConfiguration();
        assertThat(configAlt.containsRole(role), is(false));
        assertThat(configAlt.getRole(role.getId()).getName(), is(name));
    }

    @Test
    public void testSetRoleDescription() throws Exception {
        Role role = TestUtils.getRole();
        ServerConfiguration config = new ConfigurationBuilder().addRole(role).createServerConfiguration();
        assertThat(config.containsRole(role), is(true));
        Description description = factory.getDescription("testDescription");
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setRoleDescription(role.getId(), description).createServerConfiguration();
        assertThat(configAlt.containsRole(role), is(false));
        assertThat(configAlt.getRole(role.getId()).getDescription(), is(description));
    }

    @Test
    public void testAddOperationToRole() throws Exception {
        Role role = TestUtils.getRole();
        ServerConfiguration config = new ConfigurationBuilder().addRole(role).createServerConfiguration();
        assertThat(config.containsRole(role), is(true));
        OperationId opId = TestUtils.getOperationId();
        assertThat(role.getOperations().contains(opId), is(false));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).addOperationToRole(role.getId(), opId).createServerConfiguration();
        assertThat(configAlt.getRole(role.getId()).getOperations().contains(opId), is(true));
    }

    @Test
    public void testRemoveOperationFromRole() throws Exception {
        Role role = TestUtils.getRole();
        ServerConfiguration config = new ConfigurationBuilder().addRole(role).createServerConfiguration();
        assertThat(config.containsRole(role), is(true));
        OperationId opId = role.getOperations().iterator().next();
        ServerConfiguration configAlt = new ConfigurationBuilder(config).removeOperationFromRole(role.getId(), opId).createServerConfiguration();
        assertThat(configAlt.getRole(role.getId()).getOperations().contains(opId), is(false));
    }

    @Test
    public void testAddOperation() throws Exception {
        Operation operation = TestUtils.getCustomOperation();
        ServerConfiguration config = new ConfigurationBuilder().addOperation(operation).createServerConfiguration();
        assertThat(config.containsOperation(operation), is(true));
    }

    @Test
    public void testRemoveOperation() throws Exception {
        Operation operation = TestUtils.getCustomOperation();
        ServerConfiguration config = new ConfigurationBuilder().addOperation(operation).createServerConfiguration();
        assertThat(config.containsOperation(operation), is(true));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).removeOperation(operation).createServerConfiguration();
        assertThat(configAlt.containsOperation(operation), is(false));
    }

    @Test
    public void testSetOperation() throws Exception {
        Operation operation = TestUtils.getCustomOperation();
        ServerConfiguration config = new ConfigurationBuilder().addOperation(operation).createServerConfiguration();
        assertThat(config.containsOperation(operation), is(true));
        Operation opAlt = factory.getCustomOperation(operation.getId(), operation.getName(), operation.getDescription(), OperationType.READ, Operation.Scope.GUI);
        assertThat(operation, is(not(equalTo(opAlt))));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setOperation(operation.getId(), opAlt).createServerConfiguration();
        assertThat(configAlt.containsOperation(operation), is(false));
        assertThat(configAlt.containsOperation(opAlt), is(true));
        assertThat(configAlt.getOperation(opAlt.getId()), is(opAlt));
    }

    @Test
    public void testSetOperationName() throws Exception {
        Operation operation = TestUtils.getCustomOperation();
        ServerConfiguration config = new ConfigurationBuilder().addOperation(operation).createServerConfiguration();
        assertThat(config.containsOperation(operation), is(true));
        Name name = factory.getName("testName");
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setOperationName(operation.getId(), name).createServerConfiguration();
        assertThat(configAlt.containsOperation(operation), is(false));
        assertThat(configAlt.getOperation(operation.getId()).getName(), is(name));
    }

    @Test
    public void testSetOperationDescription() throws Exception {
        Operation operation = TestUtils.getCustomOperation();
        ServerConfiguration config = new ConfigurationBuilder().addOperation(operation).createServerConfiguration();
        assertThat(config.containsOperation(operation), is(true));
        Description description = factory.getDescription("testDescription");
        ServerConfiguration configAlt = new ConfigurationBuilder(config).setOperationDescription(operation.getId(), description).createServerConfiguration();
        assertThat(configAlt.containsOperation(operation), is(false));
        assertThat(configAlt.getOperation(operation.getId()).getDescription(), is(description));
    }

    @Test
    public void testAddPolicy() throws Exception {
        User user = TestUtils.getUser();
        Project project = TestUtils.getProject();
        Role role = TestUtils.getRole();
        ServerConfiguration config = new ConfigurationBuilder().addPolicy(user.getId(), project.getId(), role.getId()).createServerConfiguration();
        assertThat(config.hasRole(user.getId(), project.getId(), role.getId()), is(true));
    }

    @Test
    public void testRemovePolicy() throws Exception {
        User user = TestUtils.getUser();
        Project project = TestUtils.getProject();
        Role role = TestUtils.getRole();
        ServerConfiguration config = new ConfigurationBuilder().addPolicy(user.getId(), project.getId(), role.getId()).createServerConfiguration();
        assertThat(config.hasRole(user.getId(), project.getId(), role.getId()), is(true));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).removePolicy(user.getId(), project.getId(), role.getId()).createServerConfiguration();
        assertThat(configAlt.hasRole(user.getId(), project.getId(), role.getId()), is(false));
    }

    @Test
    public void testRemovePolicyFromProject() throws Exception {
        User user = TestUtils.getUser();
        Project project = TestUtils.getProject();
        Role role = TestUtils.getRole();
        ServerConfiguration config = new ConfigurationBuilder().addPolicy(user.getId(), project.getId(), role.getId()).createServerConfiguration();
        assertThat(config.hasRole(user.getId(), project.getId()), is(true));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).removePolicy(user.getId(), project.getId()).createServerConfiguration();
        assertThat(configAlt.hasRole(user.getId(), project.getId()), is(false));
    }

    @Test
    public void testRemoveUserFromPolicy() throws Exception {
        User user = TestUtils.getUser();
        Project project = TestUtils.getProject();
        Role role = TestUtils.getRole();
        ServerConfiguration config = new ConfigurationBuilder().addPolicy(user.getId(), project.getId(), role.getId()).createServerConfiguration();
        assertThat(config.hasRole(user.getId(), project.getId()), is(true));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).removePolicy(user.getId()).createServerConfiguration();
        assertThat(configAlt.hasRole(user.getId(), project.getId()), is(false));
    }

    @Test
    public void testRemoveProjectFromPolicy() throws Exception {
        User user = TestUtils.getUser();
        Project project = TestUtils.getProject();
        Role role = TestUtils.getRole();
        ServerConfiguration config = new ConfigurationBuilder().addPolicy(user.getId(), project.getId(), role.getId()).createServerConfiguration();
        assertThat(config.hasRole(user.getId(), project.getId()), is(true));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).removePolicy(project.getId()).createServerConfiguration();
        assertThat(configAlt.hasRole(user.getId(), project.getId()), is(false));
    }

    @Test
    public void testRemoveRoleFromPolicy() throws Exception {
        User user = TestUtils.getUser();
        Project project = TestUtils.getProject();
        Role role = TestUtils.getRole(), roleAlt = TestUtils.getRole();
        ServerConfiguration config = new ConfigurationBuilder().addPolicy(user.getId(), project.getId(), role.getId(), roleAlt.getId()).createServerConfiguration();
        assertThat(config.hasRole(user.getId(), project.getId(), role.getId()), is(true));
        assertThat(config.hasRole(user.getId(), project.getId(), roleAlt.getId()), is(true));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).removePolicy(role.getId()).createServerConfiguration();
        assertThat(configAlt.hasRole(user.getId(), project.getId(), role.getId()), is(false));
        assertThat(configAlt.hasRole(user.getId(), project.getId(), roleAlt.getId()), is(true));
    }

    @Test
    public void testRegisterUser() throws Exception {
        UserId userId = TestUtils.getUserId();
        SaltedPasswordDigest pwd = TestUtils.getSaltedPassword();
        SaltedPasswordDigest pwdAlt = TestUtils.getSaltedPassword();
        ServerConfiguration config = new ConfigurationBuilder().registerUser(userId, pwd).createServerConfiguration();
        assertThat(config.hasValidCredentials(userId, pwd), is(true));
        assertThat(config.hasValidCredentials(userId, pwdAlt), is(false));
    }

    @Test
    public void testUnregisterUser() throws Exception {
        UserId userId = TestUtils.getUserId();
        SaltedPasswordDigest pwd = TestUtils.getSaltedPassword();
        ServerConfiguration config = new ConfigurationBuilder().registerUser(userId, pwd).createServerConfiguration();
        assertThat(config.hasValidCredentials(userId, pwd), is(true));
        ServerConfiguration configAlt = new ConfigurationBuilder(config).unregisterUser(userId).createServerConfiguration();
        assertThat(configAlt.hasValidCredentials(userId, pwd), is(false));
    }

    @Test
    public void testChangePassword() throws Exception {
        UserId userId = TestUtils.getUserId();
        SaltedPasswordDigest pwd = TestUtils.getSaltedPassword();
        ServerConfiguration config = new ConfigurationBuilder().registerUser(userId, pwd).createServerConfiguration();
        assertThat(config.hasValidCredentials(userId, pwd), is(true));
        SaltedPasswordDigest pwdAlt = TestUtils.getSaltedPassword();
        ServerConfiguration configAlt = new ConfigurationBuilder(config).changePassword(userId, pwdAlt).createServerConfiguration();
        assertThat(configAlt.hasValidCredentials(userId, pwd), is(false));
        assertThat(configAlt.hasValidCredentials(userId, pwdAlt), is(true));
    }
}