package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UnknownUserIdException extends UnknownAccessControlObjectIdException {
    private static final long serialVersionUID = -7616897751282373804L;

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
