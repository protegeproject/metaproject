package edu.stanford.protege.metaproject.api;

/**
 * A representation of things that have an identifier and a name
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface HasDetails {

    Identifier getId();

    Name getName();

}
