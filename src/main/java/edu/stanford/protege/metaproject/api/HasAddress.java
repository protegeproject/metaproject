package edu.stanford.protege.metaproject.api;

/**
 * A representation of things that have an address
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
interface HasAddress {

    /**
     * Get the address of the object
     *
     * @return Address
     */
    Address getAddress();

}
