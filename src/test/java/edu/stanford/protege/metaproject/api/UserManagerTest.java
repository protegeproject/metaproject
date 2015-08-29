package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.exception.AccessControlObjectNotFoundException;
import edu.stanford.protege.metaproject.api.exception.UserAddressAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserAlreadyRegisteredException;
import edu.stanford.protege.metaproject.api.exception.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserManagerTest {
    private static final User user1 = Utils.getUser(), user2 = Utils.getUser(), user3 = Utils.getUser();
    private static final Set<User> userSet = Utils.getUserSet(user1, user2, user3);

    private UserManager userManager;

    @Before
    public void setUp() {
        userManager = Utils.getUserManager(userSet);
    }

    @Test
    public void testNotNull() {
        assertThat(userManager, is(not(equalTo(null))));
    }

    @Test
    public void testGetUsers() {
        assertThat(userManager.getUsers(), is(userSet));
    }

    @Test
    public void testGetUserById() throws UserNotFoundException {
        assertThat(userManager.getUser(user1.getId()), is(user1));
    }

    @Test
    public void testRemoveUser() throws AccessControlObjectNotFoundException {
        userManager.remove(user3);
        assertThat(userManager.getUsers().contains(user3), is(false));
    }

    @Test
    public void testAddUser() throws UserAddressAlreadyInUseException, UserAlreadyRegisteredException {
        User user4 = Utils.getUser();
        userManager.add(user4);
        assertThat(userManager.getUsers().contains(user4), is(true));
    }

    @Test
    public void testChangeEmailAddress() throws UserNotFoundException, UserAlreadyRegisteredException, UserAddressAlreadyInUseException {
        Address newEmailAddress = Utils.getAddress("new_test_email@test.com");
        userManager.changeEmailAddress(user2.getId(), newEmailAddress);
        assertThat(userManager.getUser(user2.getId()).getAddress(), is(newEmailAddress));
    }

    @Test
    public void testChangeName() throws UserNotFoundException, UserAlreadyRegisteredException, UserAddressAlreadyInUseException {
        Name newName = Utils.getName("new test name");
        userManager.changeName(user2.getId(), newName);
        assertThat(userManager.getUser(user2.getId()).getName(), is(newName));
    }

    @Test
    public void testGetGuestUserNotNull() {
        assertThat(userManager.getGuestUser(), is(not(equalTo(null))));
    }

    @Test
    public void testExists() {
        UserId userId = Utils.getUserId("newTestUserId");
        assertThat(userManager.exists(user1.getId()), is(true));
        assertThat(userManager.exists(userId), is(false));
    }

    @Test
    public void testGetUsersByName() {
        Set<User> userSet = new HashSet<>();
        userSet.add(user1);
        assertThat(userManager.getUsers(user1.getName()), is(userSet));
    }

    @Test
    public void testGetUsersByEmail() {
        Set<User> userSet = new HashSet<>();
        userSet.add(user1);
        assertThat(userManager.getUsers(user1.getAddress()), is(userSet));
    }
}
