package edu.stanford.protege.metaproject.impl;

import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.EmailAddressAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.IdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;
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
public class UserRegistryTest {
    private static final String toStringHead = UserRegistry.class.getSimpleName();
    private static final User user1 = Utils.getUser(), user2 = Utils.getUser(), user3 = Utils.getUser();
    private static final Set<User> userSet = Utils.getUserSet(user1, user2, user3);

    private UserRegistry userRegistry, otherUserRegistry, diffUserRegistry;

    @Before
    public void setUp() {
        userRegistry = Utils.getUserRegistry(userSet);
        otherUserRegistry = Utils.getUserRegistry(userSet);
        diffUserRegistry = Utils.getUserRegistry();
    }

    @Test
    public void testNotNull() {
        assertThat(userRegistry, is(not(equalTo(null))));
    }

    @Test
    public void testGetUsers() {
        assertThat(userRegistry.getEntries(), is(userSet));
    }

    @Test
    public void testGetUserById() throws UnknownMetaprojectObjectIdException {
        assertThat(userRegistry.get(user1.getId()), is(user1));
    }

    @Test
    public void testRemoveUser() {
        userRegistry.remove(user3);
        assertThat(userRegistry.getEntries().contains(user3), is(false));
    }

    @Test
    public void testAddUser() throws EmailAddressAlreadyInUseException, IdAlreadyInUseException {
        User user4 = Utils.getUser();
        userRegistry.add(user4);
        assertThat(userRegistry.getEntries().contains(user4), is(true));
    }

    @Test
    public void testChangeEmailAddress() throws UnknownMetaprojectObjectIdException, IdAlreadyInUseException, EmailAddressAlreadyInUseException {
        EmailAddress newEmailAddress = Utils.getEmailAddress("new_test_email@test.com");
        userRegistry.setEmailAddress(user2.getId(), newEmailAddress);
        assertThat(userRegistry.get(user2.getId()).getEmailAddress(), is(newEmailAddress));
    }

    @Test
    public void testChangeName() throws UnknownMetaprojectObjectIdException, IdAlreadyInUseException, EmailAddressAlreadyInUseException {
        Name newName = Utils.getName("new test name");
        userRegistry.setName(user2.getId(), newName);
        assertThat(userRegistry.get(user2.getId()).getName(), is(newName));
    }

    @Test
    public void testGetGuestUserNotNull() {
        assertThat(userRegistry.getGuestUser(), is(not(equalTo(null))));
    }

    @Test
    public void testExists() {
        UserId userId = Utils.getUserId("newTestUserId");
        assertThat(userRegistry.contains(user1.getId()), is(true));
        assertThat(userRegistry.contains(userId), is(false));
    }

    @Test
    public void testGetUsersByName() {
        Set<User> userSet = new HashSet<>();
        userSet.add(user1);
        assertThat(userRegistry.getEntries(user1.getName()), is(userSet));
    }

    @Test
    public void testGetUsersByEmail() {
        Set<User> userSet = new HashSet<>();
        userSet.add(user1);
        assertThat(userRegistry.getEntries(user1.getEmailAddress()), is(userSet));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(userRegistry, is(userRegistry));
    }

    @Test
    public void testEquals() {
        assertThat(userRegistry, is(otherUserRegistry));
    }

    @Test
    public void testNotEquals() {
        assertThat(userRegistry, is(not(diffUserRegistry)));
    }

    @Test
    public void testHashcode() {
        assertThat(userRegistry.hashCode(), is(otherUserRegistry.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(userRegistry.toString(), startsWith(toStringHead));
    }
}
