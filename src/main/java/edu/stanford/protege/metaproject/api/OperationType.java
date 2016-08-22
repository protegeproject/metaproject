package edu.stanford.protege.metaproject.api;

/**
 * Types of operations supported are the usual read, write and execute
 *
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public enum OperationType {
    READ, WRITE, EXECUTE;

    public String getName() {
        return Character.toUpperCase(name().charAt(0)) + name().substring(1).toLowerCase();
    }

    public String getInitial() {
        return Character.toUpperCase(name().charAt(0)) + "";
    }
}