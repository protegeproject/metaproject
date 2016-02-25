package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UnknownProjectIdException extends UnknownAccessControlObjectIdException {
    private static final long serialVersionUID = -1164559865154277077L;

    public UnknownProjectIdException() {
        super();
    }

    public UnknownProjectIdException(String message) {
        super(message);
    }

    public UnknownProjectIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownProjectIdException(Throwable cause) {
        super(cause);
    }

    protected UnknownProjectIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
