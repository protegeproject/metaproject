package edu.stanford.protege.metaproject.api;

/**
 * A representation of host information, consisting of a (Web) address and a port number
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Host extends HasAddress {

    int getPort();

}
