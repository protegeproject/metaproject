package edu.stanford.protege.metaproject;

/**
 * A representation of a unique user identifier that is used for logging in to the server
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface UserId {

    /**
     * Get the user identifier
     *
     * @return User identifier
     */
    String getId();

}
