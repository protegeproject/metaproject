package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationNotFoundException extends AccessControlObjectNotFoundException {
    private static final long serialVersionUID = 3465417694325272882L;

    public OperationNotFoundException() {
        super();
    }

    public OperationNotFoundException(String message) {
        super(message);
    }

    public OperationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationNotFoundException(Throwable cause) {
        super(cause);
    }

    protected OperationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
