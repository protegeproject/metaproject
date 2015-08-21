package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.UserAddressAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserAlreadyRegisteredException;
import edu.stanford.protege.metaproject.api.exception.UserNotFoundException;

import java.io.Serializable;
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
public class UserManager implements Manager, Serializable {
    private static final String GUEST_ID = "guest", GUEST_NAME = "guest user", GUEST_EMAIL = "";
    private static final long serialVersionUID = -2510957728872778738L;
    private Set<User> users = new HashSet<>();

    /**
     * Constructor
     *
     * @param users Set of users
     */
    public UserManager(Set<User> users) {
        this.users = checkNotNull(users);
    }

    /**
     * No-arguments constructor
     */
    public UserManager() { }

    /**
     * Create a new user instance with the given details
     *
     * @param userId    User identifier
     * @param userName  User name
     * @param emailAddress  User email address
     * @return New user instance
     */
    public User createUser(String userId, String userName, String emailAddress) {
        return new UserImpl(new UserIdImpl(userId), new NameImpl(userName), new AddressImpl(emailAddress));
    }

    /**
     * Add a user
     *
     * @param user  User instance
     * @throws UserAddressAlreadyInUseException Email address already in use by another user
     * @throws UserAlreadyRegisteredException   Identifier of given user is already in use
     */
    public void addUser(User user) throws UserAddressAlreadyInUseException, UserAlreadyRegisteredException {
        if(exists(user.getId())) {
            throw new UserAlreadyRegisteredException("The specified user identifier is already used by another user");
        }
        if(isAddressUsed(user.getAddress())) {
            throw new UserAddressAlreadyInUseException("The specified user address is already used by another user.");
        }
        users.add(user);
    }

    /**
     * Verify whether the email address of the given user is already being used by another user
     *
     * @param address   User address
     * @return true if email address is used by some other user, false otherwise
     */
    private boolean isAddressUsed(Address address) {
        for(User u : users) {
            if(u.getAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a given set of users
     *
     * @param users    Set of users
     * @throws UserAddressAlreadyInUseException Email address already in use by another user
     * @throws UserAlreadyRegisteredException   Identifier of given user is already in use
     */
    public void addUsers(Set<User> users) throws UserAddressAlreadyInUseException, UserAlreadyRegisteredException {
        for(User user : users) {
            addUser(user);
        }
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
    public Optional<User> getUserOptional(UserId userId) {
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
    public User getUser(UserId userId) throws UserNotFoundException {
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
     * @param userName  User name instance
     * @return Set of users with given name
     */
    public Set<User> getUsers(Name userName) {
        return getUsers(userName.get());
    }

    /**
     * Get the user(s) registerd with the specified name
     *
     * @param userName  User name string
     * @return Set of users with given name
     */
    public Set<User> getUsers(String userName) {
        return users.stream().filter(user -> user.getName().get().equals(userName)).collect(Collectors.toSet());
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
     * @param userId    User identifier
     * @param userName  New name
     * @throws UserNotFoundException  User does not exist
     * @throws UserAddressAlreadyInUseException Email address already in use by another user
     * @throws UserAlreadyRegisteredException   Identifier of given user is already in use
     */
    public void changeUserName(UserId userId, Name userName) throws UserNotFoundException, UserAddressAlreadyInUseException, UserAlreadyRegisteredException {
        User user = getUser(userId);
        removeUser(user);
        User newUser = new UserImpl(userId, userName, user.getAddress());
        addUser(newUser);
    }

    /**
     * Change the email address of a user
     *
     * @param userId  User identifier
     * @param emailAddress New email address
     * @throws UserNotFoundException  User does not exist
     * @throws UserAddressAlreadyInUseException Email address already in use by another user
     * @throws UserAlreadyRegisteredException   Identifier of given user is already in use
     */
    public void changeEmailAddress(UserId userId, Address emailAddress) throws UserNotFoundException, UserAddressAlreadyInUseException, UserAlreadyRegisteredException {
        User user = getUser(userId);
        removeUser(user);
        User newUser = new UserImpl(userId, user.getName(), emailAddress);
        addUser(newUser);
    }

    /**
     * Get a guest user instance
     *
     * @return Guest user
     */
    public User getGuestUser() {
        return new UserImpl(new UserIdImpl(GUEST_ID), new NameImpl(GUEST_NAME), new AddressImpl(GUEST_EMAIL));
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
