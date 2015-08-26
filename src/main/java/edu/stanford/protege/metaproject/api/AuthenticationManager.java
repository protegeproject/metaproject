package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UserAddressAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserAlreadyRegisteredException;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;

import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface AuthenticationManager extends Manager {

    void registerUser(UserId userId, PlainPassword password) throws UserAlreadyRegisteredException, UserAddressAlreadyInUseException;

    void removeUser(UserId userId) throws UserNotRegisteredException;

    Set<UserAuthenticationDetails> getUserAuthenticationDetails();

    boolean hasValidCredentials(UserId userId, PlainPassword password) throws UserNotRegisteredException;

    boolean isRegistered(UserId userId);

    void changePassword(UserId userId, PlainPassword password) throws UserNotRegisteredException;

    void changePassword(UserAuthenticationDetails userDetails, PlainPassword password);

}
