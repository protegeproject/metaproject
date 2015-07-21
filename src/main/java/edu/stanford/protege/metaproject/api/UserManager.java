package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UserNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A manager for users and user details (except password, which is handled by {@link AuthenticationManager})
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class UserManager {
    private static UserManager instance = null;
    private Set<User> users = new HashSet<>();

    private UserManager() { }

    public static UserManager getInstance() {
        if(instance == null) {
            instance = new UserManager();
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
     * @throws UserNotFoundException
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
    public Optional<User> getUser(UserId userId) {
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
    private User getUserOrFail(UserId userId) throws UserNotFoundException {
        Optional<User> user = getUser(userId);
        if(user.isPresent()) {
            return user.get();
        }
        else {
            throw new UserNotFoundException("The specified user does not exist");
        }
    }

    /**
     * Get the user(s) registered with the specified name
     *
     * @param userName  User name
     * @return Set of users
     */
    public Set<User> getUsers(UserName userName) {
        return users.stream().filter(user -> user.getName().equals(userName)).collect(Collectors.toSet());
    }

    /**
     * Get the user(s) registered with the specified email address
     *
     * @param emailAddress  Email address
     * @return Set of users
     */
    public Set<User> getUsers(EmailAddress emailAddress) {
        return users.stream().filter(user -> user.getEmailAddress().equals(emailAddress)).collect(Collectors.toSet());
    }

    /**
     * Change the display name of the given user
     *
     * @param userId    User identifier
     * @param userName  New name
     * @throws UserNotFoundException  User does not exist
     */
    public void changeUserName(UserId userId, UserName userName) throws UserNotFoundException {
        User user = getUserOrFail(userId);
        removeUser(user);

        User newUser = new User(user.getId(), userName, user.getEmailAddress());
        addUser(newUser);
    }

    /**
     * Change the email address of a user
     *
     * @param userId  User identifier
     * @param emailAddress New email address
     * @throws UserNotFoundException  User does not exist
     */
    public void changeEmailAddress(UserId userId, EmailAddress emailAddress) throws UserNotFoundException {
        User user = getUserOrFail(userId);
        removeUser(user);

        User newUser = new User(user.getId(), user.getName(), emailAddress);
        addUser(newUser);
    }
}
