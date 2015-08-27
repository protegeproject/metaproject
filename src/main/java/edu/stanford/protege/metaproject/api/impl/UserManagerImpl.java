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

    @Override
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

    @Override
    public void remove(User... user) throws UserNotFoundException {
        for(User u : user) {
            if (!this.users.contains(u)) {
                throw new UserNotFoundException("The specified user does not exist");
            }
            this.users.remove(u);
        }
    }

    @Override
    public Set<User> getUsers() {
        return users;
    }

    @Override
    public User getUser(UserId userId) throws UserNotFoundException {
        Optional<User> user = getUserOptional(userId);
        if(user.isPresent()) {
            return user.get();
        }
        else {
            throw new UserNotFoundException("The specified user identifier does not correspond to an existing user");
        }
    }

    @Override
    public Set<User> getUsers(Name userName) {
        return users.stream().filter(user -> user.getName().get().equals(userName.get())).collect(Collectors.toSet());
    }

    @Override
    public Set<User> getUsers(Address emailAddress) {
        return users.stream().filter(user -> user.getAddress().equals(emailAddress)).collect(Collectors.toSet());
    }

    @Override
    public User getGuestUser() {
        return new UserImpl(new UserIdImpl(GUEST_ID), new NameImpl(GUEST_NAME), new AddressImpl(GUEST_EMAIL));
    }

    @Override
    public void changeUserName(UserId userId, Name userName) throws UserNotFoundException, UserAddressAlreadyInUseException, UserAlreadyRegisteredException {
        User user = getUser(userId);
        remove(user);
        User newUser = new UserImpl(userId, userName, user.getAddress());
        add(newUser);
    }

    @Override
    public void changeEmailAddress(UserId userId, Address emailAddress) throws UserNotFoundException, UserAddressAlreadyInUseException, UserAlreadyRegisteredException {
        User user = getUser(userId);
        remove(user);
        User newUser = new UserImpl(userId, user.getName(), emailAddress);
        add(newUser);
    }

    @Override
    public boolean exists(AccessControlObjectId userId) {
        for(User user : users) {
            if(user.getId().equals(userId)) {
                return true;
            }
        }
        return false;
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
