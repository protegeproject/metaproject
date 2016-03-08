package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface AuthToken extends Comparable<AuthToken> {

    /**
     * Get the user identifier
     *
     * @return User identifier
     */
    UserId getUserId();

    /**
     * Check if token reflects successful authorisation with the server
     *
     * @return true if successfully authorised with the server, false otherwise
     */
    boolean isAuthorized();

}

