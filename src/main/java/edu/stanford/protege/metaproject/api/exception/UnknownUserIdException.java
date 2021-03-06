package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class UnknownUserIdException extends UnknownPolicyObjectIdException {
    private static final long serialVersionUID = 7359092108632051421L;

    public UnknownUserIdException() {
        super();
    }

    public UnknownUserIdException(String message) {
        super(message);
    }

    public UnknownUserIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownUserIdException(Throwable cause) {
        super(cause);
    }

    protected UnknownUserIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
