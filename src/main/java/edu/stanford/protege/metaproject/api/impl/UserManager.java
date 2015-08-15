package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.UserNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A manager for users and user details (except password, which is handled by {@link AuthenticationManager}).
 * The user manager follows the singleton pattern; only one instance of this manager may exist.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class UserManager implements Manager {
    private static UserManager instance = null;
    private static Set<User> users;

    /**
     * Private constructor
     *
     * @param users Set of users
     */
    private UserManager(Set<User> users) {
        this.users = checkNotNull(users);
    }

    /**
     * Get the singleton instance of the user manager. If the instance has not been created, or the given set of users is different
     * than that in the existing instance, then a new user manager instance is created with the given user set
     *
     * @param userSet   Set of users
     * @return User manager
     */
    public static UserManager getInstance(Set<User> userSet) {
        if(instance == null || !users.equals(userSet)) {
            instance = new UserManager(userSet);
        }
        return instance;
    }

    /**
     * Get the singleton instance of the user manager. If the instance has not been created, then a user manager instance is
     * created with an empty set of users
     *
     * @return User manager
     */
    public static UserManager getInstance() {
        if(instance == null) {
            instance = new UserManager(new HashSet<>());
        }
        return instance;
    }

    /**
     * Add a user
     *
     * @param user  User instance
     */
    public void addUser(User user) {
        users.add(checkNotNull(user));
    }

    /**
     * Add a given set of users
     *
     * @param users    Set of users
     */
    public void addUsers(Set<User> users) {
        users.forEach(this::addUser);
    }

    /**
     * Remove a user
     *
     * @param user User instance
     * @throws UserNotFoundException  User does not exist
     */
    public void removeUser(User user) throws UserNotFoundException {
        if(!users.contains(user)) {
            throw new UserNotFoundException("The specified user does not exist");
        }
        users.remove(user);
    }

    /**
     * Remove a given set of users
     *
     * @param users Set of users
     * @throws UserNotFoundException    User not found
     */
    public void removeUsers(Set<User> users) throws UserNotFoundException {
        for(User user : users) {
            removeUser(user);
        }
    }

    /**
     * Get all users
     *
     * @return Set of all users
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Get the user with the specified identifier
     *
     * @param userId  User identifier
     * @return User instance
     */
    public Optional<User> getUserOptional(Id userId) {
        User userFound = null;
        for(User user : users) {
            if(user.getId().equals(userId)) {
                userFound = user;
                break;
            }
        }
        return Optional.ofNullable(userFound);
    }

    /**
     * A convenience method to fetch a user or die trying (with an exception)
     *
     * @param userId    User identifier
     * @return User instance
     * @throws UserNotFoundException    User not found
     */
    public User getUser(Id userId) throws UserNotFoundException {
        Optional<User> user = getUserOptional(userId);
        if(user.isPresent()) {
            return user.get();
        }
        else {
            throw new UserNotFoundException("The specified user identifier does not correspond to an existing user");
        }
    }

    /**
     * Get the user(s) registered with the specified name
     *
     * @param userName  User name
     * @return Set of users
     */
    public Set<User> getUsers(Name userName) {
        return users.stream().filter(user -> user.getName().equals(userName)).collect(Collectors.toSet());
    }

    /**
     * Get the user(s) registered with the specified email address
     *
     * @param emailAddress  Email address
     * @return Set of users
     */
    public Set<User> getUsers(Address emailAddress) {
        return users.stream().filter(user -> user.getAddress().equals(emailAddress)).collect(Collectors.toSet());
    }

    /**
     * Change the display name of the given user
     *
     * @param user    User
     * @param userName  New name
     * @throws UserNotFoundException  User does not exist
     */
    public void changeUserName(User user, Name userName) throws UserNotFoundException {
        removeUser(user);
        User newUser = new UserImpl(user.getId(), userName, user.getAddress());
        addUser(newUser);
    }

    /**
     * Change the email address of a user
     *
     * @param user  User
     * @param emailAddress New email address
     * @throws UserNotFoundException  User does not exist
     */
    public void changeEmailAddress(User user, Address emailAddress) throws UserNotFoundException {
        removeUser(user);
        User newUser = new UserImpl(user.getId(), user.getName(), emailAddress);
        addUser(newUser);
    }

    /**
     * Verify whether a given user identifier corresponds to a registered user
     *
     * @param userId User identifier
     * @return true if user with the given user identifier exists, false otherwise
     */
    public boolean exists(Id userId) {
        for(User user : users) {
            if(user.getId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserManager that = (UserManager) o;
        return Objects.equal(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(users);
    }
}
