package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class UnknownPolicyObjectIdException extends ConfigurationException {
    private static final long serialVersionUID = -3371031224832939317L;

    public UnknownPolicyObjectIdException() {
        super();
    }

    public UnknownPolicyObjectIdException(String message) {
        super(message);
    }

    public UnknownPolicyObjectIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownPolicyObjectIdException(Throwable cause) {
        super(cause);
    }

    protected UnknownPolicyObjectIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
