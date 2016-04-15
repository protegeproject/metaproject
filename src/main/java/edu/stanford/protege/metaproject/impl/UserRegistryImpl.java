package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.Manager;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.IdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;
import edu.stanford.protege.metaproject.api.exception.UnknownUserIdException;

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
    private static final long serialVersionUID = -1890146077901635672L;
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
    public void add(User user) throws IdAlreadyInUseException {
        checkNotNull(user);
        if (contains(user.getId())) {
            throw new IdAlreadyInUseException("The specified user identifier is already used by another user");
        }
        users.add(user);
    }

    @Override
    public void remove(User user) {
        checkNotNull(user);
        users.remove(user);
    }

    @Override
    public Set<User> getEntries() {
        return users;
    }

    @Override
    public <E extends MetaprojectObjectId> User get(E id) throws UnknownMetaprojectObjectIdException {
        if(!(id instanceof UserId)) {
            throw new IllegalArgumentException("Programmer error: Expected a user identifier");
        }
        Optional<User> user = getUserOptional((UserId)id);
        if(user.isPresent()) {
            return user.get();
        }
        else {
            throw new UnknownUserIdException("The specified user identifier does not correspond to an existing user");
        }
    }

    @Override
    public Set<User> getEntries(Name userName) {
        checkNotNull(userName);
        return users.stream().filter(user -> user.getName().get().equals(userName.get())).collect(Collectors.toSet());
    }

    @Override
    public Set<User> getEntries(EmailAddress emailAddress) {
        checkNotNull(emailAddress);
        return users.stream().filter(user -> user.getEmailAddress().equals(emailAddress)).collect(Collectors.toSet());
    }

    @Override
    public void setName(UserId userId, Name userName) throws UnknownMetaprojectObjectIdException {
        checkNotNull(userName);
        User user = get(userId);
        User newUser = createUser(userId, userName, user.getEmailAddress());
        update(userId, newUser);
    }

    @Override
    public void setEmailAddress(UserId userId, EmailAddress emailAddress) throws UnknownMetaprojectObjectIdException {
        checkNotNull(emailAddress);
        User user = get(userId);
        User newUser = createUser(userId, user.getName(), emailAddress);
        update(userId, newUser);
    }

    @Override
    public <E extends MetaprojectObjectId> void update(E id, User newObj) throws UnknownMetaprojectObjectIdException {
        remove(get(id));
        users.add(newObj);
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
    public boolean contains(User obj) {
        checkNotNull(obj);
        return users.contains(obj);
    }

    @Override
    public <E extends MetaprojectObjectId> boolean contains(E id) {
        checkNotNull(id);
        for(User user : users) {
            if(user.getId().equals(id)) {
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
