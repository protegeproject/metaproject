package edu.stanford.protege.metaproject.api;

/**
 * A representation of things, like users, that have an identifier, a display name, and an email address
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface HasUserDetails {

    Identifier getId();

    Name getName();

    EmailAddress getEmailAddress();

}
