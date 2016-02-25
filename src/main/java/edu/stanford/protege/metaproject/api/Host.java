package edu.stanford.protege.metaproject.api;

/**
 * A representation of host information, consisting of an address, a port number, and the RMI registry port
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Host extends HasAddress {

    /**
     * Get the host port
     *
     * @return Port
     */
    Port getPort();

    /**
     * Get the RMI registry port
     *
     * @return RMI registry port
     */
    RegistryPort getRegistryPort();

}
