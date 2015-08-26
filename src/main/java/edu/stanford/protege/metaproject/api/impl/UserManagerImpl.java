package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
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
public class UserManagerImpl implements UserManager, Serializable {
    private static final String GUEST_ID = "guest", GUEST_NAME = "guest user", GUEST_EMAIL = "";
    private static final long serialVersionUID = 4737112809180106195L;
    private Set<User> users = new HashSet<>();

    /**
     * Constructor
     *
     * @param users Set of users
     */
    public UserManagerImpl(Set<User> users) {
        this.users = checkNotNull(users);
    }

    /**
     * No-arguments constructor
     */
    public UserManagerImpl() { }

    /**
     * Add user(s)
     *
     * @param users  One or more users
     * @throws UserAddressAlreadyInUseException Email address already in use by another user
     * @throws UserAlreadyRegisteredException   Identifier of given user is already in use
     */
    public void add(User... users) throws UserAddressAlreadyInUseException, UserAlreadyRegisteredException {
        for(User user : users) {
            if (exists(user.getId())) {
                throw new UserAlreadyRegisteredException("The specified user identifier is already used by another user");
            }
            if (isAddressUsed(user.getAddress())) {
                throw new UserAddressAlreadyInUseException("The specified user address is already used by another user.");
            }
            this.users.add(user);
        }
    }

    /**
     * Remove the given user(s)
     *
     * @param user One or more users
     * @throws UserNotFoundException  User does not exist
     */
    public void remove(User... user) throws UserNotFoundException {
        for(User u : user) {
            if (!this.users.contains(u)) {
                throw new UserNotFoundException("The specified user does not exist");
            }
            this.users.remove(u);
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
    private Optional<User> getUserOptional(UserId userId) {
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
        return users.stream().filter(user -> user.getName().get().equals(userName.get())).collect(Collectors.toSet());
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
     * Get a guest user instance
     *
     * @return Guest user
     */
    public User getGuestUser() {
        return new UserImpl(new UserIdImpl(GUEST_ID), new NameImpl(GUEST_NAME), new AddressImpl(GUEST_EMAIL));
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
        remove(user);
        User newUser = new UserImpl(userId, userName, user.getAddress());
        add(newUser);
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
        remove(user);
        User newUser = new UserImpl(userId, user.getName(), emailAddress);
        add(newUser);
    }

    /**
     * Verify whether a given user identifier corresponds to a registered user
     *
     * @param userId User identifier
     * @return true if user with the given user identifier exists, false otherwise
     */
    public boolean exists(AccessControlObjectId userId) {
        for(User user : users) {
            if(user.getId().equals(userId)) {
                return true;
            }
        }
        return false;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserManagerImpl that = (UserManagerImpl) o;
        return Objects.equal(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(users);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("users", users)
                .toString();
    }
}
