package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class IdAlreadyInUseException extends ConfigurationException {
    private static final long serialVersionUID = -765203158316832832L;

    public IdAlreadyInUseException() {
        super();
    }

    public IdAlreadyInUseException(String message) {
        super(message);
    }

    public IdAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    protected IdAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
