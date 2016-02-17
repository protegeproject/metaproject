package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UnknownProjectIdException extends UnknownAccessControlObjectIdException {
    private static final long serialVersionUID = 781856892742967319L;

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
