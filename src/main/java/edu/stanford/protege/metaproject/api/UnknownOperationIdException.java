package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UnknownOperationIdException extends UnknownAccessControlObjectIdException {
    private static final long serialVersionUID = -1335216677651434899L;

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
