package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface AuthToken extends Comparable<AuthToken> {

    /**
     * Get the user that was attributed this authorization token upon login
     *
     * @return User
     */
    @Nonnull
    User getUser();

    /**
     * Check if token reflects successful authorisation with the server
     *
     * @return true if successfully authorised with the server, false otherwise
     */
    boolean isAuthorized();

}

