package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.UnknownUserIdException;
import edu.stanford.protege.metaproject.api.exception.UserIdAlreadyInUseException;

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
public class UserRegistryImpl implements UserRegistry, Serializable {
    private static final long serialVersionUID = -4127144765876721800L;
    private Set<User> users = new HashSet<>();

    /**
     * Constructor
     *
     * @param users Set of users
     */
    public UserRegistryImpl(Set<User> users) {
        this.users = checkNotNull(users);
    }

    @Override
    public void add(User... users) throws UserIdAlreadyInUseException {
        checkNotNull(users);
        for(User user : users) {
            checkNotNull(user);
            if (contains(user.getId())) {
                throw new UserIdAlreadyInUseException("The specified user identifier is already used by another user");
            }
            this.users.add(user);
        }
    }

    @Override
    public void remove(User... users) {
        checkNotNull(users);
        for(User user : users) {
            checkNotNull(user);
            this.users.remove(user);
        }
    }

    @Override
    public Set<User> getUsers() {
        return users;
    }

    @Override
    public User getUser(UserId userId) throws UnknownUserIdException {
        Optional<User> user = getUserOptional(userId);
        if(user.isPresent()) {
            return user.get();
        }
        else {
            throw new UnknownUserIdException("The specified user identifier does not correspond to an existing user");
        }
    }

    @Override
    public Set<User> getUsers(Name userName) {
        checkNotNull(userName);
        return users.stream().filter(user -> user.getName().get().equals(userName.get())).collect(Collectors.toSet());
    }

    @Override
    public Set<User> getUsers(EmailAddress emailAddress) {
        checkNotNull(emailAddress);
        return users.stream().filter(user -> user.getEmailAddress().equals(emailAddress)).collect(Collectors.toSet());
    }

    @Override
    public User getGuestUser() {
        final String id = "guest", name = "Guest user", email = "";
        Factory f = Manager.getFactory();
        return createUser(f.getUserId(id), f.getName(name), f.getEmailAddress(email));
    }

    @Override
    public void setName(UserId userId, Name userName) throws UnknownUserIdException {
        checkNotNull(userName);
        User user = getUser(userId);
        remove(user);
        User newUser = createUser(userId, userName, user.getEmailAddress());
        try {
            add(newUser);
        } catch (UserIdAlreadyInUseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setEmailAddress(UserId userId, EmailAddress emailAddress) throws UnknownUserIdException {
        checkNotNull(emailAddress);
        User user = getUser(userId);
        remove(user);
        User newUser = createUser(userId, user.getName(), emailAddress);
        try {
            add(newUser);
        } catch (UserIdAlreadyInUseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isEmailAddressInUse(EmailAddress address) {
        checkNotNull(address);
        for(User u : users) {
            if(u.getEmailAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(UserId userId) {
        checkNotNull(userId);
        for(User user : users) {
            if(user.getId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create an instance of a user
     */
    private User createUser(UserId userId, Name name, EmailAddress emailAddress) {
        return Manager.getFactory().getUser(userId, name, emailAddress);
    }

    /**
     * Get the user with the specified identifier
     *
     * @param userId  User identifier
     * @return User instance
     */
    private Optional<User> getUserOptional(UserId userId) {
        checkNotNull(userId);
        User userFound = null;
        for(User user : users) {
            if(user.getId().equals(userId)) {
                userFound = user;
                break;
            }
        }
        return Optional.ofNullable(userFound);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistryImpl that = (UserRegistryImpl) o;
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
