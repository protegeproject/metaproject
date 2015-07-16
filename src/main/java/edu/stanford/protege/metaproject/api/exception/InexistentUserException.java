package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class InexistentUserException extends Exception {

    public InexistentUserException() {
        super();
    }

    public InexistentUserException(String message) {
        super(message);
    }

    public InexistentUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public InexistentUserException(Throwable cause) {
        super(cause);
    }

    protected InexistentUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
