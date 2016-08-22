package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public interface Password {

    /**
     * Get the password
     *
     * @return Password string
     */
    @Nonnull
    String getPassword();

}
