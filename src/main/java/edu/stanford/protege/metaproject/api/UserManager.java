package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.AccessControlObjectNotFoundException;
import edu.stanford.protege.metaproject.api.exception.UserAddressAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserAlreadyRegisteredException;
import edu.stanford.protege.metaproject.api.exception.UserNotFoundException;

import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface UserManager extends Manager {

    void add(User... user) throws UserAddressAlreadyInUseException, UserAlreadyRegisteredException;

    void remove(User... user) throws AccessControlObjectNotFoundException;

    Set<User> getUsers();

    Set<User> getUsers(Name userName);

    Set<User> getUsers(Address emailAddress);

    User getGuestUser();

    User getUser(UserId userId) throws UserNotFoundException;

    void changeUserName(UserId userId, Name userName) throws UserNotFoundException, UserAddressAlreadyInUseException, UserAlreadyRegisteredException;

    void changeEmailAddress(UserId userId, Address emailAddress) throws UserNotFoundException, UserAddressAlreadyInUseException, UserAlreadyRegisteredException;

}
