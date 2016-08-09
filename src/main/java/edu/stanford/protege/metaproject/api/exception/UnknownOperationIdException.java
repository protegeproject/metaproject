package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class UnknownOperationIdException extends UnknownPolicyObjectIdException {
    private static final long serialVersionUID = -2828654461530220971L;

    public UnknownOperationIdException() {
        super();
    }

    public UnknownOperationIdException(String message) {
        super(message);
    }

    public UnknownOperationIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownOperationIdException(Throwable cause) {
        super(cause);
    }

    protected UnknownOperationIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
